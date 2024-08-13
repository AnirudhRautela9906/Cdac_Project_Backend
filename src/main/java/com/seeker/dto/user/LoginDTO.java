package com.seeker.dto.user;

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
public class LoginDTO {
	@NotEmpty(message = "Email Id cannot be empty")
	@NotNull(message = "Email Id cannot be null")
	@NotBlank(message = "Email Id cannot be blank")
	private String email;

	@NotEmpty(message = "Password cannot be empty")
	@NotNull(message = "Password cannot be null")
	@NotBlank(message = "Password cannot be blank")
	private String password;
}
