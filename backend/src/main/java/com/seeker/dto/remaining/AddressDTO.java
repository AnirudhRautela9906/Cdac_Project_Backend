package com.seeker.dto.remaining;

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
public class AddressDTO {
	@NotBlank(message = "State cannot be blank") 
	private String state;
	@NotBlank(message = "City cannot be blank") 
	private String city;
	@NotBlank(message = "Area cannot be blank") 
	private String area;
}
