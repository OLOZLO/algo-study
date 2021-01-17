package _210119.boj14466;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};
        int N = sc.nextInt(), K = sc.nextInt(), R = sc.nextInt();

        // 1 2 3
        // 4 5 6
        // 7 8 9
        // 로 변경하여 연결 표시
        boolean[][] adj = new boolean[(N + 1) * (N + 1)][(N + 1) * (N + 1)];
        for (int i = 0; i < (N + 1) * (N + 1); i++)
            Arrays.fill(adj[i], true); // 기본적으로는 길을 건너지 않아도 가능하기 때문에 true

        for (int i = 0; i < R; i++) {
            int n1 = sc.nextInt() * N + sc.nextInt(); // (2,2)는 5로 변경
            int n2 = sc.nextInt() * N + sc.nextInt();

            adj[n1][n2] = adj[n2][n1] = false; // 길을 건너 가야하기 때문에 false
        }

        int[][] fences = new int[N + 1][N + 1]; // 같은 울타리라면 같은 숫자를 저장할 배열
        int fenceCnt = 0; // 울타리로 분리된 개수

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (fences[i][j] != 0)// 울타리 번호를 매겼다면 패스
                    continue;

                // 울타리 번호를 매기지 않았다면 연결된 범위를 bfs로 탐색
                Queue<int[]> queue = new LinkedList<>();
                queue.add(new int[]{i, j});
                fences[i][j] = ++fenceCnt; // 울타리 범위 개수 증가

                while (!queue.isEmpty()) {
                    int[] cur = queue.poll();
                    int curNum = cur[0] * N + cur[1]; // 연결을 체크할 번호

                    for (int k = 0; k < 4; k++) {
                        int ny = cur[0] + dy[k];
                        int nx = cur[1] + dx[k];

                        if (ny <= 0 || nx <= 0 || ny > N || nx > N || !adj[curNum][ny * N + nx] || fences[ny][nx] != 0)
                            continue;

                        queue.add(new int[]{ny, nx});
                        fences[ny][nx] = fenceCnt;
                    }
                }
            }
        }

        int[] fenceCows = new int[fenceCnt + 1]; // 울타리 범위내에 소가 몇 마리 있는지 저장할 배열
        for (int i = 0; i < K; i++)
            fenceCows[fences[sc.nextInt()][sc.nextInt()]]++; // 해당 울타리 번호에 있는 소카운트를 증가

        int ans = 0;

        // 쌍의 개수를 구해야 하므로 다른 울타리에 있는 모든 소를 짝짓기 위해 곱하여 더해준다
        for (int i = 1; i <= fenceCnt; i++)
            for (int j = i + 1; j <= fenceCnt; j++)
                ans += fenceCows[i] * fenceCows[j]; 

        System.out.println(ans);


    }
}
