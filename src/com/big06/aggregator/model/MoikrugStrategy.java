package com.big06.aggregator.model;

import com.big06.aggregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoikrugStrategy implements Strategy
{
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?page=%d&q=java+%s";

    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        int pageNumb = 1;

        try
        {
            while (true)
            {
                Document htmlDoc = getDocument(pageNumb++, searchString);
                if (htmlDoc == null) break;

                //Непонятно, почему эта строчка не работает
                //Elements elements = htmlDoc.select("div#jobs_list div.inner");
                //А эта работает
                Elements elements = htmlDoc.getElementsByClass("job");
                if (elements.size() == 0 || elements.isEmpty()) break;

                for (Element element : elements)
                {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setSiteName(htmlDoc.title());
                    vacancy.setUrl("https://moikrug.ru"+element.select("div.title a").attr("href"));
                    vacancy.setTitle(element.getElementsByClass("title").text());
                    vacancy.setCompanyName(element.getElementsByClass("company_name").text());
                    vacancy.setCity(element.getElementsByClass("location").text());
                    String salary = element.getElementsByClass("salary").text();
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

    protected Document getDocument(int page, String searchString) throws IOException
    {
        String searchURL = String.format(URL_FORMAT, page, searchString);

        return Jsoup.connect(searchURL).userAgent("53.0.2785.116").referrer("http://google.ru").get();
    }
}
