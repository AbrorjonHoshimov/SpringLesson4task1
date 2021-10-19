package com.example.test.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String phoneNumber;
    private String password;
}
