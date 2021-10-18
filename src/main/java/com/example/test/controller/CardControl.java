package com.example.test.controller;


import com.example.test.payload.ApiResponse;
import com.example.test.payload.IncomeOutDto;
import com.example.test.projection.ProjectionInOut;
import com.example.test.repostory.CardRepostory;
import com.example.test.repostory.IncomeRepostory;
import com.example.test.repostory.OutcomeRepostory;
import com.example.test.security.JwtProvider;
import com.example.test.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/card")
public class CardControl {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    OutcomeRepostory outcomeRepostory;
    @Autowired
    IncomeRepostory incomeRepostory;
    @Autowired
    CardService cardService;
    @Autowired
    CardRepostory cardRepostory;

    @PostMapping
    public HttpEntity<?> add(HttpServletRequest httpRequest) {
        ApiResponse apiResponse = cardService.addCard(httpRequest);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> in_out(HttpServletRequest httpServletRequest, @RequestBody IncomeOutDto incomeDto) {
        ApiResponse apiResponse = cardService.in_outHistory(httpServletRequest, incomeDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/search")
    public HttpEntity<?> getByNumberIncome(@RequestParam String number) {
        List<ProjectionInOut> byToCard_number = incomeRepostory.findAllByToCard_Number(number);
        return ResponseEntity.ok(byToCard_number);

    }

    @GetMapping
    public HttpEntity<?> getByNumberOut(@RequestParam String number) {
        List<ProjectionInOut> allByToCard_number = outcomeRepostory.findAllByToCard_Number(number);
        return ResponseEntity.ok(allByToCard_number);

    }
}
