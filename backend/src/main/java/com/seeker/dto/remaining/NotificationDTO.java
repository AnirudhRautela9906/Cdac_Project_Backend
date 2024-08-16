package com.seeker.dto.remaining;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
	
	private Long id;
	
	private String message;
	
	private String jobTitle;
	
	private boolean isRead;
	

}
