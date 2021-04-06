package _210406.boj17953;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

// 1. 결과 : 성공
// 2. 시간 복잡도 : O(NM^2)
// - 이유
// 날짜 수 = N
// 디저트 수 = M
// ===============
// 날짜를 하나씩 증가시키면서 현재 먹을 음식과 전날 먹을 음식을 모두 매칭
// 따라서, N * M * M = 10^5 * 10^2 = 10^7
public class Main_girawhale {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 날짜 수
        int M = Integer.parseInt(st.nextToken()); // 디저트 수

        int[][] dessert = new int[M][N]; //[디저트][날짜]
        for (int i = 0; i < M; i++)
            dessert[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int[][] dp = new int[M][N]; // dp를 저장할 배열
        for (int i = 0; i < M; i++) // 첫날은 전날 먹은 디저트가 없기 때문에 그대로 복사
            dp[i][0] = dessert[i][0];

        for (int i = 1; i < N; i++) { // 날짜별 탐색
            for (int j = 0; j < M; j++) { // 전날 먹은 디저트
                for (int k = 0; k < M; k++) { // 오늘 먹을 디저트
                    if (j == k) // 전날 먹은거랑 겹치면?
                        dp[j][i] = Math.max(dp[j][i], dessert[j][i] / 2 + dp[k][i - 1]); // 오늘 먹을 디저트의 절반만 누적
                    else
                        dp[j][i] = Math.max(dp[j][i], dessert[j][i] + dp[k][i - 1]); // 아니면 다 누적
                }
            }
        }

        System.out.println(IntStream.range(0, M).map(i -> dp[i][N - 1]).max().getAsInt()); // 막날에서 가장 높은 값이 정답!
    }
}
