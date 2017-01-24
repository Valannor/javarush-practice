package com.big06.aggregator.model;

import com.big06.aggregator.vo.Vacancy;

import java.util.List;

public interface Strategy
{
    List<Vacancy> getVacancies(String searchString);
}
