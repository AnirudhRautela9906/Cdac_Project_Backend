package com.seeker.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.seeker.config.JwtService;
import com.seeker.dto.job.JobAppliedDTO;
import com.seeker.dto.job.JobPostedDTO;
import com.seeker.dto.remaining.AddressDTO;
import com.seeker.dto.user.LoginDTO;
import com.seeker.dto.user.MeDTO;
import com.seeker.dto.user.RegisterDTO;
import com.seeker.exception.BackendException;
import com.seeker.model.Address;
import com.seeker.model.Job;
import com.seeker.model.Role;
import com.seeker.model.User;
import com.seeker.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServices {
	
	@Autowired
	private JwtService jwtService;
	
//	@Autowired
//	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository UserRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private ModelMapper mapper;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	
	// Admin ==> List of all Users  
	public List<RegisterDTO> getAllUsers(){
		return UserRepo.findAll().stream()
				.map(e-> mapper.map(e, RegisterDTO.class))
				.collect(Collectors.toList());
	}
	
	// One user details
	public RegisterDTO getUser(String email) {
		return mapper.map(UserRepo.findByEmail(email).orElseThrow(() -> new BackendException("User not Found")),
				RegisterDTO.class);
	}

	// Create User
	public Object registerUser(RegisterDTO RegisterDTO,  HttpServletResponse response) {
//		public RegisterDTO registerUser(RegisterDTO RegisterDTO,  HttpServletResponse response) {
//	public RegisterDTO registerUser(RegisterDTO RegisterDTO) {
		System.out.println(RegisterDTO);
		
		User User = mapper.map(RegisterDTO, User.class);
		
		Address Address = User.getAddress();
		
		// Important
		Address.setUser(User);
		User.setAddress(Address);
		User.setPassword(passwordEncoder.encode(RegisterDTO.getPassword()));
		User.setRole(Role.USER);
		if(RegisterDTO.getEmail().equals("ani@gmail.com"))
			User.setRole(Role.ADMIN);
//		UserRepo.save(User);
		
		 // Set JWT in cookie
//		final UserDetails userDetails = userDetailsService.loadUserByUsername(RegisterDTO.getEmail());

		String jwt = jwtService.generateToken(User);
        Cookie cookie = new Cookie("JWT_TOKEN", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*5);
        response.addCookie(cookie);
        
//        return TokenDTO
//        		.builder()
//        		.token(jwt)
//        		.build();
        
//		return mapper.map(UserRepo.save(User), RegisterDTO.class);

        User = UserRepo.save(User);
        return loadUserDetails(User.getEmail()) ;
	}
	
	
	// Update User details
	public RegisterDTO updateUser(String email,RegisterDTO registerDTO) {
		User User = mapper.map(registerDTO, User.class);
		if (UserRepo.existsById(email)) {
			User.setEmail(email);
//			User.setIssue_details(RegisterDTO.getIssue_details());
//			User.setResolution_details(RegisterDTO.getResolution_details());
//			User.setStatus(RegisterDTO.getStatus());
//			meDTO.setName(user.getName());
//			meDTO.setEmail(user.getEmail());
//			meDTO.setPassword(user.getPassword());
//			meDTO.setAadhar(user.getAadhar());
//			meDTO.setAge(user.getAge());
//			meDTO.setRole(user.getRole());
//			meDTO.setAddress(mapper.map(user.getAddress(), AddressDTO.class));
//			meDTO.setPhoneNumber(user.getPhoneNumber());
			
			UserRepo.save(User);			
			return mapper.map(User, RegisterDTO.class);
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

	// LOgin
	public Object login(LoginDTO loginDto,  HttpServletResponse response) {
		  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		  User user = UserRepo.findByEmail(loginDto.getEmail()).orElseThrow(()-> new BackendException("User not found"));
		  String jwt = jwtService.generateToken(user);
	        Cookie cookie = new Cookie("JWT_TOKEN", jwt);
	        cookie.setHttpOnly(true);
	        cookie.setPath("/");
	        cookie.setMaxAge(60*60*5);
	        response.addCookie(cookie);
	        response.addHeader("Authorization", "Bearer "+jwt); // Headers are set 
	        
		return loadUserDetails(user.getEmail()) ;
	}

	public Object loadUserDetails(String username) {
		User user = UserRepo.findByEmail(username).orElseThrow(()-> new BackendException("User not found"));
		MeDTO meDTO = new MeDTO();
		
		meDTO.setName(user.getName());
		meDTO.setEmail(user.getEmail());
		meDTO.setPassword(user.getPassword());
		meDTO.setAadhar(user.getAadhar());
		meDTO.setAge(user.getAge());
		meDTO.setRole(user.getRole());
		meDTO.setAddress(mapper.map(user.getAddress(), AddressDTO.class));
		meDTO.setPhoneNumber(user.getPhoneNumber());
		
		// List of jobs posted by the logged in user
		List<JobPostedDTO> jobPostedDtoList = user.getJobsPosted().stream()
		.map(e-> mapper.map(e, JobPostedDTO.class))
		.collect(Collectors.toList());
		meDTO.setJobsPosted(jobPostedDtoList);
		
		//List of jobs applied by the logged in user
		List<Job> jobsAppliedList = user.getJobsApplied();
		List<JobAppliedDTO> jobsAppliedDtoList = jobsAppliedList.stream().map(e -> mapper.map(e, JobAppliedDTO.class)).collect(Collectors.toList());
		meDTO.setJobsApplied(jobsAppliedDtoList);
		
		
		return meDTO;

		
	}
}
