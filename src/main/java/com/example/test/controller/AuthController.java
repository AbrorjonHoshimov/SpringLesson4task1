package com.example.test.controller;


import com.example.test.entity.User;
import com.example.test.payload.ApiResponse;
import com.example.test.payload.LoginDto;
import com.example.test.payload.RegisterDto;
import com.example.test.repostory.UserRepostory;
import com.example.test.security.JwtProvider;
import com.example.test.service.MyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    MyAuthService myAuthService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserRepostory repostory;


    @PostMapping("/login")

    public ResponseEntity<?> loginToSystem(@RequestBody LoginDto loginDto) {
//        UserDetails userDetails = myAuthService.loadUserByUsername(loginDto.getUserName());
//        boolean matches = passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword());
//        if (matches){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getPhoneNumber(),
                    loginDto.getPassword()));
            String token = jwtProvider.generatedToken(loginDto.getPhoneNumber());
            return ResponseEntity.ok(token);

        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(401).body("login yoki parol xato");

        }


    }
    @PostMapping("/register")

    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {

        ApiResponse apiResponse = myAuthService.registerUser(registerDto);

        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }
    @GetMapping("/list")
    public HttpEntity<?>list(){
        List<User> all = repostory.findAll();
        return ResponseEntity.ok(all);
    }
}
