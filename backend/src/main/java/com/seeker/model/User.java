package com.seeker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    private String email;

    private String name;
        
    private String password;

    private String aadhar;

    private Integer age;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Job> jobsApplied = new ArrayList<>();

    @OneToMany(mappedBy = "creator", fetch =  FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Job> jobsPosted = new ArrayList<>();

    
    @OneToMany(mappedBy = "assignedUser", fetch =  FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Job> assignedJobs = new ArrayList<>();
    
    
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    private Address address;
    
    
    private String phoneNumber;
    
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Notification> notificationList = new ArrayList<Notification>();
    
    private Double wallet = 1000.0;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<Transaction>(); 

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
