package com.projects.task_management.dto;

import lombok.Data;

@Data

public class SignupRequest {
    private String name;
    private String email;
    private String password;
    public SignupRequest() {
		// TODO Auto-generated constructor stub
	}
	public SignupRequest(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    

}
