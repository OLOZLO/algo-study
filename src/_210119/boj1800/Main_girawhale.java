package _210119.boj1800;

import java.util.*;

public class Main_girawhale {
    static int N, P, K, ans = Integer.MAX_VALUE;
    static ArrayList<int[]>[] adj;
    static boolean[] costCheck;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        P = sc.nextInt();
        K = sc.nextInt();

        adj = new ArrayList[N + 1];
        costCheck = new boolean[1000001];
        costCheck[0] = true;

        for (int i = 1; i <= N; i++)
            adj[i] = new ArrayList();

        for (int i = 0; i < P; i++) {
            int n1 = sc.nextInt(), n2 = sc.nextInt(), cost = sc.nextInt();

            adj[n1].add(new int[]{n2, cost});
            adj[n2].add(new int[]{n1, cost});
            costCheck[cost] = true;
        }

        divide(0, 1000000);

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    static void divide(int start, int end) {
        while (start <= end) {
            int mid = (start + end) / 2;

            if (dfs(mid)) {
                if (costCheck[mid])
                    ans = mid;
                end = mid - 1;
            } else
                start = mid + 1;
        }
    }

    static boolean dfs(int x) {
        Deque<int[]> stack = new ArrayDeque();
        boolean[][] visit = new boolean[N + 1][K + 1];

        stack.push(new int[]{1, K});

        while (!stack.isEmpty()) {
            int[] cur = stack.pop();
            if (cur[0] == N) return true;

            if (visit[cur[0]][cur[1]]) continue;
            visit[cur[0]][cur[1]] = true;

            for (int[] a : adj[cur[0]]) {
                if (a[1] <= x) stack.push(new int[]{a[0], cur[1]});
                else if (cur[1] > 0) stack.push(new int[]{a[0], cur[1] - 1});
            }
        }

        return false;
    }
}
