package com.hatimonline.spike.braintree3ds2;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class BraintreePayment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Column(name = "legacy_id")
	private String legacyId;
	
	@Column(name = "transaction_date")
	private Instant transactionDate;
	
	@NotNull
	@DecimalMin(value = "0")
	@Column(name = "amount", precision = 21, scale = 2, nullable = false)
	private BigDecimal amount;
	
	@Column(name = "currency")
	private String  currency;
	
	
	public BraintreePayment() {
	}
	
	public BraintreePayment(String transactionId, String legacyId, Instant transactionDate, BigDecimal amount, String currency) {
		this.transactionId = transactionId;
		this.legacyId = legacyId;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.currency = currency;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getLegacyId() {
		return legacyId;
	}
	
	public void setLegacyId(String legacyId) {
		this.legacyId = legacyId;
	}
	
	public Instant getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Instant transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
