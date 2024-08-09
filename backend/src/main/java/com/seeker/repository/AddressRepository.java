package com.seeker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seeker.model.User;

public interface AddressRepository extends JpaRepository<User, Long> {

	
}
