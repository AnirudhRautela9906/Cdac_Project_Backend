package com.seeker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeker.services.NotificationServices;

@RestController
@RequestMapping("/seeker/notification")
public class NotificationController {
	
	@Autowired
	private NotificationServices notificationService;
	
	@GetMapping("/read-all-notification")
	public ResponseEntity<?> readAllNotifications(){
		return ResponseEntity.status(HttpStatus.OK).body(notificationService.readAllNotifications());
	}
	
}
