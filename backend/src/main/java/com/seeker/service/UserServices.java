package com.seeker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.seeker.config.JwtService;
import com.seeker.dto.LoginDTO;
import com.seeker.dto.UserDTO;
import com.seeker.exception.BackendException;
import com.seeker.model.Address;
import com.seeker.model.User;
import com.seeker.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class UserServices {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private UserRepository UserRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	
	// Admin ==> List of all Users  
	public List<UserDTO> getAllUsers(){
		return UserRepo.findAll().stream()
				.map(e-> mapper.map(e, UserDTO.class))
				.collect(Collectors.toList());
	}
	
	// One user details
	public UserDTO getUser(String email) {
		return mapper.map(UserRepo.findByEmail(email).orElseThrow(() -> new BackendException("User not Found")),
				UserDTO.class);
	}

	// Create User
	public UserDTO registerUser(UserDTO UserDTO,  HttpServletResponse response) {
//	public UserDTO registerUser(UserDTO UserDTO) {
		System.out.println(UserDTO);
		
		User User = mapper.map(UserDTO, User.class);
		
		Address Address = User.getAddress();
		
		// Important
		Address.setUser(User);
		User.setAddress(Address);
		User.setPassword(passwordEncoder.encode(UserDTO.getPassword()));
//		UserRepo.save(User);
		
		 // Set JWT in cookie
//		final UserDetails userDetails = userDetailsService.loadUserByUsername(UserDTO.getEmail());

		String jwt = jwtService.generateToken(User);
        Cookie cookie = new Cookie("JWT_TOKEN", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        
//        return TokenDTO
//        		.builder()
//        		.token(jwt)
//        		.build();
        
		
		return mapper.map(UserRepo.save(User), UserDTO.class);
	}
	
	
	// Update User details
	public UserDTO updateUser(String email,UserDTO UserDTO) {
		User User = mapper.map(UserDTO, User.class);
		if (UserRepo.existsById(email)) {
			User.setEmail(email);
//			User.setIssue_details(UserDTO.getIssue_details());
//			User.setResolution_details(UserDTO.getResolution_details());
//			User.setStatus(UserDTO.getStatus());
			
			UserRepo.save(User);			
			return mapper.map(User, UserDTO.class);
		}
		throw new BackendException("User Not Found");
	}
	
//	Admin ==> Delete
	public String deleteUser(String email) {
		if (UserRepo.existsById(email)) {
			UserRepo.deleteById(email);
			return "Deleted Successfully";
		}
		throw new BackendException("User Not Found");
	}

	public Object login(LoginDTO loginDto,  HttpServletResponse response) {
		  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		  User user = UserRepo.findByEmail(loginDto.getEmail()).orElseThrow(()-> new BackendException("User not found"));
//		  if(!loginDto.getPassword().equals(user.getPassword()))
//			  throw new BackendException("Invalid Credentials");
		  String jwt = jwtService.generateToken(user);
	        Cookie cookie = new Cookie("JWT_TOKEN", jwt);
	        cookie.setHttpOnly(true);
	        cookie.setPath("/");
	        response.addCookie(cookie);
		return "Login Successful";
	}
}
