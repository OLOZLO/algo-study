package _210406.boj17953;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/*

결과 : 성공
시간복잡도 : O(N*M*MlogM) (최악의 경우 O(10^7*log10))
N*M크기의 dp를 계산하는 과정에서 정렬(MlogM)을 사용했기 때문에 위와 같이 생각했습니다.

*/
public class Main_hz {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int D = Integer.parseInt(st.nextToken());   // DAY
        int K = Integer.parseInt(st.nextToken());   // KIND

        int[][] food = new int[K][D];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < D; j++) {
                food[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] dp= new int[K][D];  // dp[K][D] = 음식K를 D번째 날에 먹을 경우 만족감의 최댓값
        for (int i = 0; i < K; i++) dp[i][0] = food[i][0];

        for (int day = 1; day < D; day++) {
            int[] tmp = new int[K]; // 전날 
            for (int i = 0; i < K; i++) tmp[i] = dp[i][day-1];
            Arrays.sort(tmp);

            for (int f = 0; f < K; f++) {
                // 음식의 종류가 하나이면 (전날 만족감의 최대값 + 현재 먹으려는 음식의 만족감 / 2) 저장
                if (K == 1) dp[f][day] = dp[f][day-1]+food[f][day]/2;
                else {
                    // 전날 만족감이 최대였던 음식과 현재 먹으려는 음식이 같을 경우
                    // (전날 만족감의 최대값 + 현재 먹으려는 음식의 만족감 / 2) 과 (전날 만족감이 두번째 높은 값 + 현재 먹으려는 음식의 만족감) 중 최대값 저장
                    if (dp[f][day-1] == tmp[K-1]) dp[f][day] = Math.max(dp[f][day-1]+(food[f][day]/2), tmp[K-2]+food[f][day]);
                    // 전날 먹은 음식과 현재 먹으려는 음식이 다를 경우 전날까지 만족감의 최댓값 + 현재 음식의 만족감 저장
                    else dp[f][day] = tmp[K-1]+food[f][day];
                }
            }
        }

        int result = 0;
        // 정답은 마지막날 각 음식을 먹었을 때의 만족감 중 최댓값
        for (int i = 0; i < K; i++) result = Math.max(result, dp[i][D-1]);

        System.out.println(result);
    }
}
