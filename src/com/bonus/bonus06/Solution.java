package com.bonus.bonus06;

import java.util.ArrayList;
import java.util.List;

/* Кроссворд
1. Дан двумерный массив, который содержит буквы английского алфавита в нижнем регистре.
2. Метод detectAllWords должен найти все слова из words в массиве crossword.
3. Элемент(startX, startY) должен соответствовать первой букве слова, элемент(endX, endY) - последней.
text - это само слово, располагается между начальным и конечным элементами
4. Все слова есть в массиве.
5. Слова могут быть расположены горизонтально, вертикально и по диагонали как в нормальном, так и в обратном порядке.
6. Метод main не участвует в тестировании
*/
public class Solution
{
    public static void main(String[] args)
    {
        int[][] crossword = new int[][]
        {
                {'f', 'd', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'l', 'p', 'r', 'r', 'h'},
                {'p', 'o', 'e', 'e', 'j', 'j'}
        };

        List<Word> list = detectAllWords(crossword, "home", "same", "rr", "r");
        for (Word word : list) {System.out.println(word);}


        /*
        Ожидаемый результат
        home - (5, 3) - (2, 0)
        same - (1, 1) - (4, 1)
        */
    }

    public static List<Word> detectAllWords(int[][] crossword, String... words)
    {
        ArrayList<Word> list = new ArrayList<>();
        for (String oneWord : words)
        {
            for (int y = 0; y < crossword.length; y++)
            {
                for (int x = 0; x < crossword[y].length; x++)
                {
                    Word word = new Word(oneWord);
                    char[] spelledWord = oneWord.toCharArray();
                    char firstChar = spelledWord[0];

                    if (firstChar == crossword[y][x])
                    {
                        if (spelledWord.length < 2)
                        {
                            word.setStartPoint(x, y);
                            word.setEndPoint(x, y);
                            list.add(word);
                        }

                        if (spelledWord.length > 1)
                        {
                            //Сканируем "Северную" сторону
                            try
                            {
                                for (int m = 0; m < spelledWord.length; m++)
                                {
                                    if (crossword[y - m][x] != spelledWord[m]) {throw new IndexOutOfBoundsException();}

                                    if (m == spelledWord.length - 1)
                                    {
                                        word.setStartPoint(x, y);
                                        word.setEndPoint(x, y - m);
                                        list.add(word);
                                    }
                                }
                            }
                            catch (IndexOutOfBoundsException e) {}


                            //Сканируем "Южную" сторону
                            try
                            {
                                for (int m = 0; m < spelledWord.length; m++)
                                {
                                    if (crossword[y + m][x] != spelledWord[m]) {throw new IndexOutOfBoundsException();}

                                    if (m == spelledWord.length - 1)
                                    {
                                        word.setStartPoint(x, y);
                                        word.setEndPoint(x, y + m);
                                        list.add(word);
                                    }
                                }
                            }
                            catch (IndexOutOfBoundsException e) {}


                            //Сканируем "Восточную" сторону
                            try
                            {
                                for (int m = 0; m < spelledWord.length; m++)
                                {
                                    if (crossword[y][x + m] != spelledWord[m]) {throw new IndexOutOfBoundsException();}

                                    if (m == spelledWord.length - 1)
                                    {
                                        word.setStartPoint(x, y);
                                        word.setEndPoint(x + m, y);
                                        list.add(word);
                                    }
                                }
                            }
                            catch (IndexOutOfBoundsException e) {}


                            //Сканируем "Западную" сторону
                            try
                            {
                                for (int m = 0; m < spelledWord.length; m++)
                                {
                                    if (crossword[y][x - m] != spelledWord[m]) {throw new IndexOutOfBoundsException();}

                                    if (m == spelledWord.length - 1)
                                    {
                                        word.setStartPoint(x, y);
                                        word.setEndPoint(x - m, y);
                                        list.add(word);
                                    }
                                }
                            }
                            catch (IndexOutOfBoundsException e) {}


                            //Сканируем "Северо-Западную" сторону
                            try
                            {
                                for (int m = 0; m < spelledWord.length; m++)
                                {
                                    if (crossword[y - m][x - m] != spelledWord[m]) {throw new IndexOutOfBoundsException();}

                                    if (m == spelledWord.length - 1)
                                    {
                                        word.setStartPoint(x, y);
                                        word.setEndPoint(x - m, y - m);
                                        list.add(word);
                                    }
                                }
                            }
                            catch (IndexOutOfBoundsException e) {}


                            //Сканируем "Северо-Восточную" сторону
                            try
                            {
                                for (int m = 0; m < spelledWord.length; m++)
                                {
                                    if (crossword[y - m][x + m] != spelledWord[m]) {throw new IndexOutOfBoundsException();}

                                    if (m == spelledWord.length - 1)
                                    {
                                        word.setStartPoint(x, y);
                                        word.setEndPoint(x + m, y - m);
                                        list.add(word);
                                    }
                                }
                            }
                            catch (IndexOutOfBoundsException e) {}


                            //Сканируем "Юго-Западную" сторону
                            try
                            {
                                for (int m = 0; m < spelledWord.length; m++)
                                {
                                    if (crossword[y + m][x - m] != spelledWord[m]) {throw new IndexOutOfBoundsException();}

                                    if (m == spelledWord.length - 1)
                                    {
                                        word.setStartPoint(x, y);
                                        word.setEndPoint(x - m, y + m);
                                        list.add(word);
                                    }
                                }
                            }
                            catch (IndexOutOfBoundsException e) {}


                            //Сканируем "Юго-Восточную" сторону
                            try
                            {
                                for (int m = 0; m < spelledWord.length; m++)
                                {
                                    if (crossword[y + m][x + m] != spelledWord[m]) {throw new IndexOutOfBoundsException();}

                                    if (m == spelledWord.length - 1)
                                    {
                                        word.setStartPoint(x, y);
                                        word.setEndPoint(x + m, y + m);
                                        list.add(word);
                                    }
                                }
                            }
                            catch (IndexOutOfBoundsException e) {}
                        }
                    }
                }
            }
        }

        return list;
    }

    public static class Word
    {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text)
        {
            this.text = text;
        }

        public void setStartPoint(int i, int j)
        {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j)
        {
            endX = i;
            endY = j;
        }

        @Override
        public String toString()
        {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }
}