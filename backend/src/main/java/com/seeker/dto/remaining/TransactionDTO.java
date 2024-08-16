package com.seeker.dto.remaining;

import com.seeker.dto.job.JobTransactionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
	
	private Long id;
	
	private String transactionCode;
	
	private Double price;
	
	private JobTransactionDTO job;
	
}
