package com.seeker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeker.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByEmail(String email);
}
