package com.big06.aggregator.model;

import com.big06.aggregator.view.View;
import com.big06.aggregator.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class Model
{
    View view;
    Provider[] providers;

    public Model(View view, Provider... providers)
    {
        if (view == null || providers == null || providers.length == 0)
            throw new IllegalArgumentException();

        this.view = view;
        this.providers = providers;
    }

    public void selectCity(String city)
    {
        List<Vacancy> vacancies = new ArrayList<>();

        for (Provider provider : providers)
            vacancies.addAll(provider.getJavaVacancies(city));

        view.update(vacancies);
    }
}
