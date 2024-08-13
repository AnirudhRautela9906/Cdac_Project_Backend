package com.seeker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.seeker.model.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {

//    @Query("SELECT DISTINCT a.area FROM Address a WHERE a.state = :state AND a.city = :city")
//    List<String> findDistinctAreaByStateAndCity(@Param("state") String state, @Param("city") String city);

}
