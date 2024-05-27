package com.example.w12exercisespringdataadvancedquerying.repository;

import com.example.w12exercisespringdataadvancedquerying.model.entity.AgeRestriction;
import com.example.w12exercisespringdataadvancedquerying.model.entity.EditionType;

import java.math.BigDecimal;

public interface BookInfo {
    String getTitle();
    EditionType getEditionType();
    AgeRestriction getAgeRestriction();
    BigDecimal getPrice();

}
