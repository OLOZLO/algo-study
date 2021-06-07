package _210601.boj2529;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(N!)
 *               모든 경우를 확인하는 완탐을 사용
 *               최악의 경우 10!으로 안정권
 *
 * 3. 접근 방식
 *      숫자를 차례대로 넣어보면서 부등호의 방향과 일치하지 않는 숫자가 들어오면 빠져나오는 백트래킹 방법을 사용
 *
 *
 */
public class Main_girawhale {
    static long[] nums;
    static String[] op;
    static long max, min = Long.MAX_VALUE;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        op = br.readLine().split(" ");
        nums = new long[N + 1];

        solve(0, new boolean[10]);

        String format = "%0" + (N + 1) + "d"; // %0d 포맷을 사용해 길이가 부족하면 앞에 0을 채운당
        System.out.printf(format + '\n', max);
        System.out.printf(format, min);
    }

    static void solve(int idx, boolean[] visit) {
        if (idx > N) {
            long num = Arrays.stream(nums).reduce(0, (s, n) -> s * 10 + n); //  reduce를 사용해 배열을 long 숫자로 변경
            min = Math.min(min, num);
            max = Math.max(max, num);
            return;
        }

        for (int i = 0; i < 10; i++) {
            if (visit[i]) continue; // 사용한 숫자 패스
            if (idx > 0) {
                // 부등호와 일치하지 않는 숫자가 들어오면 패스
                if ((op[idx - 1].equals("<") && nums[idx - 1] > i) ||
                        (op[idx - 1].equals(">") && nums[idx - 1] < i)) continue;
            }

            visit[i] = true;
            nums[idx] = i;
            solve(idx + 1, visit); // idx를 증가하면서 모든 경우를 탐색
            visit[i] = false;
        }
    }
}
