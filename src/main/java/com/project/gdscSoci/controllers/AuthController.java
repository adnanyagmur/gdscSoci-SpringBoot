package com.project.gdscSoci.controllers;

import com.project.gdscSoci.Dto.UserLoginRequest;
import com.project.gdscSoci.Dto.UserRegisterRequest;
import com.project.gdscSoci.entities.User;
import com.project.gdscSoci.security.JwtTokenProvider;
import com.project.gdscSoci.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider, UserService
                                  userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest loginRequest){

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUserName(), loginRequest.getPassword());
            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwtToken = jwtTokenProvider.generateJwtToken(auth);

            return "Bearer " + jwtToken;

    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest registerRequest){
        if(userService.getOneUserByUserName(registerRequest.getUserName()) != null ){
            return new ResponseEntity<>("Username already in use.", HttpStatus.BAD_REQUEST);
        }else{
            User user = new User();
            user.setUserName(registerRequest.getUserName());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            userService.saveOneUser(user);
            return new ResponseEntity<>("User successfully registered", HttpStatus.CREATED);
        }
    }
}
