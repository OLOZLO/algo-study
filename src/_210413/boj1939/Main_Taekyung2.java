package _210413.boj1939;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_Taekyung2 {

    /**
     * [골드4] 중량 제한
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(logC * (N + M))
     * 3. 아이디어
     *      - 최적화 문제를 결정 문제로 바꿔 풀기 -> 아주 유용해!
     *      - 이분 탐색을 돌며 가능한 답 중 최대값을 찾자 (lower_bound)
     *
     */

    static int N, M, min = Integer.MAX_VALUE, max = 0;
    static int S, E;
    static List<Edge>[] adj;


    static int stoi(String s) { return Integer.parseInt(s); }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = stoi(st.nextToken());
        M = stoi(st.nextToken());

        // 인접 리스트 초기화
        adj = new ArrayList[N + 1];
        for(int idx = 1; idx <= N; idx++) {
            adj[idx] = new ArrayList<>();
        }

        // 인접 리스트 입력, 이분 탐색 최소, 최대 값 구하기
        for(int iter = 0; iter < M; iter++) {
            st = new StringTokenizer(br.readLine());
            int a = stoi(st.nextToken());
            int b = stoi(st.nextToken());
            int c = stoi(st.nextToken());

            min = Math.min(min, c);
            max = Math.max(max, c);

            adj[a].add(new Edge(b, c));
            adj[b].add(new Edge(a, c));
        }

        st = new StringTokenizer(br.readLine());
        S = stoi(st.nextToken());
        E = stoi(st.nextToken());

        System.out.println(binarySearch());
    }


    public static int binarySearch() {
        int lo = min, hi = max;

        // mid값 이하로 도달 가능한 최대 값 구하기
        while(lo <= hi) {
            int mid = (lo + hi) / 2;

            if(isReachable(mid))
                lo = mid + 1;
            else
                hi = mid - 1;
        }

        return hi;
    }


    // 일반적인 BFS
    public static boolean isReachable(int limit) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] discovered = new boolean[N + 1];

        q.add(S);
        discovered[S] = true;

        while(!q.isEmpty()) {
            int cur = q.poll();
            if(cur == E)
                return true;

            for(Edge next : adj[cur]) {
                // 다리 중량 제한보다 크면 못감
                if(discovered[next.to] || next.cost < limit) continue;
                discovered[next.to] = true;
                q.add(next.to);
            }
        }

        return false;
    }


    static class Edge {
        int to, cost;

        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }
}
