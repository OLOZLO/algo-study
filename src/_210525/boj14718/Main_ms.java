package _210525.boj14718;

import java.util.Scanner;

/*
 *   1. 결과 : 맞았습니다.
 *   2. 시간복잡도 : O(N^4)
 *       - 이유
 *           - for문 중첩 네 번.
 *           - 10^8, 1억으로 딱 맞을 듯? 빡빡한 테케가 없는건지 시간은 488ms 나옵니다.
 *   3. 접근 방식
 *       - 완전 탐색.
 *       - 진수의 힘, 민첩, 지능 스탯을 주어진 병사들의 스탯으로 미리 결정해둔다.
 *       - 모든 병사를 순회하며, 위에서 결정해둔 진수의 힘, 민첩, 지능이 모두 병사의 것 이상인 횟수를 센다.
 *       - 횟수가 K 이상이면, 스텟 합을 갱신해준다.
 */

public class Main_ms {
    static int N, K;      // N : 병사 수, K : 적어도 이겨야 할 병사 수
    static int[][] stats; // stats : 병사의 힘, 민첩, 지능 스탯

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        stats = new int[N][3];
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            stats[i][0] = sc.nextInt();
            stats[i][1] = sc.nextInt();
            stats[i][2] = sc.nextInt();
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    int cnt = 0;

                    for (int n = 0; n < N; n++) {
                        // 진수의 힘, 민첩, 지능 스탯이 해당 n번째 병사의 것 이상이면 cnt를 센다.
                        if (stats[i][0] >= stats[n][0] && stats[j][1] >= stats[n][1] && stats[k][2] >= stats[n][2]) {
                            cnt++;
                        }
                    }

                    // cnt가 K 이상이면, min 을 더 적은 스탯 합으로 갱신해준다.
                    if (cnt >= K) min = Math.min(min, stats[i][0] + stats[j][1] + stats[k][2]);
                }
            }
        }

        System.out.println(min);
    }
}
