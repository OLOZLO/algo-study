package _210406.boj1103;

import java.util.Scanner;

/*
    1. 결과 : 틀림 & 시초
    2. 시간복잡도 : 모르겠습니다.
        - 이유 : DFS 시간 복잡도가 O(V^2)이라는데, 제 코드의 시간 복잡도는 어떻게 되는걸까요..?
    3. 아이디어
        - dfs 탐색만을 써서 풀려고 했습니다.
        - 현재 위치로에서 이동하게 되는 다음 위치가 "범위 밖"이거나, "구멍"이면 continue 시켜 dfs 재귀를 가지치기 했습니다.
        - 방문하지 않은 다음 위치에 대해서만 dfs 를 호출했습니다.
        - 현재 위치의 번호와, 현재 위치에서 움직일 수 있는 다음 위치의 번호가 같다면 사이클이 생기니까, isRotate 라는 불린 변수에 값을 담아 -1을 출력하도록 생각했습니다.
    4. 질문
        1. 제 코드의 시간 복잡도가 어떻게 되나요?
        2. dfs 에 dp를 섞게 되면, visit 처리로 걸러지는 거 외에도 dp 배열의 갱신 유무로 바로 리턴되는 가지치기 부분 때문에 시간이 단축되는 건가요?
*/

public class Main_ms {
    static int N, M;
    static char[][] map;
    static boolean[][] visit;
    static int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
    static int answer;
    static boolean isRotate;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();

        map = new char[N][M];
        visit = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            String s = sc.next();
            for (int j = 0; j < s.length(); j++) {
                map[i][j] = s.charAt(j);
            }
        }

        dfs(0, 0, 1);

        System.out.println(isRotate ? -1 : answer);
    }

    static void dfs(int x, int y, int cnt) {
        if (isRotate) return;

        visit[x][y] = true;
        answer = Math.max(answer, cnt);
        int dist = map[x][y] - '0';

        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d] * dist, ny = y + dy[d] * dist;

            if (!inRange(nx, ny) || map[nx][ny] == 'H') continue;

            if (map[nx][ny] - '0' == dist) {
                isRotate = true;
            }

            if (!visit[nx][ny])
                dfs(nx, ny, cnt + 1);
        }

        visit[x][y] = false;
    }

    static boolean inRange(int x, int y) {
        if (x < 0 || x > N - 1 || y < 0 || y > M - 1) return false;
        return true;
    }
}