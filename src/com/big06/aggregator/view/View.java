package com.big06.aggregator.view;

import com.big06.aggregator.Controller;
import com.big06.aggregator.vo.Vacancy;

import java.util.List;

public interface View
{
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
