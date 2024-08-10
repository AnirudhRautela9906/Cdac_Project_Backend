package com.seeker.dto;

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
public class UserDTO {
//	User (id, name, email, password, List of Jobs Applied by him, List of Jobs Posted by him, rating, 
	@NotEmpty(message = "Email Id cannot be empty")
	@NotNull(message = "Email Id cannot be null")
	@NotBlank(message = "Email Id cannot be blank")
	private String email;

	@NotEmpty(message = "Name cannot be empty")
	@NotNull(message = "Name cannot be null")
	@NotBlank(message = "Name cannot be blank")
	private String name;

	@NotEmpty(message = "Password cannot be empty")
	@NotNull(message = "Password cannot be null")
	@NotBlank(message = "Password cannot be blank")
	private String password;

	@NotEmpty(message = "Aadhar cannot be empty")
	@NotNull(message = "Aadhar cannot be null")
	@NotBlank(message = "Aadhar cannot be blank")
	private String aadhar;


	@NotNull(message = "Age cannot be null")
	private int age;
	
	private Role role;


//    private List<Job> jobsApplied = new ArrayList<>();
//
//    private List<Job> jobsPosted = new ArrayList<>();
//
//    private Set<Job> assignedJobs;

//	@NotEmpty(message = "Address cannot be empty")
//	@NotNull(message = "Address cannot be null")
//	@NotBlank(message = "Address cannot be blank")   
    private AddressDTO address;
    
    

}
