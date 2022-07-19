package com.cvc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvc.dto.TransferDTO;
import com.cvc.model.Transfer;
import com.cvc.repository.ITransferRepository;
import com.cvc.util.Constants;
import com.cvc.util.DateUtil;

@Service
public class TransferService {
	
	@Autowired
	private ITransferRepository transferRepository;
	
	public BigDecimal calculateTransferFee(String transferValue, String transferDate, String appointmentDate  ){
		
		BigDecimal calculateFee = BigDecimal.ZERO;
		Long diff = DateUtil.diffDates(transferDate, Constants.DD_MM_YYYY);
		
		//Type A
		if (diff.equals(0L)) {
			calculateFee = this.processTypeA(transferValue);
			
		//Type B
		} else if (diff > 0 && diff <= 10) {
			calculateFee = this.processTypeB(transferValue, diff);
			
		//Type C
		} else if (diff > 10){
			calculateFee = this.processTypeC(transferValue, diff);
		}
		
		return calculateFee;
	}

	private BigDecimal processTypeA(String transferValue) {
		
		BigDecimal calculate = new BigDecimal(3);
		BigDecimal percent = new BigDecimal("0.03");
		
		calculate.add(new BigDecimal(transferValue).multiply(percent).setScale(2, RoundingMode.HALF_EVEN));
		
		return calculate;
	}

	private BigDecimal processTypeB(String transferValue, Long diff) {
		
		BigDecimal calculate = new BigDecimal(12);
		calculate = calculate.multiply(new BigDecimal(diff).setScale(2, RoundingMode.HALF_EVEN));
		
		return calculate;
	}

	private BigDecimal processTypeC(String transferValue, Long diff) {
		
		BigDecimal calculate;
		BigDecimal transferValueConvert = new BigDecimal(transferValue);
		BigDecimal percent = BigDecimal.ZERO;
		
		if ( diff <= 20 ) {
			percent = new BigDecimal("0.08");
		
		} else if ( diff > 20 && diff <=30) {
			percent = new BigDecimal("0.06");
			
		} else if ( diff > 30 && diff <=40) {
			percent = new BigDecimal("0.04");

		} else if ( diff > 40 &&  transferValueConvert.compareTo(new BigDecimal("100000")) == 0) {
			percent = new BigDecimal("0.02");
		}
		
		calculate = transferValueConvert.multiply(percent).setScale(2, RoundingMode.HALF_EVEN);
		
		return calculate;
	}

	public List<Transfer> makeListTransfer() {
		return transferRepository.findAll();
	}

	public @Valid TransferDTO saveTransfer(@Valid TransferDTO transferDto) {
		
		Transfer en = transferRepository.save(transferDto.converterObjectToEntity());

		return TransferDTO.converterEntityToObject(en);
	}
}
