package _210615.boj2631;

import java.util.Scanner;

/*
 *   [골드5] 줄세우기
 *
 *   https://ddb8036631.github.io/boj/2631_줄세우기/
 *   1. 결과 : 풀지 못함 -> 풀이보고 품.
 *   2. 시간복잡도 : O(N^2)
 *       - 이유 : 이중 for문 사용했기 때문.
 *   3. 접근 방식
 *       - LIS의 동적 계획법(DP) 접근.
 *       - 문제에서 말하는 "최소 변경 횟수"가 곧 (N - LIS의 길이) 였음.
 *   4. 후기
 *       - "DP"를 이용한 LIS 알고리즘을 배우고 정리했음.
 *       - LIS를 구현하는 O(logN) 시간 복잡도가 걸리는 "이분 탐색" 방법도 있다던데, 나중에 찾아봐야겠다.
 */

public class Main_ms {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] nums = new int[N];
        int[] dp = new int[N];
        int max = 0;

        for (int i = 0; i < N; i++) nums[i] = sc.nextInt();

        for (int i = 0; i < N; i++) {
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) { // 증가 양상을 띄면, 최장 증가 길이를 갱신해줌.
                    dp[i] = dp[j] + 1;
                }
            }
        }

        for (int i = 0; i < N; i++) max = Math.max(max, dp[i]);

        System.out.println(N - max);
    }
}
