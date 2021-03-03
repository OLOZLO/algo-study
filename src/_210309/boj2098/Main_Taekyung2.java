package _210309.boj2098;

import java.util.*;

public class Main_Taekyung2 {
    static int N, INF = (int)1e9, ans = INF;
    static int[][] map, cache;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        map = new int[N][N];
        cache = new int[N][1 << N];
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                map[i][j] = sc.nextInt();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++)
                Arrays.fill(cache[j], -1);
            ans = Math.min(ans, tcp(i, i, 1 << i));
        }
        System.out.println(ans);
    }

    static int tcp(int s, int cur, int visited) {
        if(visited == (1 << N) - 1) {
            if(map[cur][s] != 0) return map[cur][s];
            else return INF;
        }
        int ret = cache[cur][visited];
        if(ret != -1) return ret;
        ret = INF;
        for(int i = 0; i < N; i++) {
            if((visited & (1 << i)) != 0 || map[cur][i] == 0) continue;
            ret = Math.min(ret, map[cur][i] + tcp(s, i, visited | (1 << i)));
        }
        return cache[cur][visited] = ret;
    }
}