package com.seeker.dto.user;

import java.util.List;

import com.seeker.dto.job.JobAppliedDTO;
import com.seeker.dto.job.JobPostedDTO;
import com.seeker.dto.remaining.AddressDTO;
import com.seeker.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MeDTO {
//	User (id, name, email, password, List of Jobs Applied by him, List of Jobs Posted by him, rating, 

	private String email;

	private String name;

	private String password;
	
	private String aadhar;

	private int age;
	
	private Role role;
  
    private String phoneNumber;

    private List<JobAppliedDTO> jobsApplied;

    private List<JobPostedDTO> jobsPosted;

//    private List<Job> assignedJobs;

	@NotNull(message = "Address cannot be null")   
    private AddressDTO address;
    
    

}
