package _210511.boj20166;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
 *   1. 결과 : 맞았습니다(2/16)
 *   2. 시간복잡도 : O(NM)
 *       - 이유 : NXM 배열을 다 돌며 각 좌표에서 8^5개의 경우를 확인.
 *   3. 접근 방식
 *       - 완탐.
 *       - K개의 문자열을 해시맵에서 찾자. contains는 O(1)의 연산 속도니까 빠를 것.
 *   4. 후기
 *       - dfs 인자로 String 말고 StringBuilder로 어찌저찌 해볼라 했는데 왜 안되는 지 모르겠다.
 *       - 다 맞으려면 어케해야되는 지 내일 알아보겠다.. 메모리가 문제인듯?
 */

public class Main_ms {
    static int N, M, K;
    static char[][] map;
    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1}, dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    static HashMap<String, Integer> hashMap;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = stoi(st.nextToken());
        M = stoi(st.nextToken());
        K = stoi(st.nextToken());
        map = new char[N][M];
        hashMap = new HashMap<>();

        for (int i = 0; i < N; i++) map[i] = br.readLine().toCharArray();
        for (int i = 0; i < K; i++) hashMap.put(br.readLine(), 0); // 미리 K개 문자열 해시맵에 0번 등장한 걸로 초기 세팅.

        for (int i = 0; i < N; i++) { // NXM 다 돌며 dfs 수행.
            for (int j = 0; j < M; j++) {
                dfs(i, j, 1, String.valueOf(map[i][j])); // 좌표 주고, 1개 카운트 해주고, 초기 문자 하나만 전달.
            }
        }

        StringBuilder answer = new StringBuilder();
        for (String key : hashMap.keySet()) answer.append(hashMap.get(key) + "\n");
        System.out.println(answer);
    }

    static void dfs(int x, int y, int cnt, String s) {
        if (hashMap.containsKey(s)) hashMap.put(s, hashMap.get(s) + 1); // 만들어 가는 문자열을 의미하는 s가 도중에 신이 좋아하는 문자열을 만족시키면, 개수 증가.

        if (cnt > 5) return; // 신이 만든 문자열은 길이가 5를 초과할 수 없기에 그만.

        for (int d = 0; d < 8; d++) {
            int nx = x + dx[d], ny = y + dy[d]; // 8 방향에 대해 미리 좌표 세팅한 후, 아래에서 벽을 넘어가면 조건에 따라 처리해줌.

            if (nx < 0) nx = N - 1;
            else if (nx > N - 1) nx = 0;
            if (ny < 0) ny = M - 1;
            else if (ny > M - 1) ny = 0;

            dfs(nx, ny, cnt + 1, s + map[nx][ny]); // 문자를 하나 붙이고 다음 좌표로 넘어가 dfs 수행 계속.
        }
    }

    static int stoi(String s) {
        return Integer.parseInt(s);
    }
}
