package com.cvc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;

import com.cvc.model.Transfer;
import com.cvc.util.Constants;

public class TransferDTO {
	
	@NotBlank(message = "{source.account.not.blank}")
	private String sourceAccount;
	
	@NotBlank(message = "{destination.account.not.blank}")
	private String destinationAccount;
	
	@NotBlank(message = "{transfer.value.not.blank}")
	private String transferValue;
	
	private String fee;

	@NotBlank(message = "{transfer.date.not.blank}")
	private String transferDate;
	
	private String appointmentDate;
	
	
	public TransferDTO(String sourceAccount, String destinationAccount, String transferValue, String fee,
			String transferDate, String appointmentDate) {
		this.sourceAccount = sourceAccount;
		this.destinationAccount = destinationAccount;
		this.transferValue = transferValue;
		this.fee = fee;
		this.transferDate = transferDate;
		this.appointmentDate = appointmentDate;
	}

	public String getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public String getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(String destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public String getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(String transferValue) {
		this.transferValue = transferValue;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public Transfer converterObjectToEntity() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY);

		LocalDate transferDate = LocalDate.parse(this.transferDate, formatter);
		LocalDate appointmentDate = LocalDate.parse(this.appointmentDate, formatter);
		
		Transfer transfer = new Transfer(null, 
										 this.sourceAccount, 
										 this.destinationAccount, 
										 new BigDecimal(this.transferValue), 
										 new BigDecimal(this.fee), 
										 transferDate, 
										 appointmentDate);
		
		return transfer;
	}
	
	public static TransferDTO converterEntityToObject(Transfer transfer) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY);
		
		return new TransferDTO( transfer.getSourceAccount(),  
												   transfer.getDestinationAccount(), 
												   transfer.getTransferValue().toString(), 
												   transfer.getFee().toString(),
												   transfer.getTransferDate().format(formatter).toString(), 
												   transfer.getAppointmentDate().format(formatter).toString());
		
	}
	
}
