package _210325.boj20207;

import java.util.Scanner;

public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] cnt = new int[366];

        for (int i = 0; i < N; i++) {
            int s = sc.nextInt(), e = sc.nextInt();
            while (s <= e)
                cnt[s++]++;
        }

        int day = 1;
        int ans = 0;
        while (day <= 365) {
            if (cnt[day] == 0) day++;
            else {
                int len = 0, max = cnt[day];
                while (day <= 365 && cnt[day] != 0) {
                    max = Math.max(max, cnt[day]);
                    day++;
                    len++;
                }
                ans += len * max;
            }
        }

        System.out.println(ans);
    }

}
