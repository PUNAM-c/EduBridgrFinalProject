package com.projects.task_management.services.auth;

import com.projects.task_management.dto.SignupRequest;
import com.projects.task_management.dto.UserDto;

public interface AuthService {
    UserDto signupUser(SignupRequest signupRequest);
    boolean hasUserWithEmail(String email);
}
