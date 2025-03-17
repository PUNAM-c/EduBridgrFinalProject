package com.projects.task_management.dto;

import com.projects.task_management.enums.UserRole;
import lombok.Data;

@Data

public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private UserRole userRole;
    public AuthenticationResponse() {
		// TODO Auto-generated constructor stub
	}
	public AuthenticationResponse(String jwt, Long userId, UserRole userRole) {
		super();
		this.jwt = jwt;
		this.userId = userId;
		this.userRole = userRole;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
    
}
