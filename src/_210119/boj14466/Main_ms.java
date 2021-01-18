package _210119.boj14466;

import java.awt.*;
import java.util.*;
import java.util.List;

// https://ddb8036631.github.io/알고리즘%20풀이/백준_14666_소가-길을-건너간-이유-6/
public class Main_ms {
    static int N, K, R;
    static Point[] k;
    static int[] di = {-1, 1, 0, 0};
    static int[] dj = {0, 0, -1, 1};
    static List<Integer>[] adj;
    static boolean[] visit;
    static int answer;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt(); // N : 배열의 크기
        K = sc.nextInt(); // K : 소의 수
        R = sc.nextInt(); // R : 간선의 수

        adj = new ArrayList[N * N + 1];
        for (int i = 1; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < R; i++) {

            // 두 정점의 x, y 좌표를 입력받아 아래 convert 메소드를 통해 1차원 배열의 인덱스로 변환해준다.
            int from = convert(sc.nextInt(), sc.nextInt());
            int to = convert(sc.nextInt(), sc.nextInt());

            // 무방향이므로 from->to, to->from 둘 다 가능하도록 양쪽 다 추가해준다.
            adj[from].add(to);
            adj[to].add(from);
        }

        k = new Point[K]; // k : 각 소들이 위치하는 좌표들을 저장하는 배열
        for (int i = 0; i < K; i++) {
            k[i] = new Point(sc.nextInt(), sc.nextInt());
        }

        // 가능한 모든 쌍에 대해 각각 bfs 탐색을 수행한다.
        for (int i = 0; i < K - 1; i++) {
            for (int j = i + 1; j < K; j++) {
                Point from = new Point(k[i].x, k[i].y);
                Point to = new Point(k[j].x, k[j].y);

                bfs(from, to);
            }
        }

        System.out.println(answer);
    }

    // bfs : 상하좌우 4방향으로 탐색하되, 길이 있으면 해당 좌표로 이동하지 않는다.
    // start에서 dest까지는 어떻게든 갈 수 있다. 다만, 그 경로상 길이 하나라도 포함됐냐 안됐냐의 차이.
    // 길을 하나라도 포함한다면, 이후의 경로는 더이상 확인 안해봐도 됨. -> 왜? 이미 길을 건넜으니까 길을 건너지 않으면 만날 수 없음을 의미.
    static void bfs(Point start, Point dest) {
        Queue<Point> queue = new LinkedList<>();
        visit = new boolean[N * N + 1]; // 2차원 배열의 각 좌표를 1차원으로 변환해 사용할 것이기 때문에, N*N+1개의 크기를 갖는 visit 배열을 생성함.

        queue.add(start);
        visit[convert(start.x, start.y)] = true;

        boolean reachWithoutRoad = false; // 길을 사용했냐 안했냐 판단 위한 boolean 변수.

        while (!queue.isEmpty()) {
            Point now = queue.poll();

            if (now.x == dest.x && now.y == dest.y) {
                reachWithoutRoad = true; // 아래 길을 택하지 않는 로직에 따라 목적지까지 도달했다면? 길을 이용하지 않은 것이므로 표시해줌.
                break;
            }

            for (int d = 0; d < 4; d++) {

                // 상하좌우 인접한 다음 좌표를 구한 후,
                int next_i = now.x + di[d];
                int next_j = now.y + dj[d];

                // "현재 좌표"와 위에서 바로 위에서 구한 인접한 "다음 좌표"를 1차원 인덱스로 변환하고,
                int from = convert(now.x, now.y);
                int to = convert(next_i, next_j);

                // 배열 범위를 벗어나지도 않고, 방문도 안했으며, 길도 없다?
                if (next_i < 1 || next_i > N || next_j < 1 || next_j > N || visit[convert(next_i, next_j)] || adj[from].contains(to))
                    continue;

                // 그럼 그 이동 경로는 "길을 이용하지 않았으므로" 채택. 큐에 넣고 다음 이동을 진행한다.
                queue.add(new Point(next_i, next_j));
                visit[convert(next_i, next_j)] = true;
            }
        }

        // 길을 이용해야 했다면 카운트를 증가시킨다.
        if (!reachWithoutRoad) {
            answer++;
        }
    }

    // 2차원 배열에서의 좌표를 1차원 배열의 인덱스로 변환하는 함수
    static int convert(int x, int y) {
        return (x - 1) * N + y;
    }
}
