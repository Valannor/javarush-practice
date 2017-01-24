package com.bonus.bonus02;

/* Алгоритмы-прямоугольники
1. Дан двумерный массив N*N, который содержит несколько прямоугольников.
2. Различные прямоугольники не соприкасаются и не накладываются.
3. Внутри прямоугольник весь заполнен 1.
4. В массиве:
4.1) a[i, j] = 1, если элемент (i, j) принадлежит какому-либо прямоугольнику
4.2) a[i, j] = 0, в противном случае
5. getRectangleCount должен возвращать количество прямоугольников.
6. Метод main не участвует в тестировании
*/
public class Solution
{
    public static void main(String[] args)
    {
        byte[][] a = new byte[][]
        {
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 1}
        };
        int count = getRectangleCount(a);
        System.out.println("count = " + count + ". Должно быть 2");

        a = new byte[][]
        {
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 0, 1}
        };
        count = getRectangleCount(a);
        System.out.println("Count = " + count + ". Должно быть 3");

        a = new byte[][]
        {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {1, 1, 0, 0}
        };
        count = getRectangleCount(a);
        System.out.println("Count = " + count + ". Должно быть 1");

        a = new byte[][]
        {
                {1, 1, 0, 0, 0},
                {1, 1, 0, 1, 1},
                {1, 1, 0, 0, 0},
                {1, 1, 0, 0, 1},
                {0, 0, 0, 0, 1}
        };
        count = getRectangleCount(a);
        System.out.println("Count = " + count + ". Должно быть 3");
    }

    public static int getRectangleCount(byte[][] a)
    {
        int count = 0;
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a.length; j++)
            {
                if (a[i][j] == 1)
                {
                    if ((i != 0 && j != 0) && a[i - 1][j - 1] == 0 && a[i - 1][j] == 0 && a[i][j - 1] == 0) count++;
                    else if (i == 0 && j == 0) count++;
                    else if ((i != 0 && j == 0) && a[i - 1][j] == 0) count++;
                    else if ((i == 0 && j != 0) && a[i][j - 1] == 0) count++;
                }
            }
        }

        return count;
    }
}
