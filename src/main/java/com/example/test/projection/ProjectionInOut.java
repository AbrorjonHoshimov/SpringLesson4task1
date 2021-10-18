package com.example.test.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ProjectionInOut {

    @Value("#{target.toCard.username+' '+ target.toCard.number}")
    String gettoCard();

    @Value("#{target.fromCard.username+' '+ target.fromCard.number}")
    String getfromCard();
}
