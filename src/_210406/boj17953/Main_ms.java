package _210406.boj17953;

import java.util.Scanner;

/*
    1. 결과 : 성공
    2. 시간복잡도 : O(NxM^2)
        - 이유 : 3중 포문(첫 번째 포문이 N 번, 두 번째 포문이 M 번, 세 번째 포문이 M 번)이 쓰였기 때문이라고 생각합니다.
                최대 10^7번의 연산이 실행되는 것 같습니다.
*/

public class Main_ms {
    static int N, M;
    static int[][] dessert; // dessert[i][j] : j 날짜에 i 디저트를 먹을 때의 만족감.
    static int[][] dp;      // dp[i][j]      : j 날짜에 i 디저트를 먹었을 때의 누적 최대 만족감.

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        dessert = new int[M][N];
        dp = new int[M][N];

        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                dessert[i][j] = sc.nextInt();

        for (int i = 0; i < dessert.length; i++)
            dp[i][0] = dessert[i][0]; // dp[i][0] : 0 번째 날에 i 디저트를 먹었을 때의 최대 만족도 == 첫날의 디저트 만족도.

        // day : 날짜를 의미하는 인덱스.
        // type : 오늘 먹을 디저트를 의미하는 인덱스.
        // eachType : 디저트들을 돌기 위한 인덱스.
        for (int day = 1; day < N; day++) {
            for (int type = 0; type < M; type++) {
                // i 고정시켜놓고 k를 돕니다.
                for (int eachType = 0; eachType < M; eachType++) {
                    if (eachType == type) { // i와 k가 같으면 만족도가 반감됩니다..
                        dp[type][day] = Math.max(dp[type][day], dp[eachType][day - 1] + dessert[type][day] / 2);
                    } else {                // i와 k가 다르면, 전날까지의 최대 만족도에 오늘 만족도를 더해 최대값을 갱신시킵니다.
                        dp[type][day] = Math.max(dp[type][day], dp[eachType][day - 1] + dessert[type][day]);
                    }
                }
            }
        }

        int answer = 0;
        for (int i = 0; i < M; i++) answer = Math.max(answer, dp[i][N - 1]); // 마지막날의 디저트 만족도를 다 조사해서 큰 값을 뽑아냅니다.

        System.out.println(answer);
    }
}