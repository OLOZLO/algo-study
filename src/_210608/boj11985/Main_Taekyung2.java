package _210608.boj11985;

import java.util.Arrays;
import java.util.Scanner;



public class Main_Taekyung2 {

    /**
     * [골드5] 오렌지 출하
     * 1. 결과 : 맞았습니다.
     * 2. 시간복잡도 : O(N * M)
     *
     * 3. 풀이
     * 	(1) 다이내믹 프로그래밍 떠올리는게 중요한 듯
     * 	(2) i번째 오렌지를 포장할 때, 현재 상자에 몇 개의 오렌지를 담을지 하나하나 다 해본다
     *
     * 4. 후기
     *  - 오버플로우는 맨날 틀리는듯
     */


    static long[] cache;
    static int[] orange;
    static int max, min;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();

        // cache[i] = i번째 오렌지까지 포장했을 때 최소 비용
        cache = new long[N + 1];
        orange = new int[N + 1];

        for(int i = 1; i <= N; i++)
            orange[i] = sc.nextInt();

        Arrays.fill(cache, Long.MAX_VALUE);
        cache[0] = 0;

        for(int i = 1; i <= N; i++) {
            max = min = orange[i];
            for(int j = 1; j <= M; j++) {
                if(i < j) break;
                // i번째 오렌지를 포장해야되는데, 이 상자에는 j개의 오렌지가 들어가있음, 그 중에서 최대값과 최소값을 구하자
                max = Math.max(max, orange[i - j + 1]);
                min = Math.min(min, orange[i - j + 1]);
                // 현재 포장하고 있느 상자 전까지 최소값에서 현재 포장하는 상자 비용 더함
                cache[i] = Math.min(cache[i], cache[i - j] + K + (long) j * (max - min));
            }
        }

        System.out.println(cache[N]);
    }
}
