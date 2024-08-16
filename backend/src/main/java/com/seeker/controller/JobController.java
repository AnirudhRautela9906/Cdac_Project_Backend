package com.seeker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeker.dto.job.JobDTO;
import com.seeker.dto.remaining.AddressDTO;
import com.seeker.services.JobServices;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/seeker/job")
public class JobController {
	
	@Autowired
	private JobServices jobSer;

	@GetMapping
	public ResponseEntity<?> getAllJobs() {
		return ResponseEntity.status(HttpStatus.OK).body(jobSer.getAllJobs());
	}
	
	
	@PostMapping("/search")
	public ResponseEntity<?> getJobsAtMyCity(@Valid @RequestBody AddressDTO addressDto) {
		return ResponseEntity.status(HttpStatus.OK).body(jobSer.getJobsAtMyCity(addressDto));
	}
	
//	@GetMapping("/loadAreaDropDown")
//	public ResponseEntity<?> getListOfDistinctArea(@Valid @RequestBody AddressDTO addressDto) {
//		return ResponseEntity.status(HttpStatus.OK).body(jobSer.getListOfDistinctArea(addressDto));
//	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createJob(@Valid @RequestBody JobDTO jobDto, HttpServletResponse response) {
		return ResponseEntity.status(HttpStatus.CREATED).body(jobSer.createJob(jobDto, response));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getJob(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(jobSer.getJob(id));
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateJob(@Valid @PathVariable String id, @RequestBody JobDTO jobDto) {
		return ResponseEntity.status(HttpStatus.OK).body(jobSer.updateJob(id, jobDto));
	}
	
	@PutMapping("/apply/{id}")
	public ResponseEntity<?> applyJob(@Valid @PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(jobSer.applyJob(id));
	}
	
	@PutMapping("/assign/{email}/{jobId}")
	public ResponseEntity<?> assignUserForJob(@PathVariable String email, @PathVariable Long jobId){
		return ResponseEntity.status(HttpStatus.OK).body(jobSer.assignedUserForJob(email, jobId));
	}
	
	@PutMapping("/job-completed/{jobId}")
	public ResponseEntity<?> jobCompleted(@PathVariable Long jobId){
		return ResponseEntity.status(HttpStatus.OK).body(jobSer.jobComplete(jobId));
	}
	
	
}
