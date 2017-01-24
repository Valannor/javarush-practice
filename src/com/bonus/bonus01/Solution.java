package com.bonus.bonus01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* Отслеживаем изменения
Считать в консоли 2 имени файла - file1, file2.
Файлы содержат строки, file2 является обновленной версией file1, часть строк совпадают.
Нужно создать объединенную версию строк, записать их в список lines
Операции ADDED и REMOVED не могут идти подряд, они всегда разделены SAME
Пример:
оригинальный   редактированный    общий
file1:         file2:             результат:(lines)

строка1        строка1            SAME строка1
строка2                           REMOVED строка2
строка3        строка3            SAME строка3
строка4                           REMOVED строка4
строка5        строка5            SAME строка5
               строка0            ADDED строка0
строка1        строка1            SAME строка1
строка2                           REMOVED строка2
строка3        строка3            SAME строка3
               строка5            ADDED строка5
строка4        строка4            SAME строка4
строка5                           REMOVED строка5
*/

public class Solution
{
    public static List<LineItem> lines = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        //Открываем потоки
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader file1 = new BufferedReader(new FileReader(reader.readLine()));
        BufferedReader file2 = new BufferedReader(new FileReader(reader.readLine()));

        //Считываем строки в файлах
        ArrayList<String> fileArray1 = new ArrayList<>();
        ArrayList<String> fileArray2 = new ArrayList<>();
        while (file1.ready()) {fileArray1.add(file1.readLine());}
        while (file2.ready()) {fileArray2.add(file2.readLine());}

        //Закрываем потоки ввода-вывода
        reader.close();
        file1.close();
        file2.close();

        Type removed = Type.REMOVED;
        Type added = Type.ADDED;
        Type same = Type.SAME;

        //ИСПОЛЬЗУЕМ МЕТОД
        compareFiles(fileArray1, fileArray2, lines, same, added, removed);

