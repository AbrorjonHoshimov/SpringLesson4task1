package com.example.test.repostory;


import com.example.test.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepostory extends JpaRepository<Card,Integer> {

    Optional<Card> findByUsername(String username);

    Optional<Card> findByNumber(String card);
}
