package com.big06.aggregator;

import com.big06.aggregator.model.HHStrategy;
import com.big06.aggregator.model.Model;
import com.big06.aggregator.model.MoikrugStrategy;
import com.big06.aggregator.model.Provider;
import com.big06.aggregator.view.HtmlView;

public class Aggregator
{
    public static void main(String[] args)
    {
        Provider provider1 = new Provider(new HHStrategy());
        Provider provider2 = new Provider(new MoikrugStrategy());
        Provider[] providers = {provider1, provider2};

        HtmlView view = new HtmlView();
        Model model = new Model(view, providers);
        Controller controller = new Controller(model);

        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
