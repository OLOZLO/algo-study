package _210601.boj2529;

import java.util.Scanner;

/**
 * [실버3] 부등호
 * 1. 결과 : 맞았습니다.
 * 2. 시간복잡도 : O((k+1)!)
 *
 * 3. 후기
 * 	- 오버플로우 간과해서 틀려버렸음
 *
 */

public class Main_Taekyung2 {
    static int K;
    // 오버플로우를 막기 위해 long으로 선언합니다
    static long max = Long.MIN_VALUE, min = Long.MAX_VALUE;
    static char[] op;
    static boolean[] chk = new boolean[10];
    static int[] pick;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        K = sc.nextInt();
        op = new char[K];
        pick = new int[K + 1];
        for(int i = 0; i < K; i++)
            op[i] = sc.next().charAt(0);

        // 숫자 K + 1개 순열을 모두 구합니다
        permutation(0);
        // 0 개수 맞춰서 출력합니다
        System.out.printf("%0" + (K + 1) + "d\n", max);
        System.out.printf("%0" + (K + 1) + "d", min);
    }

    public static void permutation(int idx) {
        if(idx == K + 1) {
            // 부등호 조건에 맞는지 검사합니다, 하나라도 안맞으면 return
            for(int i = 0; i < K; i++)
                if((op[i] == '<' && pick[i] >= pick[i + 1]) ||
                        (op[i] == '>' && pick[i] <= pick[i + 1]))
                    return;

            // char 배열을 숫자로 만듭니다
            long num = 0, p = 1;
            for(int i = K; i >= 0; i--) {
                num += pick[i] * p;
                p *= 10;
            }

            // 최대값, 최소값 비교
            max = Math.max(max, num);
            min = Math.min(min, num);
            return;
        }

        // 순열 만드는 작업
        for(int n = 0; n < 10; n++) {
            if(chk[n]) continue;
            chk[n] = true;
            pick[idx] = n;
            permutation(idx + 1);
            chk[n] = false;
        }
    }
}
