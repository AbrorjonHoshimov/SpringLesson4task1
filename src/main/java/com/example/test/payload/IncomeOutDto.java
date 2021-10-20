package com.example.test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncomeOutDto {
    @NotNull
    private String from;
    @NotNull
    private String to;
    @NotNull
    private double amount;
}
