package _210615.boj18430;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_hz {

    /**
     * [실1] 무기 공학
     *
     * 1. 결과 : 성공
     * 2. 시간복잡도 : 모르겠어요...
     * 3. 풀이
     *    : 전체 나무 재료로 만들 수 있는 부매랑을 만든 후 최대 강도를 가지는 경우를 답으로 지정
     *
     * */

    static int[][] wood;
    static boolean[][] visited;
    static int result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        wood = new int[N][M];   // 나무 재료
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                wood[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        visited = new boolean[N][M];
        result = 0;
        make(0, 0, 0);
        System.out.println(result);
    }

    // [0] ㅢ [1] ㄴ [2] ┌- [3] ㄱ 모양
    static int[][] dir = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};

    public static void make(int r, int c, int power) {

        // (r, c) 부터 순서대로 나무 재료를 확인.
        for (int i = r; i < wood.length; i++) {
            int jj = 0;
            if (i == r) jj = c;

            for (int j = jj; j < wood[0].length; j++) {
                if (visited[i][j]) continue;

                // 현재 위치의 재료가 사용되지 않았을 경우 4가지 부매랑 모양을 만들 수 있나 확인
                for (int d = 0; d < dir.length; d++) {
                    int ni = i + dir[d][0];
                    int nj = j + dir[d][1];

                    // 부매랑 모양을 만들 수 있고
                    if (ni >= 0 && ni < wood.length && nj >= 0 && nj < wood[0].length) {
                        // 부매랑 모양에 해당하는 나무 재료가 사용되지 않았을 경우 부매랑 만들기
                        if (!visited[ni][j] && !visited[i][nj]) {
                            visited[i][j] = visited[ni][j] = visited[i][nj] = true;
                            // 사실 (i, j) 말고 (i, j)의 다음 위치를 인자로 넘기고 싶었는데, j == M-1일 때 고려하기 귀찮아서 그냥 (i, j)로 넘김
                            make(i, j, power+wood[i][j]*2+wood[ni][j]+wood[i][nj]);
                            visited[i][j] = visited[ni][j] = visited[i][nj] = false;
                        }
                    }
                }
            }
        }

        // 더 이상 나무 재료로 부매랑을 만들 수 없을 때 강도의 최대값이 정답이 되도록
        result = Math.max(result, power);
    }
}
