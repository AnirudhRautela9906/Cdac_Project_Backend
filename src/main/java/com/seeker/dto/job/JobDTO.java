package com.seeker.dto.job;

import java.time.LocalDateTime;
import java.util.List;

import com.seeker.dto.remaining.AddressDTO;
import com.seeker.dto.user.RegisterDTO;
import com.seeker.model.JobStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JobDTO {

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

	private List<RegisterDTO> appliedUsers;

	private JobStatus status;
}
