package _210119.boj1800;

import java.util.*;

public class Main_girawhale {
    static int N, P, K, ans = Integer.MAX_VALUE;
    static ArrayList<int[]>[] adj;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        P = sc.nextInt();
        K = sc.nextInt();

        adj = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++)
            adj[i] = new ArrayList();

        for (int i = 0; i < P; i++) {
            int n1 = sc.nextInt(), n2 = sc.nextInt(), cost = sc.nextInt();

            // 무방향 그래프이기 때문에 간선을 양방향에 추가
            adj[n1].add(new int[]{n2, cost});
            adj[n2].add(new int[]{n1, cost});
        }

        // 가격 범위 0 ~ 1,000,000까지 이분탐색
        // 0? N까지 도달하는데 간선이 a개만큼 존재하는데 무료 설치횟수가 a이상일 경우 돈을 내지 않고도 연결이 가능하다
        divide(0, 1000000);

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    static void divide(int start, int end) {
        while (start <= end) {
            int mid = (start + end) / 2;

            // DFS, Dijkstra 두 개 다 가능!
//            if (dijkstra(mid)) {
            if (dfs(mid)) { // 리턴이 true면 가능하다는 의미이므로
                ans = mid; // ans를 갱신해주고
                end = mid - 1; // 최소 돈이 더 작을 때 가능한지 살펴본다
            } else // 리턴이 false라면 해당 최소돈을 가질 때는 컴퓨터의 연결이 불가능하다
                start = mid + 1; // 그렇다면 돈의 범위를 올리자!
        }
    }


    // 최소값이 x라고 가정했을 때, x를 넘는 값은 모두 무료로 연결하려고 한다.
    // 만약 x보다 큰 값이 들어왔다면 K번까지는 무료로 설치가 가능하므로 K번까지 설치해본다
    // 만약 무료 설치횟수가 K가 넘어간다면 최솟값 x는 불가능한 경우이므로 false를 반환하고
    // 무료 설치 횟수가 K번 이내이면서 N을 찾아간다면 최솟값이 x는 가능한 경우로 true로 반환
    static boolean dfs(int x) {
        Deque<int[]> stack = new ArrayDeque(); // dfs를 탐색하기 위한 stack
        boolean[][] visit = new boolean[N + 1][K + 1]; //[현재 노드][공짜로 연결가능한 잔여 횟수]

        stack.push(new int[]{1, K}); // 처음 탐색은 1번 컴퓨터부터, K번까지 가능하도록 설정

        while (!stack.isEmpty()) {
            int[] cur = stack.pop();
            if (cur[0] == N) return true; // 만약 현재 탐색하는 노드가 N이라면 return true로 탐색 종료

            if (visit[cur[0]][cur[1]]) continue; // 이미 방문했다면 pass
            visit[cur[0]][cur[1]] = true;

            for (int[] a : adj[cur[0]]) {
                // 거리가 x보다 크다면 무료로 설치해야하는 경우이므로 잔여 설치 횟수에서 -1, 아니면 그냥 대입
                int[] next = new int[]{a[0], cur[1] - (a[1] > x ? 1 : 0)};
                if (next[1] >= 0) // 잔여횟수가 0이상이어야 탐색
                    stack.push(next);
            }
        }

        //스택이 비었을 때까지 N에 다다르지 못했기때문에 불가능한 경우로 false를 반환
        return false;
    }

    static boolean dijkstra(int x) {
        int[] dist = new int[N + 1]; // 현위치까지 도달했을 때 몇 번 무료설치했는지 저장
        Arrays.fill(dist, K + 1); // 무료 설치는 최대 K개까지 가능하므로 K+1로 INF값 설정

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1])); // x 초과 개수순 오름차순 정렬
        queue.add(new int[]{1, 0}); // {현재 위치, 가격 x를 넘은 개수}

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            if (cur[0] == N)
                return true;

            for (int[] a : adj[cur[0]]) {
                int[] next = new int[]{a[0], cur[1] + (a[1] > x ? 1 : 0)}; //다음에 탐색할 컴퓨터가 x를 넘는다면 초과개수를 증가

                if (next[1] <= K && next[1] < dist[next[0]]) { // 초과수가 K개 이하이고, 저장된 dist보다 작으면 탐색하기
                    queue.add(next);
                    dist[next[0]] = next[1];
                }
            }
        }

        return false;
    }
}
