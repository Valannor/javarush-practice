package com.bonus.bonus03;

/* Знакомство с тегами
Считайте с консоли имя файла, который имеет HTML-формат
Пример:
Info about Leela <span xml:lang="en" lang="en"><b><span>Turanga Leela
</span></b></span><span>Super</span><span>girl</span>
Первым параметром в метод main приходит тег. Например, "span"
Вывести на консоль все теги, которые соответствуют заданному тегу
Каждый тег на новой строке, порядок должен соответствовать порядку следования в файле
Количество пробелов, \n, \r не влияют на результат
Файл не содержит тег CDATA, для всех открывающих тегов имеется отдельный закрывающий тег, одиночных тегов нету
Тег может содержать вложенные теги
Пример вывода:
<span xml:lang="en" lang="en"><b><span>Turanga Leela</span></b></span>
<span>Turanga Leela</span>
<span>Super</span>
<span>girl</span>

Шаблон тега:
<tag>text1</tag>
<tag text2>text1</tag>
<tag
text2>text1</tag>

text1, text2 могут быть пустыми
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Solution
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader stream = new BufferedReader(new FileReader(reader.readLine()));

        //Создаем переменные Tag
        String tagName = args[0];
        String openTag = "<" + tagName;
        String closeTag = "</" + tagName + ">";

        //Считываем данные
        String line = "";
        while (stream.ready())
        {
            int data = stream.read();
            line += ((char)data);
        }

        //Заменяем все Tag на скобки
        String searchLine = line.substring(line.indexOf(openTag));
        searchLine = searchLine.replaceAll("\r\n", "");
        searchLine = searchLine.replaceAll(openTag, "`");
        searchLine = searchLine.replaceAll(closeTag, "+");

        HashMap<Integer, Integer> open = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> close = new HashMap<Integer, Integer>();
        ArrayList<Integer> counted = new ArrayList<Integer>();
        int openCount = 0;
        char[] chars = searchLine.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            char c = chars[i];
            if (c == '`')
            {
                openCount++;
                open.put(openCount, i);
                counted.add(openCount);
            }
            else if (c == '+')
            {
                int closeCount = counted.get(counted.size() - 1);
                close.put(closeCount, i);
                counted.remove(counted.size() - 1);
            }
        }

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < open.size(); i++)
        {
            String found = searchLine.substring(open.get(i + 1), close.get(i + 1));
            list.add(found);
        }

        ArrayList<String> printLines = new ArrayList<String>();
        for (String print : list)
        {
            char[] chars1 = print.toCharArray();
            String a = "";
            for (char c : chars1)
            {
                if (c == '`') a += openTag;
                else if (c == '+') a += closeTag;
                else a += c;
            }
            a += closeTag;
            printLines.add(a);
        }

        for (String a : printLines)
        {
            System.out.println(a);
        }

        reader.close();
        stream.close();
    }
}
