package com.seeker.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class Transaction {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "transaction_code")
	private String transactionCode;
	
	private Double price;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "job_id")
	private Job job;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
