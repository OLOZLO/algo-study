package _210406.boj17953;

import java.io.*;
import java.util.*;

public class Main_Taekyung2 {

    /**
     *  1. 결과 : 성공
     *  2. 시간 복잡도 : O( N * M * M )
     *      - 이유 : dp 부분 문제 개수(N * M) * 부분 문제 시간(반복문 M)
     *  3. 아이디어
     *      - dp(day, prev) : 전 날 prev번째 디저트를 먹었을 때, 오늘 얻을 수 있는 최대 만족도
     *      - 완전 탐색, 메모이제이션
     */

    static int N, M;
    static int[][] dessert, cache;

    static int stoi(String s) { return Integer.parseInt(s); }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = stoi(st.nextToken());
        M = stoi(st.nextToken());
        dessert = new int[M][N];
        cache = new int[M][N];

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            Arrays.fill(cache[i], -1);
            for (int j = 0; j < N; j++)
                dessert[i][j] = stoi(st.nextToken());
        }

        System.out.println(dp(0, 0));
    }

    static int dp(int prev, int day) {
        // 종료 조건
        if(day == N) return 0;

        int ret = cache[prev][day];
        if(ret != -1) return ret;

        ret = 0;
        for(int i = 0; i < M; i++) {
            int cur = dessert[i][day];

            // 첫 번째 날이 아니고, 전 날과 같은 디저트 일 때 만족도 절반
            if(day != 0 && i == prev)
                cur /= 2;

            // 모든 디저트 전부 해본다
            ret = Math.max(ret ,dp(i, day + 1) + cur);
        }

        return cache[prev][day] = ret;
    }
}
