package com.big06.aggregator.model;

import com.big06.aggregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy
{
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        int pageNumb = 0;

        try
        {
            while (true)
            {
                Document htmlDoc = getDocument(searchString, pageNumb++);
                if (htmlDoc == null) break;

                Elements elements = htmlDoc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (elements.size() == 0) break;

                for (Element element : elements)
                {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setSiteName(htmlDoc.title());
                    vacancy.setUrl(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));
                    vacancy.setTitle(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text());
                    String salary = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text();
                    if (salary == null) salary = "";
                    vacancy.setSalary(salary);

                    vacancies.add(vacancy);
                }
            }
        }
        catch (IOException ignore)
        {
        }

        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException
    {
        String searchURL = String.format(URL_FORMAT, searchString, page);

        return Jsoup.connect(searchURL).userAgent("52.0.2743.116").referrer("http://google.ru").get();
    }
}
