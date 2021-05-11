package _210511.boj6236;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(N)
 *              이분 탐색 = O(log10^9) ≒ O(log(2^10)^3) <= 30
 *              인출 횟수 세기 = O(N) <= 100,000
 *
 *               => O(N*log10^9) = O(N)
 *
 * 3. 접근 방식
 *      문제가 불친절해서 핵 헤맴...
 *      
 *      인출 금액이 고정하면 인출 횟수를 셀수 있다!
 *      그렇다면 M을 넘는지 체크하면서 범위를 줄여보자 => 이분탐색
 *
 */
public class Main_girawhale {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] day = new int[N]; // 1일 사용 금액
        int s = 0, e = (int) 1e9, ans = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            day[i] = Integer.parseInt(br.readLine());
            s = Math.max(s, day[i]); // 최소 금액은 항상 사용금액 이상이어야 함
        }

        while (s <= e) {
            int m = (s + e) / 2; // 인출 금액

            int count = 0; // 인출 횟수
            int money = 0; // 잔액
            for (int i = 0; i < N; i++) {
                if (money < day[i]) { // 사용 금액보다 현재 잔액이 부족하면 
                    money = m; // 돈을 인출하고 횟수 증가
                    count++;
                }
                money -= day[i]; // 오늘 사용 금액 빼기
            }
            if (count > M) { // M보다 크면 안됨
                s = m + 1; // 범위를 늘려줌
            } else { // M보다 작으면 횟수를 강제적으로 늘리면 M번을 채울 수 있음
                ans = m; //값 저장
                e = m - 1; // 범위 줄임
            }
        }
        System.out.println(ans);
    }
}

