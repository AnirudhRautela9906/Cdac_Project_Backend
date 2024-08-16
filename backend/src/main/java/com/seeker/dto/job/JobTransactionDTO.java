package com.seeker.dto.job;

import java.time.LocalDateTime;

import com.seeker.dto.remaining.AddressDTO;
import com.seeker.dto.user.RegisterDTO;
import com.seeker.model.JobStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JobTransactionDTO {

	private Long jobId;
	@NotEmpty(message = "Title Description cannot be empty")
	@NotNull(message = "Title Description cannot be null")
	@NotBlank(message = "Title Description cannot be blank")
    private String title;
    
	@NotEmpty(message = "Long Description cannot be empty")
	@NotNull(message = "Long Description cannot be null")
	@NotBlank(message = "Long Description cannot be blank")
    private String longDesc;

	private RegisterDTO creator;
	
	private Double price;

	private AddressDTO jobLocation;

	private RegisterDTO assignedUser;

	private LocalDateTime jobPostDateTime;

	private JobStatus status;
}
