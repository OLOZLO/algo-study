package _210518.boj11967;

import java.util.*;
import java.util.stream.IntStream;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : 정확히는 모루겠음... 예상은 O(N + M)...?
 *
 * 3. 접근 방식
 *      칸 방문하면 일단 연결된거 불 다킴
 *      - 불 켰는데 방문 안하고, 주변 4방향에 갈 수 있는 칸 있으면 Queue에 추가
 *      - 주변 칸에 불켰는데 방문 안한 칸 있으면 추가
 *
 */
public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] dy = {-1, 1, 0, 0};
        int[] dx = {0, 0, -1, 1};
        int N = sc.nextInt(), M = sc.nextInt();
        List<int[]>[][] adj = new ArrayList[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                adj[i][j] = new ArrayList<>();

        while (M-- > 0) { // [0, 0]부터 시작하게 변환하고 연결
            adj[(sc.nextInt() - 1)][sc.nextInt() - 1].add(new int[]{sc.nextInt() - 1, sc.nextInt() - 1});
        }

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] on = new boolean[N][N];
        boolean[][] visit = new boolean[N][N];
        queue.add(new int[]{0, 0}); // [0, 0]부터 시작
        on[0][0] = visit[0][0] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            adj[cur[0]][cur[1]].forEach(e -> {
                on[e[0]][e[1]] = true; // 스위치 칸 불 켬

                if (!visit[e[0]][e[1]]) { // 방문 안했는데 주변에 방문했던 칸 있으면 add
                    for (int k = 0; k < 4; k++) {
                        int ny = e[0] + dy[k], nx = e[1] + dx[k];

                        if (ny < 0 || nx < 0 || ny >= N || nx >= N || !visit[ny][nx]) continue;

                        queue.add(e);
                        visit[e[0]][e[1]] = true;
                        break;
                    }
                }
            });
            for (int k = 0; k < 4; k++) { // 주변에 불켰는데 안간 칸 있으면 add
                int ny = cur[0] + dy[k], nx = cur[1] + dx[k];

                if (ny < 0 || nx < 0 || ny >= N || nx >= N || !on[ny][nx] || visit[ny][nx]) continue;

                queue.add(new int[]{ny, nx});
                visit[ny][nx] = true;
            }
        }

        // 불 켠칸 모두 세기
        System.out.println(IntStream.range(0, N).mapToLong(i -> IntStream.range(0, N).filter(j -> on[i][j]).count()).sum());
    }

}