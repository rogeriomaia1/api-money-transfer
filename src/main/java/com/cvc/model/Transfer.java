package com.cvc.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transfer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String sourceAccount;
	
	@Column(nullable = false)
	private String destinationAccount;
	
	@Column(nullable = false)
	private BigDecimal transferValue;
	
	@Column(nullable = false)
	private BigDecimal fee;
	
	@Column(nullable = false)
	private LocalDate transferDate;
	
	@Column
	private LocalDate appointmentDate;

	
	public Transfer() {}
	
	public Transfer(Long id, String sourceAccount, String destinationAccount, BigDecimal transferValue, BigDecimal fee,
			LocalDate transferDate, LocalDate appointmentDate) {
		super();
		this.id = id;
		this.sourceAccount = sourceAccount;
		this.destinationAccount = destinationAccount;
		this.transferValue = transferValue;
		this.fee = fee;
		this.transferDate = transferDate;
		this.appointmentDate = appointmentDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(BigDecimal transferValue) {
		this.transferValue = transferValue;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public LocalDate getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	
}
