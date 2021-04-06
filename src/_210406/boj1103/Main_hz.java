package _210406.boj1103;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*

결과 : 성공
시간복잡도 : O(4*N*M)
메모이제이션을 사용하여 이미 방문하여 그 때 계산을 한 결과를 그대로 사용하기 때문에 O(N*M) 이라고 생각했고
4를 곱한 이유는 각 정점에서 사방을 탐색하며 dp의 크기를 벗어나는 경우가 있을 수 있기 때문에 위와 같이 생각했습니다.

*/
public class Main_hz {

    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int N, M;
    static int[][] board, dp;

    public static class Pos {
        int i, j;

        Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");

        N = Integer.parseInt(s[0]);
        M = Integer.parseInt(s[1]);
        board = new int[N][M];
        dp = new int[N][M]; // [N][M]위치에서 점프할 수 있는 횟수 저장

        for (int i = 0; i < N; i++) {
            String in = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = in.charAt(j) == 'H' ? -1 : in.charAt(j)-'0';
            }
        }

        dfs(new Pos(0, 0), new boolean[N][M]);
        System.out.println(dp[0][0]);
    }

    public static int dfs(Pos p, boolean[][] visited) {

        // 이미 현재 위치에서 점프 가능한 횟수를 구해놓은 경우 그대로 리턴
        if (dp[p.i][p.j] != 0) return dp[p.i][p.j];
        
        visited[p.i][p.j] = true;

        for (int d = 0; d < dir.length; d++) {
            int ni = p.i + board[p.i][p.j]*dir[d][0];
            int nj = p.j + board[p.i][p.j]*dir[d][1];

            // 게임이 종료되는 경우
            if (ni < 0 || ni >= N || nj < 0 || nj >= M || board[ni][nj] == -1) {
                dp[p.i][p.j] = Math.max(dp[p.i][p.j], 1);
                continue;
            }

            // 만약 다음에 이동할 위치를 이미 방문했을 경우 무한루프에 빠질 수 있기 때문에 -1 리턴
            if (visited[ni][nj]) return dp[p.i][p.j] = -1;

            int tmp = dfs(new Pos(ni, nj), visited);
            if (tmp == -1) return dp[p.i][p.j] = -1;    // 만약 다음 위치에서 점프가 불가능해서 -1를 리턴하면 현재 위치에 -1 저장 후 리턴
            else dp[p.i][p.j] = Math.max(dp[p.i][p.j], tmp+1);  // 다음 위치에서 점프가 가능 할 경우, 현재 위치에서 점프 가능한 횟수와 다음 위치에서 점프 가능한 횟수+1 중 최댓값 저장
        }

        visited[p.i][p.j] = false;
        return dp[p.i][p.j];
    }

}
