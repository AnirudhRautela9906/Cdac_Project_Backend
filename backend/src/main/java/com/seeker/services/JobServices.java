package com.seeker.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.seeker.dto.job.JobDTO;
import com.seeker.dto.remaining.AddressDTO;
import com.seeker.exception.BackendException;
import com.seeker.model.Address;
import com.seeker.model.Job;
import com.seeker.model.User;
import com.seeker.repository.JobRepository;
import com.seeker.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class JobServices {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private JobRepository jobRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public Object getAllJobs() {
		
		return jobRepo.findAll().stream()
				.map(e-> mapper.map(e, JobDTO.class))
				.collect(Collectors.toList());
	}
	
	// Search button 
	public Object getJobsAtMyCity(AddressDTO addressDto) {
		
		return jobRepo.findAll().stream()
		.filter(e-> e.getJobLocation().getArea().equals(addressDto.getArea())
			  	 && e.getJobLocation().getCity().equals(addressDto.getCity())
			  	 && e.getJobLocation().getState().equals(addressDto.getState()))
		.map(e-> mapper.map(e, JobDTO.class))
		.collect(Collectors.toList());
	}
	
////	Drop Down load area
//	public Object getListOfDistinctArea(@Valid AddressDTO addressDto) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
//	Create Job
	public Object createJob(JobDTO jobDto, HttpServletResponse response) {
		User user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new BackendException("User not Found"));
        }

        System.out.println("JOB DTO"+jobDto);
        Job job = mapper.map(jobDto, Job.class);
        System.out.println("JOB MODEL"+job);
        Address Address = job.getJobLocation();
		
		// Important
		Address.setJobId(job);
		job.setJobLocation(Address);
        job.setCreator(user);
        List<Job> jobs = user.getJobsPosted();
        jobs.add(job); 
        user.setJobsPosted(jobs);
        jobRepo.save(job);
		return "Job Created";
	}

	public Object getJob(String id) {
		
		return mapper.map(jobRepo.findById(Long.parseLong(id)).orElseThrow(() -> new BackendException("Job not Found")),JobDTO.class);
	}

//	Edit Job
	public Object updateJob(@Valid String id, JobDTO jobDto) {
		
		return null;
	}
	
//	Apply Job
	public Object applyJob(String id) {
		User user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new BackendException("User not Found"));
        }
        Job job = jobRepo.findById(Long.parseLong(id)).orElseThrow(() -> new BackendException("Job not Found"));
        
        if(job.getAppliedUsers().contains(user))
        	throw new BackendException("Already Applied");
        
        List<Job> jobsList = user.getJobsApplied();
        jobsList.add(job);
        user.setJobsApplied(jobsList);
        List<User> usersList = job.getAppliedUsers();
        usersList.add(user);
        job.setAppliedUsers(usersList);
        
        jobRepo.save(job);
		return "Job Applied";
	}

	
	
	//Job Assigned to a user by the job poster
	public Object assignedUserForJob(String email) {
		User user = userRepo.findByEmail(email).orElseThrow(()-> new BackendException("User Not Found"));
		
		return null;
	}




	
}
