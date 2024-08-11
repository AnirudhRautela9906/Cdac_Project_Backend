package com.seeker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seeker.model.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {

	
}
