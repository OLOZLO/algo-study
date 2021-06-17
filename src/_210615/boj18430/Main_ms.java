package _210615.boj18430;

import java.util.Scanner;

/*
 *   [실버1] 무기 공학
 *
 *   https://ddb8036631.github.io/boj/18430_무기-공학/
 *   1. 결과 : 맞았습니다.
 *   2. 시간복잡도 : O(4^(NM))
 *       - 이유 : NM개의 모든 좌표 각각에 대해 4개의 부메랑 모양을 확인하기 때문.
 *   3. 접근 방식
 *       - 백트래킹
 *       - 현재 좌표 기준으로 4개의 부매랑 모양을 각각 확인하고 재귀 호출해 부메랑 강도 합을 계산해 재귀 호출.
 *       - 현재 좌표에서 아무런 부메랑을 만들지 못하는 경우, 현재 강도 합은 그대로 좌표만 이동시켜 재귀 호출.
 *   4. 후기
 *       - 좌표의 이동이 오른쪽 한 쪽으로만 진행되어서 첨에 당황했음.
 */

public class Main_ms {
    static int N, M;
    static int[][] map;
    static boolean[][] used;
    static int max;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        map = new int[N][M];
        used = new boolean[N][M];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                map[i][j] = sc.nextInt();

        dfs(0, 0, 0);

        System.out.println(max);
    }

    static void dfs(int x, int y, int sum) {
        if (y == M) {
            y = 0;
            x++;
        }

        if (x == N) {
            max = Math.max(max, sum);
            return;
        }

        if (isInRange(x, y - 1) && isInRange(x + 1, y) && !used[x][y] && !used[x][y - 1] && !used[x + 1][y]) {
            used[x][y] = used[x][y - 1] = used[x + 1][y] = true;
            dfs(x, y + 1, sum + 2 * map[x][y] + map[x][y - 1] + map[x + 1][y]);
            used[x][y] = used[x][y - 1] = used[x + 1][y] = false;
        }

        if (isInRange(x, y - 1) && isInRange(x - 1, y) && !used[x][y] && !used[x][y - 1] && !used[x - 1][y]) {
            used[x][y] = used[x][y - 1] = used[x - 1][y] = true;
            dfs(x, y + 1, sum + 2 * map[x][y] + map[x][y - 1] + map[x - 1][y]);
            used[x][y] = used[x][y - 1] = used[x - 1][y] = false;
        }

        if (isInRange(x - 1, y) && isInRange(x, y + 1) && !used[x][y] && !used[x - 1][y] && !used[x][y + 1]) {
            used[x][y] = used[x - 1][y] = used[x][y + 1] = true;
            dfs(x, y + 1, sum + 2 * map[x][y] + map[x - 1][y] + map[x][y + 1]);
            used[x][y] = used[x - 1][y] = used[x][y + 1] = false;
        }

        if (isInRange(x + 1, y) && isInRange(x, y + 1) && !used[x][y] && !used[x + 1][y] && !used[x][y + 1]) {
            used[x][y] = used[x + 1][y] = used[x][y + 1] = true;
            dfs(x, y + 1, sum + 2 * map[x][y] + map[x + 1][y] + map[x][y + 1]);
            used[x][y] = used[x + 1][y] = used[x][y + 1] = false;
        }

        dfs(x, y + 1, sum);
    }

    static boolean isInRange(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}
