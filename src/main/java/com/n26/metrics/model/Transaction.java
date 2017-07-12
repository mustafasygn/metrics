package com.n26.metrics.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by musta on 5.07.2017.
 */
public class Transaction {
	@NotNull
	private Double amount;

	@NotNull
	private Long timestamp;

	public Transaction() {
	}

	public Transaction(Double amount, Long timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
