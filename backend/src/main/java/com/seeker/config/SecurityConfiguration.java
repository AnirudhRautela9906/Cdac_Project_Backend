package com.seeker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.seeker.model.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JWTAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			.authorizeHttpRequests()
//			User Mapping
			.requestMatchers("/seeker/register","/seeker/login","/seeker/me").permitAll()
			.requestMatchers("/seeker/users").hasAuthority(Role.ADMIN.name())
			.requestMatchers("/seeker/getUser/{email}").hasAuthority(Role.ADMIN.name())
			.requestMatchers("/seeker/delete/{email}").hasAuthority(Role.ADMIN.name())
			.requestMatchers("/seeker/update/{email}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
			.requestMatchers("/seeker/update-wallet").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
			.requestMatchers("/seeker/logout").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
			
//			Job Mapping
			.requestMatchers("/seeker/job").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
			.requestMatchers("/seeker/job/search").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
			.requestMatchers("/seeker/job/create").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
			.requestMatchers("/seeker/job/update/{id}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
			.requestMatchers("/seeker/job/apply/{id}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
			.requestMatchers("/seeker/job/job-completed/{jobId}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
			.requestMatchers("/seeker/job/assign/{email}/{id}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
			.requestMatchers("/seeker/job").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())

			.anyRequest()
			.authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
