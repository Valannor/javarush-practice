package com.big06.aggregator;

import com.big06.aggregator.model.Model;

public class Controller
{
    Model model;

    public Controller(Model model)
    {
        if (model == null)
            throw new IllegalArgumentException();

        this.model = model;
    }

    public void onCitySelect(String cityName)
    {
        model.selectCity(cityName);
    }
}
