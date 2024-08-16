package com.seeker.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "jobs")
@ToString
public class Job {
    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    
    private String title;
    
    private String longDesc;

    @ManyToOne
    @JoinColumn(name = "creator_email")
    private User creator;

    private Double price;

    @OneToOne(cascade = CascadeType.ALL ,mappedBy = "jobId")
    private Address jobLocation;

    @ManyToOne
    @JoinColumn(name = "assigned_user_email")
    private User assignedUser;

    @Column(name = "post_time")
    private LocalDateTime jobPostDateTime = LocalDateTime.now();

    @ManyToMany(mappedBy = "jobsApplied", fetch = FetchType.EAGER)
    private List<User> appliedUsers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private JobStatus status= JobStatus.POSTED;
    
//    @ElementCollection
//    @CollectionTable(name = "image_list", joinColumns = @JoinColumn(name = "job_id"))
//    @Column(name = "image")
//    private List<String> images = new ArrayList<String>();
}
