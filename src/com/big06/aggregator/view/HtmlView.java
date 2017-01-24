package com.big06.aggregator.view;

import com.big06.aggregator.Controller;
import com.big06.aggregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.List;

public class HtmlView implements View
{
    Controller controller;
    private final String filePath = "./src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies)
    {
        try
        {
            updateFile(getUpdatedFileContent(vacancies));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Some exception occurred");
        }
    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod()
    {
        controller.onCitySelect("Sankt-Peterburg");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) throws IOException
    {
        //Получаем объект
        Document document = getDocument();

        //Создаем шаблон
        Element element = document.getElementsByClass("template").first();
        Element templateElement = element.clone();
        templateElement.removeClass("template").removeAttr("style");

        //Удаляем все ранее добавленные вакансии
        document.getElementsByAttributeValue("class", "vacancy").remove();

        //Добавляем данные в шаблон
        for (Vacancy vacancy : vacancies)
        {
            Element cloneElement = templateElement.clone();
            cloneElement.getElementsByAttributeValue("class", "city").get(0).text(vacancy.getCity());
            cloneElement.getElementsByAttributeValue("class", "companyName").get(0).text(vacancy.getCompanyName());
            cloneElement.getElementsByAttributeValue("class", "salary").get(0).text(vacancy.getSalary());
            cloneElement.getElementsByTag("a").get(0).text(vacancy.getTitle()).attr("href", vacancy.getUrl());
            element.before(cloneElement.outerHtml());
        }

        return document.html();
    }

    private void updateFile(String data) throws IOException
    {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(data.getBytes());
    }

    protected Document getDocument() throws IOException
    {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }
}
