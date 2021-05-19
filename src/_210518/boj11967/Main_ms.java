package _210518.boj11967;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
 *   1. 결과 : 시간없어 문제 읽지도 못함 -> 맞았습니다.
 *   2. 시간복잡도 : 모르겠습니다..
 *   3. 접근 방식
 *       - 며칠 전에 푼 백준 열쇠 문제(https://ddb8036631.github.io/boj/9328_열쇠/) 접근 방법으로 풀었음.
 *       - 다음 방에 전구가 꺼져있어 이동하지 못한다면, candidates 라는 Set에 담아둠.
 *       - 새로운 방에 들어왔을 때, 켤 수 있는 전구들을 모두 켜고, 여기서 켤 수 있는 전구들이 예전에 불이 꺼져있어 가지 못한 방이라면 큐에 넣어줌. -> 이동을 계속 하게 됨.
 */
public class Main_ms {
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = stoi(st.nextToken());
        int M = stoi(st.nextToken());
        Switch[][] map = new Switch[N][N];      // map[i][j] : (i,j)에서 켤 수 있는 전구들이 리스트에 담겨있다.
        boolean[][] visit = new boolean[N][N];
        boolean[][] on = new boolean[N][N];    // on[i][j]가 true면, 해당 (i,j)방의 전구가 켜져있다.
        HashSet<Point> candidates = new HashSet<>(); // candidates : 방 불이 꺼져있어, 더 이동하지 못한 방의 좌표가 들어있음.
        int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
        int answer = 1;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                map[i][j] = new Switch();

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int x, y, a, b;
            x = stoi(st.nextToken()) - 1;
            y = stoi(st.nextToken()) - 1;
            a = stoi(st.nextToken()) - 1;
            b = stoi(st.nextToken()) - 1;

            map[x][y].switches.add(new Point(a, b)); // 입력받은 (x,y)방에서 스위치를 켤 수 있는 방 좌표(a,b)를 담아둠.
        }

        Queue<Point> queue = new LinkedList<>();
        on[0][0] = true;
        visit[0][0] = true;
        queue.add(new Point(0, 0));

        while (!queue.isEmpty()) {
            Point now = queue.poll();

            // 꺼낸 좌표 now에 해당하는 방에 달려있는 스위치의 좌표들을 다 확인하면서,
            // 해당 next 방의 불이 꺼져있다면 켜주고,
            // next 방이 예전에 꺼져있어 도달하지 못한 방이라면 큐에 다시 넣어줘서 다음번에 이동을 계속할 수 있도록 함.
            for (Point next : map[now.x][now.y].switches) {
                if (!on[next.x][next.y]) {
                    on[next.x][next.y] = true;
                    answer++;

                    if (candidates.contains(next)) {
                        queue.add(next);
                    }
                }
            }

            for (int d = 0; d < 4; d++) {
                int nx = now.x + dx[d], ny = now.y + dy[d];

                if (!isInRange(nx, ny) || visit[nx][ny]) continue;

                // (nx,ny)인 다음 방의 불이 꺼져있다면,
                // candidates에 넣어서 나중에 켜짐을 기약하자.
                if (!on[nx][ny]) {
                    candidates.add(new Point(nx, ny));
                    continue;
                }

                visit[nx][ny] = true;
                queue.add(new Point(nx, ny));
            }
        }

        System.out.println(answer);
    }

    static class Switch {
        ArrayList<Point> switches = new ArrayList<>();
    }

    static boolean isInRange(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }

    static int stoi(String s) {
        return Integer.parseInt(s);
    }
}
