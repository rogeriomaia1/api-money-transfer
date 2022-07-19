package com.cvc.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cvc.dto.TransferDTO;
import com.cvc.model.Transfer;
import com.cvc.repository.ITransferRepository;
import com.cvc.util.Constants;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

	private static final String STRING_TRANSFER_VALUE_2000 = "2000";
	private static final LocalDate APPOINTMENT_DATE = LocalDate.now();
	private static final long ID = 1L;
	private static final String DESTINATION_ACCOUNT = "002";
	private static final String SOURCE_ACCONT = "001";

	@InjectMocks
	private TransferService transferService;
	
	@Mock
	private ITransferRepository transferRepository;
	
	private Transfer transfer;
	private TransferDTO transferDto;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startTransfer();
	}

	@Test
	void whenTransferDateEqualsAppointmentThenReturnTypeA() {
		BigDecimal calculateFee = new BigDecimal("3");
		String transferDate = APPOINTMENT_DATE.plusDays(0L).format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY));
		
		try {
			Assertions.assertEquals(calculateFee.longValue(), transferService.calculateTransferFee(STRING_TRANSFER_VALUE_2000, transferDate, APPOINTMENT_DATE.toString()).longValue());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void whenTransferDateEqualsAppointmentThenReturnTypeB() {
		BigDecimal calculateFee = new BigDecimal("12");
		String transferDate = APPOINTMENT_DATE.plusDays(1L).format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY));
		
		try {
			Assertions.assertEquals(calculateFee.longValue(), transferService.calculateTransferFee(STRING_TRANSFER_VALUE_2000, transferDate , APPOINTMENT_DATE.toString()).longValue());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void whenTransferDateGreaterThan20DaysAppointmentThenReturnTypeC() {
		BigDecimal calculateFee = new BigDecimal("120");
		String transferDate = APPOINTMENT_DATE.plusDays(26L).format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY));
		
		try {
			Assertions.assertEquals(calculateFee.longValue(), transferService.calculateTransferFee(STRING_TRANSFER_VALUE_2000, transferDate , APPOINTMENT_DATE.toString()).longValue());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void whenTransferDateGreaterThan30DaysAppointmentThenReturnTypeC() {
		BigDecimal calculateFee = new BigDecimal("80");
		String transferDate = APPOINTMENT_DATE.plusDays(31L).format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY));
		
		try {
			Assertions.assertEquals(calculateFee.longValue(), transferService.calculateTransferFee(STRING_TRANSFER_VALUE_2000, transferDate , APPOINTMENT_DATE.toString()).longValue());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testMakeListTransfer() {
		when(transferRepository.findAll()).thenReturn(List.of(this.transfer));
		
		List<Transfer> list = transferService.makeListTransfer();
		
		Assertions.assertEquals(list.size(), 1);
		Assertions.assertEquals(list.get(0).getId(), ID);
		Assertions.assertEquals(list.get(0).getSourceAccount(), SOURCE_ACCONT);
		Assertions.assertEquals(list.get(0).getDestinationAccount(), DESTINATION_ACCOUNT );
		Assertions.assertEquals(list.get(0).getTransferValue(), new BigDecimal(STRING_TRANSFER_VALUE_2000));
		Assertions.assertEquals(list.get(0).getFee(), BigDecimal.TEN );
		Assertions.assertEquals(list.get(0).getTransferDate(),  APPOINTMENT_DATE.plusDays(10));
		Assertions.assertEquals(list.get(0).getAppointmentDate(), APPOINTMENT_DATE);
	}

	@Test
	void testSaveTransfer() {
		when(transferRepository.save(Mockito.any())).thenReturn(transfer);
		
		TransferDTO transferDtoSaved = transferService.saveTransfer(this.transferDto);
		
		Assertions.assertEquals(transferDtoSaved.getSourceAccount(), transfer.getSourceAccount());
		Assertions.assertEquals(transferDtoSaved.getDestinationAccount(), transfer.getDestinationAccount());
		Assertions.assertEquals(transferDtoSaved.getTransferValue(), transfer.getTransferValue().toString());
		Assertions.assertEquals(transferDtoSaved.getFee(), transfer.getFee().toString());
		Assertions.assertEquals(transferDtoSaved.getTransferDate(), transfer.getTransferDate().format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY)));
		Assertions.assertEquals(transferDtoSaved.getAppointmentDate(), transfer.getAppointmentDate().format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY)));
	}
	
	private void startTransfer() {
		this.transfer = new Transfer(ID, 
									 SOURCE_ACCONT, 
									 DESTINATION_ACCOUNT, 
									 new BigDecimal(STRING_TRANSFER_VALUE_2000),
									 BigDecimal.TEN, 
									 APPOINTMENT_DATE.plusDays(10), 
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
