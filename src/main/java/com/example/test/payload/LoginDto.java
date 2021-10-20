package com.example.test.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;
}
