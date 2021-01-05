package _210105.boj10021;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(), C = sc.nextInt();
        int[][] field = new int[N][];

        for (int i = 0; i < N; i++)
            field[i] = new int[]{sc.nextInt(), sc.nextInt()};

        PriorityQueue<int[]> que = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        que.add(new int[]{0, 0});
        int[] dist = new int[N];
        Arrays.fill(dist,Integer.MAX_VALUE);

        int cnt = 0, ans = 0;
        while (!que.isEmpty()) {
            int[] cur = que.poll();

            if (dist[cur[0]] != Integer.MAX_VALUE)
                continue;

            dist[cur[0]] = cur[1];
            cnt++;
            ans += cur[1];

            for (int i = 0; i < N; i++) {
                int d = (int) (Math.pow(field[i][0] - field[cur[0]][0], 2) + Math.pow(field[i][1] - field[cur[0]][1], 2));
                if (d >= C && dist[i] > C)
                    que.add(new int[]{i, d});
            }
        }

        System.out.println(cnt == N ? ans : -1);


    }
}
