package com.seeker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeker.model.Notification;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

}
