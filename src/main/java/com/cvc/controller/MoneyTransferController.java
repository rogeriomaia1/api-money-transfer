package com.cvc.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvc.dto.TransferDTO;
import com.cvc.exception.NotFoundException;
import com.cvc.model.Transfer;
import com.cvc.service.TransferService;
import com.cvc.util.Constants;

@RestController
@RequestMapping("/transfer")
public class MoneyTransferController {
	
	@Autowired
	private TransferService transferService;
	
	@PostMapping("/insert")
	public ResponseEntity<TransferDTO> saveSchedulingTransfer( @RequestBody @Valid TransferDTO transferDto) {
		
		BigDecimal feeValue = transferService.calculateTransferFee(transferDto.getTransferValue(), 
																   transferDto.getTransferDate(), 
																   transferDto.getAppointmentDate());
		
		if (feeValue.compareTo(BigDecimal.ZERO) == 0) {
			throw new NotFoundException("There is no applicable fee for the parameters informed.");
		}
		
		transferDto.setFee(feeValue.toString());
		transferDto.setAppointmentDate(LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY)));
		
		transferDto = transferService.saveTransfer(transferDto);
		
		return new ResponseEntity<>(transferDto, HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<TransferDTO>> makeSchedulingTransfer() {
	
		List<Transfer> listTransfer = transferService.makeListTransfer();
		
		if (listTransfer.isEmpty()) {
			throw new NotFoundException("There are no transfers registered.");
		}
		
		List<TransferDTO> listTransferDto = new ArrayList<>();
		
		listTransfer.forEach(tf -> {
			listTransferDto.add(TransferDTO.converterEntityToObject(tf)); 
		});
		
		return new ResponseEntity<List<TransferDTO>>(listTransferDto, HttpStatus.OK);
	}
		
}
