package _210525.boj15658;

import java.util.Scanner;

public class Main_Taekyung2 {
    static int N, max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
    static int[] num, operator;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        num = new int[N];
        operator = new int[4];

        for(int i = 0; i < N; i++)
            num[i] = sc.nextInt();

        for(int i = 0; i < 4; i++)
            operator[i] = sc.nextInt();

        calc(1, num[0], operator[0], operator[1], operator[2], operator[3]);
        System.out.println(max);
        System.out.println(min);
    }


    static void calc(int idx, int ans, int plus, int minus, int mul, int div) {
        if(idx == N) {
            max = Math.max(max, ans);
            min = Math.min(min, ans);
            return;
        }

        if(plus > 0)    calc(idx + 1, ans + num[idx], plus - 1, minus, mul, div);
        if(minus > 0)   calc(idx + 1, ans - num[idx], plus, minus - 1, mul, div);
        if(mul > 0)     calc(idx + 1, ans * num[idx], plus, minus, mul - 1, div);
        if(div > 0)     calc(idx + 1, ans / num[idx], plus, minus, mul, div - 1);
    }
}