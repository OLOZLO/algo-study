package _210325.boj20208;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main_ms {
    static int N, M, H;
    static int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
    static int[][] map;
    static Point house;
    static ArrayList<Point> milk;
    static boolean[] visit;
    static int answer;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        H = sc.nextInt();
        map = new int[N][N];
        milk = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int num = sc.nextInt();
                if (num == 1) {
                    house = new Point(i, j);
                } else if (num == 2) {
                    milk.add(new Point(i, j));
                }
            }
        }

        visit = new boolean[milk.size()];

        for (int i = 0; i < milk.size(); i++) {
            Point init = milk.get(i);
            int dist = Math.abs(house.x - init.x) + Math.abs(house.y - init.y);

            if (dist <= M) {
                solve(init, i, M - dist + H, 1);
            }
        }

        System.out.println(answer);
    }

    static void solve(Point now, int idx, int hp, int cnt) {
        visit[idx] = true;

        for (int i = 0; i < milk.size(); i++) {
            if (!visit[i]) {
                Point next = milk.get(i);
                int dist = Math.abs(now.x - next.x) + Math.abs(now.y - next.y);

                if (dist <= hp)
                    solve(next, i, hp - dist + H, cnt + 1);
            }
        }

        int dist = Math.abs(now.x - house.x) + Math.abs(now.y - house.y);

        if (dist <= hp) {
            answer = Math.max(answer, cnt);
        }

        visit[idx] = false;
    }
}