package com.auth_nov_service.controller;

import com.auth_nov_service.dto.ApiResponse;
import com.auth_nov_service.dto.LoginDto;
import com.auth_nov_service.dto.UserDto;
import com.auth_nov_service.entity.User;
import com.auth_nov_service.repository.UserRepository;
import com.auth_nov_service.service.JwtService;
import com.auth_nov_service.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
//@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private UserService userService;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthController(UserService userService, UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }



    @PostMapping("/store_signup")
    public ResponseEntity<ApiResponse<String>> newStore(@RequestBody UserDto userDto) {

        ApiResponse<String> response = new ApiResponse<>();

        if (userRepository.existsByEmail(userDto.getEmail())) {
            response.setMessage("error");
            response.setStatus(400);
            response.setData("Email already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            response.setMessage("error");
            response.setStatus(400);
            response.setData("Username already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Save only after validation
        userService.addUser(userDto,"ROLE_STORE");

        response.setMessage("success");
        response.setStatus(201);
        response.setData("Registration successful");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/customer_signup")
    public ResponseEntity<ApiResponse<String>> newCustomer(@RequestBody UserDto userDto) {

        ApiResponse<String> response = new ApiResponse<>();

        if (userRepository.existsByEmail(userDto.getEmail())) {
            response.setMessage("error");
            response.setStatus(400);
            response.setData("Email already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            response.setMessage("error");
            response.setStatus(400);
            response.setData("Username already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Save only after validation
        userService.addUser(userDto,"ROLE_CUSTOMER");

        response.setMessage("success");
        response.setStatus(201);
        response.setData("Registration successful");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        ApiResponse<String> response=new ApiResponse<>();
        if(authenticate.isAuthenticated()){
            String jwtToken=jwtService.generateToken(
                    loginDto.getUsername(),
                    authenticate.getAuthorities().iterator().next().getAuthority()
                    );
            response.setData(jwtToken);
            response.setMessage("token generated");
            response.setStatus(201);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setData("unauthenticated");
        response.setMessage("failed");
        response.setStatus(401);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @GetMapping("/get-user")
    public User getUser(@RequestParam("username") String username, @RequestHeader("Authorization") String token){
        return userRepository.findByUsername(username);
    }

}
