package _210615.boj2631;

import java.util.*;

public class Main_Taekyung2 {

    /**
     * [골드5] 줄세우기
     * 1. 결과 : 맞았습니다.
     * 2. 시간복잡도 : O(N ^ 2)
     *
     * 3. 후기
     * 	- 예시 설명을 보다 보니 최장증가부분수열을 제외하고 옮겨주면 되는 것을 알게 되었음
     */


    static int N;
    static int[] children, cache;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        children = new int[N + 1];
        cache = new int[N + 1];

        for(int i = 1; i <= N; i++)
            children[i] = sc.nextInt();

        // 0번에 작은 값 넣어놓으면 0번부터 시작한 최장 증가 부분 수열 길이 - 1이 가장 긴 길이가 된다
        System.out.println(N - dp(0) + 1);
    }


    // 최장 증가 부분 수열 길이 구하는 dp
    public static int dp(int idx) {
        int ret = cache[idx];
        if(ret != 0)
            return ret;
        ret = 1;
        for(int next = idx + 1; next <= N; next++) {
            if(children[idx] < children[next])
                ret = Math.max(ret, dp(next) + 1);
        }

        return cache[idx] = ret;
    }
}
