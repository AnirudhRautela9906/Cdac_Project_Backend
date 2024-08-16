package com.seeker.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.seeker.exception.BackendException;
import com.seeker.model.User;
import com.seeker.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NotificationServices {
	
	@Autowired
	private UserRepository userRepository;
	
	public Object readAllNotifications(){
		User user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new BackendException("User not Found"));
        }
        user.getNotificationList().forEach(n -> n.setRead(true));
        
        return "Read All Notifications";
        
	}

}
