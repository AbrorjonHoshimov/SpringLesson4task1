package com.example.test.repostory;

import com.example.test.entity.Income;
import com.example.test.projection.ProjectionInOut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepostory extends JpaRepository<Income,Integer> {

    List<ProjectionInOut>findAllByToCard_Number(String toCard_number);
}
