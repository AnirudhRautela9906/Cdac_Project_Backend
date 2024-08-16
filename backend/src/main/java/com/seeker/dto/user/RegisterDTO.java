package com.seeker.dto.user;

import com.seeker.dto.remaining.AddressDTO;
import com.seeker.model.Role;

import jakarta.validation.Valid;
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
public class RegisterDTO {
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
	private Integer age;
	
	private Role role;

	@NotNull(message = "Address cannot be Null")   
	@Valid
	private AddressDTO address;

	@NotBlank(message = "Phone Number cannot be blank")   
    private String phoneNumber;

    
    

}
