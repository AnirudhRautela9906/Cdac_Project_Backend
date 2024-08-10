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

import com.seeker.dto.JobDTO;
import com.seeker.dto.UserDTO;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getJob(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(jobSer.getJob(id));
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createJob(@Valid @RequestBody JobDTO jobDto, HttpServletResponse response) {
		return ResponseEntity.status(HttpStatus.CREATED).body(jobSer.createJob(jobDto, response));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateJob(@Valid @PathVariable String id, @RequestBody JobDTO jobDto) {
		return ResponseEntity.status(HttpStatus.OK).body(jobSer.updateJob(id, jobDto));
	}
	
	
}
