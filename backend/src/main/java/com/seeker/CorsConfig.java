package com.seeker;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Apply CORS to all endpoints
				.allowedOrigins("http://localhost:3000","http://localhost:3001") // Replace with your client-side URL
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Specify allowed methods
				.allowedHeaders("Content-Type", "Authorization") // Specify allowed headers
				.allowCredentials(true); // Allow credentials (cookies) to be included
	}
}
