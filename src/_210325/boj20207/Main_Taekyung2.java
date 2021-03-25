package _210325.boj20207;

import java.util.Scanner;

public class Main_Taekyung2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] cal = new int[366];
        for(int i = 0; i < N; i++) {
            int s = sc.nextInt(), e = sc.nextInt();
            for(int j = s; j <= e; j++)
                cal[j]++;
        }
        int l = 1, r = 1, h = 0, ret = 0;
        while(r <= 365) {
            if(cal[r] != 0) {
                h = Math.max(h, cal[r]);
                r++;
            } else {
                ret += (r - l) * h;
                r++;
                l = r;
                h = 0;
            }
        }
        if(h != 0) ret += (r - l) * h;
        System.out.println(ret);
    }
}
