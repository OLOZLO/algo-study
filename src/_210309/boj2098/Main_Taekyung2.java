package _210309.boj2098;

import java.util.*;

public class Main_Taekyung2 {
    static int N, INF = (int)1e9;
    static int[][] map, cache;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        map = new int[N][N];
        cache = new int[N][1 << N];
        for(int i = 0; i < N; i++) {
            Arrays.fill(cache[i], -1);
            for (int j = 0; j < N; j++)
                map[i][j] = sc.nextInt();
        }
        // 어디서 시작하든 사이클 최소 비용은 같음
        System.out.println(tcp(0, 1));
    }

    // cur에서 출발해서 모든 곳을 다 돌고 오는데 필요한 비용
    static int tcp(int cur, int visited) {
        //1이 N - 1개가 되면 모든 곳 다 방문한 것
        if(visited == (1 << N) - 1) {
            // 시작점으로 가는 경로가 있다면 그 경로 리턴
            if(map[cur][0] != 0) return map[cur][0];
            // 없으면 큰 값을 리턴해서 최소값으로 리턴 안되게 함
            else return INF;
        }
        int ret = cache[cur][visited];
        if(ret != -1) return ret;
        ret = INF;
        for(int i = 0; i < N; i++) {
            if((visited & (1 << i)) != 0 || map[cur][i] == 0) continue;
            ret = Math.min(ret, map[cur][i] + tcp(i, visited | (1 << i)));
        }
        return cache[cur][visited] = ret;
    }
}