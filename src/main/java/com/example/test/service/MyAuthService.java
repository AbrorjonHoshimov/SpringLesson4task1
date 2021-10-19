package com.example.test.service;

import com.example.test.entity.User;
import com.example.test.payload.ApiResponse;
import com.example.test.payload.RegisterDto;
import com.example.test.repostory.UserRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MyAuthService implements UserDetailsService {
    @Autowired
    UserRepostory userRepostory;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse registerUser(RegisterDto registerDto) {
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        boolean exists = userRepostory.existsByPhoneNumber(registerDto.getPhoneNum());
        if (exists)return new ApiResponse("Bunday Telefon Raqam Mavjud",false);
        user.setPhoneNumber(registerDto.getPhoneNum());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEnabled(true);
        userRepostory.save(user);
        return new ApiResponse("Saved",true);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepostory.findByPhoneNumber(s);
        if (byEmail.isPresent()){
            return byEmail.get();
        }
        throw new UsernameNotFoundException(byEmail.get()+" topilmadi");
    }
}
