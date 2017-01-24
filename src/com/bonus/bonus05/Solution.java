package com.bonus.bonus05;

import java.util.ArrayList;
import java.util.List;

/* Алгоритмы-числа
Число S состоит из M чисел, например, S=370 и M(количество цифр)=3
Реализовать логику метода getNumbers, который должен среди натуральных чисел меньше N (long)
находить все числа, удовлетворяющие следующему критерию:
число S равно сумме его цифр, возведенных в M степень
getNumbers должен возвращать все такие числа в порядке возрастания

Пример искомого числа:
370 = 3*3*3 + 7*7*7 + 0*0*0
8208 = 8*8*8*8 + 2*2*2*2 + 0*0*0*0 + 8*8*8*8

На выполнение дается 10 секунд и 50 МБ памяти.
*/
public class Solution
{
    public static void main(String[] args)
    {
        Long t0 = System.currentTimeMillis();
        int n = 370;
        int[] numbers = getNumbers(n);
        Long t1 = System.currentTimeMillis();
        System.out.println("time: " + (t1 - t0) / 1000d + " sec");
        System.out.println("memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + " mb");
        for (int i = 0; i < numbers.length; i++) {System.out.print(numbers[i] + ", ");}
        System.out.println();
    }

    public static int[] getNumbers(int N)
    {
        List<Integer> list = new ArrayList<>();
        for (int n = 1; n <= N; n++)
        {
            int sum = 0;
            int temp = n;
            int length = (int)(Math.log10(n) + 1);

            while (temp != 0)
            {
                for (int i = 0; i < length; i++)
                {
                    int prod = 1;
                    int r = temp % 10;

                    for (int j = 0; j < length; j++) {prod *= r;}
                    sum += prod;
                    temp /= 10;
                }
            }
            if (n == sum) list.add(n);
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {result[i] = list.get(i);}
        return result;
    }
}
