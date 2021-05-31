package _210525.boj15658;

import java.util.Scanner;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(4^N)
 *
 * 3. 접근 방식
 *      - 모든 연산자를 다 넣어보면 된당!!
 *
 */
public class Main_girawhale {
    static int[] num, op;
    static int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, N;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        num = new int[N];
        op = new int[4];

        for (int i = 0; i < N; i++) num[i] = sc.nextInt();
        for (int i = 0; i < 4; i++) op[i] = sc.nextInt();

        solve(num[0], 0);
        System.out.println(max);
        System.out.println(min);
    }

    static void solve(int ret, int cnt) {
        if (cnt == N - 1) {
            min = Math.min(min, ret);
            max = Math.max(max, ret);
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (op[i] <= 0) continue;

            op[i]--;
            solve(calc(ret, num[cnt + 1], i), cnt + 1);
            op[i]++;
        }
    }

    static int calc(int n1, int n2, int op) {
        if (op == 0) return n1 + n2;
        if (op == 1) return n1 - n2;
        if (op == 2) return n1 * n2;
        return n1 / n2;
    }
}
