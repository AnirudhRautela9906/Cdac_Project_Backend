package com.seeker.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.seeker.model.JobStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JobPostedDTO {
	
	private Long jobId;
	@NotEmpty(message = "Short Description cannot be empty")
	@NotNull(message = "Short Description cannot be null")
	@NotBlank(message = "Short Description cannot be blank")
    private String shortDesc;
    
	@NotEmpty(message = "Long Description cannot be empty")
	@NotNull(message = "Long Description cannot be null")
	@NotBlank(message = "Long Description cannot be blank")
    private String longDesc;

//	private RegisterDTO creator;
	
	private Double price;

	private AddressDTO jobLocation;

	private RegisterDTO assignedUser;

	private LocalDateTime jobPostDateTime;

	private List<RegisterDTO> appliedUsers;

	private JobStatus status;
}
