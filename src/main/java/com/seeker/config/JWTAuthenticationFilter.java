package com.seeker.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService; 
	
	private final UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain
	)throws ServletException, IOException {
		final String userEmail;
//		final String jwt;
//		final String authHeader = request.getHeader("Authorization");
//		System.out.println("Authorization: " +authHeader);
//		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//		
//		jwt = authHeader.substring(7);
//		
		
        
		String COOKIE_NAME = "JWT_TOKEN";
		String jwt = null;

		// Get all cookies from the request
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        // Check if the cookie name matches the expected name
		        if (COOKIE_NAME.equals(cookie.getName())) {
		            // Retrieve the JWT from the cookie value
		            jwt = cookie.getValue();
		            break;
		        }
		    }
		}

		// If JWT not found, continue the filter chain and return
		if (jwt == null) {
        	System.out.println("JWT NOT FOUND");
		    filterChain.doFilter(request, response);
		    return;
		}

		
		System.out.println(jwt);
               
		userEmail = jwtService.extractUsername(jwt) ;  // JWT TOKEN TIMEOUT exception needs to be  handled
		if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
			

			
			if(jwtService.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities()
				);
				
				authToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
				);
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			System.out.println(userDetails.getUsername());
		}
		filterChain.doFilter(request, response);

	}

}
