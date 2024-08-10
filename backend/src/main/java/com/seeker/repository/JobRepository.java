package com.seeker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seeker.model.Job;

public interface JobRepository extends JpaRepository<Job, String> {

}
