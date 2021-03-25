package _210325.boj20208;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main_girawhale {
    static int N, M, H, ans;
    static List<int[]> milk;
    static int[] home;
    static boolean[] visit;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        H = sc.nextInt();

        milk = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int in = sc.nextInt();
                if (in == 2) milk.add(new int[]{i, j});
                else if (in == 1) home = new int[]{i, j};
            }
        }
        visit = new boolean[milk.size()];

        dfs(home, 0, M);
        System.out.println(ans);
    }

    static void dfs(int[] cur, int cnt, int hp) {
        if (getDist(cur, home) <= hp) ans = Math.max(cnt, ans);

        for (int i = 0; i < milk.size(); i++) {
            int dist = getDist(cur, milk.get(i));
            if (!visit[i] && dist <= hp) {
                visit[i] = true;
                dfs(milk.get(i), cnt + 1, hp - dist + H);
                visit[i] = false;
            }
        }
    }

    static int getDist(int[] pos, int[] dest) {
        return Math.abs(pos[0] - dest[0]) + Math.abs(pos[1] - dest[1]);
    }
}
