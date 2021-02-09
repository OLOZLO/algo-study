package _210209.boj1039;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_ms {
    static int N, K;
    static boolean[][] visit = new boolean[1000001][11];
    static int answer = -1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        K = sc.nextInt();

        Queue<Pair> queue = new LinkedList<>();
        visit[N][0] = true;
        queue.add(new Pair(N, 0));

        while (!queue.isEmpty()) {
            Pair now = queue.poll();

            if (now.cnt == K) {
                answer = Math.max(answer, now.num);
                continue;
            }

            int length = String.valueOf(N).length();

            for (int i = 0; i < length - 1; i++) {
                for (int j = i + 1; j < length; j++) {
                    char[] swapped = swap(now.num, i, j);
                    int num = Integer.parseInt(String.valueOf(swapped));

                    if (swapped[0] == '0' || visit[num][now.cnt + 1])
                        continue;

                    visit[num][now.cnt + 1] = true;
                    queue.add(new Pair(num, now.cnt + 1));
                }
            }
        }

        System.out.println(answer);
    }

    private static class Pair {
        int num, cnt;

        public Pair(int num, int cnt) {
            this.num = num;
            this.cnt = cnt;
        }
    }

    private static char[] swap(int num, int i, int j) {
        StringBuilder sb = new StringBuilder(String.valueOf(num));

        char tmp = sb.charAt(i);
        sb.setCharAt(i, sb.charAt(j));
        sb.setCharAt(j, tmp);

        return sb.toString().toCharArray();
    }
}

