package com.projects.task_management.Controller.auth;


import com.projects.task_management.dto.AuthenticationRequest;
import com.projects.task_management.dto.AuthenticationResponse;
import com.projects.task_management.dto.SignupRequest;
import com.projects.task_management.dto.UserDto;
import com.projects.task_management.entities.User;
import com.projects.task_management.repositories.UserRepository;
import com.projects.task_management.services.auth.AuthService;
import com.projects.task_management.services.jwt.UserService;
import com.projects.task_management.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")

public class AuthController {

    private final AuthService authService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;


    public AuthController(AuthService authService, UserRepository userRepository, JwtUtil jwtUtil,
			UserService userService, AuthenticationManager authenticationManager) {
		super();
		this.authService = authService;
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if(authService.hasUserWithEmail(signupRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("user already exists with the given email");
        UserDto createdUserDto = authService.signupUser(signupRequest);

        if(createdUserDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);

    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;
    }

}
