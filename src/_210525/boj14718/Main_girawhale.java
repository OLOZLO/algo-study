package _210525.boj14718;

import java.util.Scanner;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(100^4)
 *
 * 3. 접근 방식
 *      - 병사의 스탯을 기준으로 모든 경우를 살펴보면 된다!
 *      - 모든 스탯의 조합의 합을 살펴보면서 K명 이상인 경우의 최솟값을 구하장
 *
 */
public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), K = sc.nextInt();
        int[][] arr = new int[N][3];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < 3; j++)
                arr[i][j] = sc.nextInt();

        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    int cnt = 0;

                    for (int[] a : arr) {
                        if (arr[i][0] >= a[0] && arr[j][1] >= a[1] && arr[k][2] >= a[2]) cnt++;
                    }
                    if (cnt >= K) {
                        ans = Math.min(ans, arr[i][0] + arr[j][1] + arr[k][2]);
                    }
                }
            }
        }
        System.out.println(ans);

    }
}
