package _210325.boj20207;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main_ms {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Schedule[] schedules = new Schedule[N];
        int[] count = new int[366];

        for (int i = 0; i < N; i++) schedules[i] = new Schedule(sc.nextInt(), sc.nextInt());

        Arrays.sort(schedules);

        for (Schedule now : schedules)
            for (int i = now.start; i <= now.end; i++)
                count[i]++;

        int answer = 0, max = 0, len = 0;
        boolean flag = false;

        for (int i = 1; i <= 365; i++) {
            if (count[i] == 0) {
                flag = false;
                answer += max * len;
                max = 0;
                len = 0;
                continue;
            }

            flag = true;
            max = Math.max(max, count[i]);
            len++;
        }

        if (flag) answer += max * len;

        System.out.println(answer);
    }

    static class Schedule implements Comparable<Schedule> {
        int start, end;

        public Schedule(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Schedule o) {
            return this.start == o.start ? o.end - this.end : this.start - o.start;
        }
    }
}
