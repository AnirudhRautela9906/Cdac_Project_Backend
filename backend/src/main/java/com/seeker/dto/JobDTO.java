package com.seeker.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seeker.model.Address;
import com.seeker.model.JobStatus;
import com.seeker.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobDTO {

	private Long jobId;
	@NotEmpty(message = "Short Description cannot be empty")
	@NotNull(message = "Short Description cannot be null")
	@NotBlank(message = "Short Description cannot be blank")
    private String shortDesc;
    
	@NotEmpty(message = "Long Description cannot be empty")
	@NotNull(message = "Long Description cannot be null")
	@NotBlank(message = "Long Description cannot be blank")
    private String longDesc;

//	private User creator;
	
	private Double price;

	private Address jobLocation;

	private User assignedUser;

	private LocalDateTime jobPostDateTime;

	private List<User> appliedUsers;

	private JobStatus status;
}
