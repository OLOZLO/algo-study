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
                // 최솟값 이내로 설치가 가능한 경우로 그냥 stack에 넣어준다
                if (a[1] <= x)
                    stack.push(new int[]{a[0], cur[1]});
                // 최솟값보다 큰경우로 무료로 설치해야되는 경우.
                // k가 0보다 크다면 아직 무료 설치 횟수가 남았으므로 -1한 뒤 stack에 추가
                else if (cur[1] > 0) stack.push(new int[]{a[0], cur[1] - 1});
            }
        }

        //스택이 비었을 때까지 N에 다다르지 못했기때문에 불가능한 경우로 false를 반환 
        return false;
    }
}
