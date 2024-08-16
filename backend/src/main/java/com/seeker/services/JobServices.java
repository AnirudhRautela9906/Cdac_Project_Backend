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
import com.seeker.model.JobStatus;
import com.seeker.model.Notification;
import com.seeker.model.Transaction;
import com.seeker.model.User;
import com.seeker.repository.JobRepository;
import com.seeker.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import utils.Utils;

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
        
        if(jobDto.getPrice() <= 0)
        	throw new BackendException("Amount can not be less then zero");

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
        System.out.println(user.getWallet());
        if(user.getWallet() < job.getPrice())
        	throw new BackendException("Insufficient Balance");
        user.setWallet(user.getWallet()-job.getPrice());
        
        Transaction transaction = new Transaction();
        transaction.setTransactionCode(Utils.generateRandomTransactionCode(10));
        transaction.setUser(user);
        transaction.setJob(job);
        transaction.setPrice(job.getPrice() * (-1));
        
        user.getTransactions().add(transaction);
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
        
        if(job.getCreator().getEmail().equals(user.getEmail()))
        	throw new BackendException("You cannot apply to this job");
        
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
	public Object assignedUserForJob(String email, Long jobId) {
		User user = userRepo.findByEmail(email).orElseThrow(()-> new BackendException("User Not Found"));
		Job job = jobRepo.findById(jobId).orElseThrow(()-> new BackendException("Job Not Found"));
		
		if(job.getAssignedUser() != null)
			throw new BackendException("A User Already Assigned");
		
		if(!job.getAppliedUsers().contains(user))
			throw new BackendException("User Cannot be Assigned");
		
		Notification notification = new Notification();
		notification.setMessage("A Job is Assigned");
		notification.setUser(user);
		notification.setJob(job);
		List<Notification> notificatioinList = user.getNotificationList();
		notificatioinList.add(notification);
		
		
		job.setAssignedUser(user);
		job.setStatus(JobStatus.ASSIGNED);
		List<Job> assignedJobs = user.getAssignedJobs();
		assignedJobs.add(job);
		user.setAssignedJobs(assignedJobs);
		userRepo.save(user);
		
		return "Job Assigned";
	}


	public Object jobComplete(Long jobId) {
		User user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new BackendException("User not Found"));
        }
        
        Job job = jobRepo.findById(jobId).orElseThrow(()-> new BackendException("Job Not Found"));
        if(!user.getJobsPosted().contains(job))
        	throw new BackendException("You Cannot the job status");
        
        job.setStatus(JobStatus.COMPLETED);
        
        // Set Notification for the job poster
        Notification notification = new Notification();
        notification.setMessage("A Job is Completed");
        notification.setJob(job);
        notification.setUser(user);
        List<Notification> posterNotifications = user.getNotificationList();
        posterNotifications.add(notification);
        user.setNotificationList(posterNotifications);
        userRepo.save(user);
        
        // Set Notification for the assigned user
        User assignedUser = job.getAssignedUser();
        Notification notification2 = new Notification();
        notification2.setMessage("A Job is Completed");
        notification2.setJob(job);
        notification2.setUser(assignedUser);
        List<Notification> assignedUserNotifications = assignedUser.getNotificationList();
        assignedUserNotifications.add(notification2);
        assignedUser.setNotificationList(assignedUserNotifications);
        
        assignedUser.setWallet(assignedUser.getWallet() + job.getPrice());
        
        Transaction transaction = new Transaction();
        transaction.setJob(job);
        transaction.setTransactionCode(Utils.generateRandomTransactionCode(10));
        transaction.setUser(assignedUser);
        transaction.setPrice(job.getPrice());
        assignedUser.getTransactions().add(transaction);
        
        
        userRepo.save(assignedUser);
        
        return "Job Completed";
        
	} 

	
}
