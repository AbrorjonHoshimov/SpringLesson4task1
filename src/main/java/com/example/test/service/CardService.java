package com.example.test.service;


import com.example.test.entity.Card;
import com.example.test.entity.Income;
import com.example.test.entity.Outcome;
import com.example.test.payload.ApiResponse;
import com.example.test.payload.IncomeOutDto;
import com.example.test.repostory.CardRepostory;
import com.example.test.repostory.IncomeRepostory;
import com.example.test.repostory.OutcomeRepostory;
import com.example.test.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardService {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    CardRepostory cardRepostory;

    @Autowired
    IncomeRepostory incomeRepostory;

    @Autowired
    OutcomeRepostory outcomeRepostory;

    public ApiResponse addCard(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
            String user = jwtProvider.getUSerFromToken(token);
            Card card = new Card();
            card.setUsername(user);
            card.setBalance(0);
            String ranNum = UUID.randomUUID().toString();
            ranNum = ranNum.substring(0, 4);
            card.setNumber(ranNum);
            card.setExperidDate(new Date(System.currentTimeMillis() + (1000 * 86400 * 365 * 2)));
            cardRepostory.save(card);
            return new ApiResponse("Saved", true);
        }
        return new ApiResponse("Xatolik", false);
    }

    public ApiResponse in_outHistory(HttpServletRequest httpServletRequest, IncomeOutDto incomeDto) {
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
            String user = jwtProvider.getUSerFromToken(token);
            Optional<Card> byUsername = cardRepostory.findByUsername(user);
            Card from = byUsername.get();
            if (from.getNumber().equals(incomeDto.getFrom())) {
                Outcome outcome = new Outcome();
                outcome.setFromCard(cardRepostory.findByNumber(incomeDto.getFrom()).get());
                outcome.setToCard(cardRepostory.findByNumber(incomeDto.getTo()).get());
                outcome.setAmount(incomeDto.getAmount());
                outcome.setDate(new java.util.Date());
                double summa = incomeDto.getAmount() + (incomeDto.getAmount() * 1 / 100);
                if (summa > from.getBalance()) {
                    return new ApiResponse("hisobingizda mablag yetarli emas", false);
                }
                outcome.setComissionAmount(summa);
                from.setBalance(from.getBalance() - summa);
                cardRepostory.save(from);
                Optional<Card> tocardOption = cardRepostory.findByNumber(incomeDto.getTo());
                Card toCard = tocardOption.get();
                toCard.setBalance(toCard.getBalance() + incomeDto.getAmount());
                cardRepostory.save(toCard);

                Income income = new Income();
                income.setFromCard(cardRepostory.findByNumber(incomeDto.getTo()).get());
                income.setToCard(cardRepostory.findByNumber(incomeDto.getFrom()).get());
                income.setAmount(incomeDto.getAmount());
                income.setDate(new java.util.Date());

                outcomeRepostory.save(outcome);
                incomeRepostory.save(income);
                return new ApiResponse("Ok", true);
            }
        }
        return new ApiResponse("Xatolik", false);
    }
}