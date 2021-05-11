package _210511.boj20166;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(NM)
 *              모든 맵의 칸에서부터 출발 = N * M
 *
 *              문자열의 최대 길이 = 5
 *              이동 가능한 방향 = 8
 *              한 점에서 이동할 수 있는 경로 수 = 8^5
 *
 *              => N * M * 8^5 <= 10 * 10 * 8^5
 *
 *
 * 3. 접근 방식
 *      칸이 최대 100칸, 문자열의 길이도 최대 5개밖에 되지 않기 때문에
 *      완탐이 가능하다고 생각하고 완탐 돌림
 *
 */
public class Main_girawhale {
    static int N, M, K;
    static int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
    static Map<String, Integer> countMap;
    static char[][] map;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();

        map = new char[N][];
        for (int i = 0; i < N; i++)
            map[i] = sc.next().toCharArray();

        countMap = new HashMap<>(); // key: 문자열, value: 횟수
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {
                dfs(y, x, ""); // 칸마다 dfs
            }
        }

        while (K-- > 0) {
            System.out.println(countMap.getOrDefault(sc.next(), 0)); // 맵에 저장된 횟수를 반환
        }
    }

    static void dfs(int y, int x, String s) {
        s += map[y][x]; // 문자열 더하기
        countMap.put(s, countMap.getOrDefault(s, 0) + 1);
        if (s.length() == 5) return; // 최대 문자열 길이 = 5

        for (int k = 0; k < 8; k++) {
            // 범위가 넘칠 때 처음 위치로 돌아가기 때문에 MOD 연산을 통해 구해준다
            int ny = (y + dy[k] + N) % N;
            int nx = (x + dx[k] + M) % M;

            dfs(ny, nx, s);
        }
    }
}
