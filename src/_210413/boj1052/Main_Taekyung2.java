package _210413.boj1052;

import java.util.Scanner;

public class Main_Taekyung2 {

    /**
     * [실버1] 물병
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(N * logN)
     * 3. 아이디어
     *      - 1, 2, 4, 8, 16 ... 이렇게 묶을 수 있음
     *      - 비트로 치환해서 K개 이하로 가능한가 확인
     *
     */

    static int N, K;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        int buy = 0;

        // K개로 가능할 때까지 물병을 구입
        while(!isPossible(N + buy))
            buy++;

        System.out.println(buy);
    }


    // bit_Count
    static boolean isPossible(int bottle) {
        int need = 0;

        while(bottle > 0) {
            bottle &= bottle - 1;
            need++;
        }

        return need <= K;
    }
}