        //print (TEMP!!!)
        for (LineItem print : lines) {System.out.println(print);}
    }

    public static void compareFiles(ArrayList<String> fileArray1, ArrayList<String> fileArray2, List<LineItem> lines,
                                    Type same, Type added, Type removed)
    {
        //Положение строки SAME в итоговом(выходном) листе
        ArrayList<Integer> inResult = new ArrayList<>();
        //Что предшествовало этому SAME
        ArrayList<String> prevResult = new ArrayList<>();

        //Флаги и маркеры для блоков - 1, 2, 3
        boolean wasPrevA = false;
        boolean wasPrevR = false;
        boolean wasPrevS = false;
        int found = 0;

        //Флаги и маркеры для блоков 4, 5
        ArrayList<String> tempBuffer = new ArrayList<>();
        boolean firstR = false;
        boolean wasExtraR = false;

        A:
        for (int i = 0; i < fileArray1.size(); i++)
        {
            ArrayList<String> mainBuffer = new ArrayList<>();
            String a1 = fileArray1.get(i);
            String a2 = null;
            if (i < (fileArray1.size() - 1)) a2 = fileArray1.get(i + 1);

            String b1 = null;
            String b2 = null;

            if (i <= fileArray1.size() - 1 && found >= fileArray2.size()) {lines.add(new LineItem(removed, a1));}

            if (!firstR)
            {
                //БЛОК №1
                for (int j = found; j < fileArray2.size(); j++)
                {
                    b1 = fileArray2.get(j);
                    if (j < (fileArray2.size() - 1)) b2 = fileArray2.get(j + 1);

                    //ПОДБЛОК №1
                    //Проверка на SAME
                    if (a1.equals(b1))
                    {
                        if (wasPrevA && wasPrevR)
                        {
                            System.out.println("Неправильные файлы! (Блок #1 (подблок 1); SAME)");
                            lines.clear();
                            break A;
                        }
                        else
                        {
                            if (!wasPrevR)
                            {
                                int m = 0;

                                for (; m < mainBuffer.size(); m++) //Спорные строки являются добавленными
                                {
                                    String s = mainBuffer.get(m);
                                    lines.add(new LineItem(added, s));
                                    wasPrevA = true;
                                }

                                mainBuffer.clear();
                            }

                            lines.add(new LineItem(same, a1));
                            inResult.add(lines.size() - 1);
                            if (wasPrevA) prevResult.add("A"); //Теперь знаем, что предшествовало
                            else prevResult.add("E");

                            wasPrevA = false;
                            wasPrevR = false;
                            wasPrevS = true;

                            //Определяем новое значение found
                            found = j + 1;

                            //Если при равенстве a1 и b1, a1 == a2 и b1 != b2
                            if (a1.equals(a2) && !b1.equals(b2) && !(fileArray1.size() < fileArray2.size()))
                            {
                                firstR = true;
                                continue A;
                            }

                            //Если первый список закончился, а в этом еще остались строки - мы их добавляем как ADDED
                            if (i == fileArray1.size() - 1 && found <= fileArray2.size())
                            {
                                for (int n = found; n < fileArray2.size(); n++)
                                {
                                    lines.add(new LineItem(added, fileArray2.get(n)));
                                }
                            }

                            break;
                        }
                    }
                    //ПОДБЛОК №2
                    //Проверка на REMOVED
                    else if (b1.equals(a2)) //&& i != 0
                    {
                        if (wasPrevA)
                        {
                            System.out.println("Неправильные файлы! (Блок #1 (подблок 2); REMOVE - 1)");
                            lines.clear();
                            break A;
                        }
                        else
                        {
                            if (wasPrevS) //Если до этого был SAME
                            {
                                if (a1.equals(b2)) //Если при этом признак того, что имел место ADDED
                                {
                                    lines.add(new LineItem(added, b1));
                                    lines.add(new LineItem(same, b2));

                                    wasPrevA = false;
                                    wasPrevR = false;
                                    wasPrevS = true;
                                    found = found + 2;
                                }
                                else
                                {
                                    lines.add(new LineItem(removed, a1));

                                    wasPrevA = false;
                                    wasPrevR = true;
                                    wasPrevS = false;
                                    found = j;
                                }

                                break;
                            }
                            else
                            {

                                if (j > found) //Если при первой итерации j стал больше found, то здесь блок удалений, а не единичный случай
                                {
                                    lines.add(new LineItem(removed, a1));
                                    firstR = true;
                                    continue A;
                                }

                                lines.add(new LineItem(removed, a1));

                                wasPrevA = false;
                                wasPrevR = true;
                                wasPrevS = false;
                                found = j;

                                continue A;
                            }
                        }
                    }
                    //ПОДБЛОК №3
                    //Добавление в mainBuffer всех спорных строк. Если fileArray2 закончился - то строка a1 была удалена
                    else
                    {
                        mainBuffer.add(b1); //Записываем все в буфер

                        if (a1.equals(b2)) //Все спорные строки - "ДОБАВЛЯЕМЫЕ"
                        {
                            if (wasPrevR)
                            {
                                System.out.println("Неправильные файлы! (Блок #1 (подблок 3); ADD - 1)");
                                lines.clear();
                                break A;
                            }
                            else
                            {
                                wasPrevA = true;
                                wasPrevR = false;
                                wasPrevS = false;
                            }
                        }

                        if (j >= fileArray2.size() - 1) //Строка a1 "УДАЛЯЕМАЯ"
                        {
                            if (mainBuffer.size() > 0 && wasPrevA)
                            {
                                System.out.println("Неправильные файлы! (Блок #1 (подблок 3); REMOVE - 2)");
                                lines.clear();
                                break A;
                            }
                            else
                            {
                                lines.add(new LineItem(removed, a1));
                                wasPrevA = false;
                                wasPrevR = true;
                                wasPrevS = false;

                                if (i == 0) //Если самый первый элемент "УДАЛЯЕМЫЙ", то запускаем алгоритм в БЛОКЕ №4
                                {
                                    firstR = true;
                                    wasExtraR = true;
                                    lines.clear();
                                }
                            }
                        }
                    }
                }

                //БЛОК №2
                if (mainBuffer.size() >= 1) //Если после первой итерации корневого цикла i в буфере остаются строки - они "ДОБАВЛЯЕМЫ" в начале
                {
                    if (i == 0)
                    {
                        if (wasPrevR && !wasExtraR)
                        {
                            System.out.println("Неправильные файлы! (Блок #2; ADD - 2)");
                            lines.clear();
                            break;
                        }
                        else if (!wasExtraR)
                        {
                            for (String tempB2 : mainBuffer) lines.add(0, new LineItem(added, tempB2));

                            wasPrevA = true;
                            wasPrevR = false;
                            wasPrevS = false;
                            found = found + 1;
                            continue;
                        }
                    }
                    else if (b1.equals(a2))
                    {
                        if (wasPrevR && !a1.equals(b2))
                        {
                            int lastSame = 0;
                            String prev = null;
                            if (inResult.size() > 0)
                            {
                                lastSame = inResult.get(inResult.size() - 1);
                                prev = prevResult.get(prevResult.size() - 1);
                            }
                            if (lastSame == 0 || prev.equals("A")) //Такое добавление строк легально, только если SAME предшествовал ADDED
                            {
                                for (String print : mainBuffer) lines.add(lastSame, new LineItem(added, print));
                            }
                            else
                            {
                                System.out.println("Неправильные файлы! (Блок #2; ADD - 3)");
                                lines.clear();
                                break;
                            }
                        }
                        else
                        {
                            for (String tempB2 : mainBuffer) lines.add(i, new LineItem(added, tempB2));

                            wasPrevA = true;
                            wasPrevR = false;
                            wasPrevS = false;
                            found = found + 1;
                            continue;
                        }
                    }
                }
            }

            //БЛОК №4
            if (i >= 0 && firstR) //Если в первой итерации корневого цикла i был выявлен "УДАЛЕННЫЙ" элемент
            {
                if (found == 0) b1 = fileArray2.get(0);
                else if (!(found >= fileArray2.size() - 1)) b1 = fileArray2.get(found);
                else continue;

                if (b1.equals(a1))
                {
                    for (String temp1 : tempBuffer) lines.add(new LineItem(removed, temp1));
                    lines.add(new LineItem(same, a1));
                    found++;

                    wasPrevA = false;
                    wasPrevR = false;
                    wasPrevS = true;
                    firstR = false;

                    tempBuffer.clear();
                }
                else if ((i == fileArray1.size() - 1)) //Если файлы некорректны
                {
                    System.out.println("Неправильные файлы! (Блок #4)");
                    lines.clear();
                }
                else tempBuffer.add(a1);
            }
        }
    }

    public enum Type
    {
        ADDED,
        REMOVED,
        SAME
    }

    public static class LineItem
    {
        public Type type;
        public String line;

        public LineItem(Type type, String line)
        {
            this.type = type;
            this.line = line;
        }

        public String toString()
        {
            if (this.type == Type.ADDED)
            {
                return "ADDED " + this.line;
            } else if (this.type == Type.REMOVED)
            {
                return "REMOVED " + this.line;
            } else
            {
                return "SAME " + this.line;
            }
        }
    }
}
