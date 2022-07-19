package com.cvc.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cvc.dto.TransferDTO;
import com.cvc.exception.NotFoundException;
import com.cvc.model.Transfer;
import com.cvc.service.TransferService;
import com.cvc.util.Constants;

@SpringBootTest
class MoneyTransferControllerTest {
	
	private static final String STRING_TRANSFER_VALUE_2000 = "2000";
	private static final LocalDate APPOINTMENT_DATE = LocalDate.now();
	private static final LocalDate TRANSFER_DATE = APPOINTMENT_DATE.plusDays(10);
	private static final long ID = 1L;
	private static final String DESTINATION_ACCOUNT = "002";
	private static final String SOURCE_ACCONT = "001";

	private Transfer transfer;
	private TransferDTO transferDto;
	
	@InjectMocks
	private MoneyTransferController moneyTransferController;

	@Mock
	private TransferService transferService;
	
	@BeforeEach
	void setUp() throws Exception {
		 MockitoAnnotations.openMocks(this);
	     this.startTransfer();
	}


	@Test
	void testSaveSchedulingTransferVerifyMessage() {
		when(transferService.calculateTransferFee(anyString(), 
				anyString() , 
				anyString())).thenReturn(BigDecimal.ZERO);
		when(transferService.saveTransfer(this.transferDto)).thenReturn(this.transferDto);
		
		 
		 assertThrows(NotFoundException.class,
		            ()->{
		            	moneyTransferController.saveSchedulingTransfer(this.transferDto);
		            });
	}


	@Test
	void testMakeSchedulingTransfer() {
		  when(transferService.makeListTransfer()).thenReturn(List.of(this.transfer));
 
	      ResponseEntity<List<TransferDTO>> response = moneyTransferController.makeSchedulingTransfer();
	      
	      Assertions.assertNotNull(response);
	      Assertions.assertNotNull(response.getBody());
	      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	      Assertions.assertEquals(ResponseEntity.class, response.getClass());
	      Assertions.assertEquals(ArrayList.class, response.getBody().getClass());
	      Assertions.assertEquals(TransferDTO.class, response.getBody().get(0).getClass());
	      Assertions.assertEquals(response.getBody().get(0).getSourceAccount(), SOURCE_ACCONT);                                 
	      Assertions.assertEquals(response.getBody().get(0).getDestinationAccount(), DESTINATION_ACCOUNT );                     
	      Assertions.assertEquals(response.getBody().get(0).getTransferValue(), STRING_TRANSFER_VALUE_2000);    
	      Assertions.assertEquals(response.getBody().get(0).getFee(), BigDecimal.TEN.toString() );                                         
	      Assertions.assertEquals(response.getBody().get(0).getTransferDate(),  TRANSFER_DATE.format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY)));             
	      Assertions.assertEquals(response.getBody().get(0).getAppointmentDate(), APPOINTMENT_DATE.format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY)));                            
	}

	private void startTransfer() {
		this.transfer = new Transfer(ID, 
				 SOURCE_ACCONT, 
				 DESTINATION_ACCOUNT, 
				 new BigDecimal(STRING_TRANSFER_VALUE_2000),
				 BigDecimal.TEN, 
				 TRANSFER_DATE, 
				 APPOINTMENT_DATE
				 );

		this.transferDto = new TransferDTO(SOURCE_ACCONT, 
							   DESTINATION_ACCOUNT, 
							   STRING_TRANSFER_VALUE_2000,
							   "1", 
							   APPOINTMENT_DATE.plusDays(2L).format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY)), 
							   APPOINTMENT_DATE.format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY))
							   );
		
	}
}
