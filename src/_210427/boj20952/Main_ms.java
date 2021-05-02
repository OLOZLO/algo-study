package _210427.boj20952;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

/*
    1. 결과 : 실패(출력 초과)
        - 로직이 어딘가 잘못된듯
    2. 시간복잡도 : O(N)
        - for문을 한 번만 돈다.
    3. 풀이
        0. N, M 둘 다 최대 10^5 이므로, 이중 포문으로 작성하면 10^10, 1초를 넘는다. -> 아예 작성 안함. 예전이었으면 일단 이렇게 짰을듯.
        1. 배열 B 각 요소까지의 누적합을 요소로 배열을 하나 만든다. 예를 들어, B={1, 2, 3} 이면, 누적합 배열 sum={1, 3, 6}
        2. 배열 A의 각 요소를 돌며, 7이 배수가 되기 위한 최소값 delta를 구한다. 예를 들어, 요소가 A[i]가 7이면 delta는 7이 됨.
        3. 위 2번에서 구한 delta 값이 누적합 배열에 있는지를 확인한다.
            - 있다면? 7의 배수가 만들어지므로, 해당 값은 배제.
            - 없다면? 7의 배수가 안되므로, 해당 값을 정답에 추가.
        4. 만약, 3번 과정에서 배열 A의 모든 요소가 7의 배수를 만든다면, 해당 연산을 무르기 위 배열 A 전체를 정답에 추가.
*/

public class Main_ms {
    static int N, M;
    static int[] A, B;
    static final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        A = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        B = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        long[] sum = new long[M];
        HashSet<Long> hashSet = new HashSet<>();
        sum[0] = B[0] % MOD;
        hashSet.add(sum[0]);

        for (int i = 1; i < M; i++) {
            sum[i] = (sum[i - 1] + B[i]) % MOD;
            hashSet.add(sum[i]);
        }

        ArrayList<Long> list = new ArrayList<>();
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            long delta = ((A[i] / 7 + 1) * 7) % MOD - A[i];

            if (hashSet.contains(delta)) {
                cnt++;
                continue;
            }

            list.add((A[i] + sum[M - 1]) % MOD);
        }

        StringBuilder answer = new StringBuilder();

        if (cnt == N) {
            answer.append(N + "\n");
            for (int i = 0; i < N; i++) answer.append(A[i] + " ");
        } else {
            answer.append(list.size() + "\n");
            for (long num : list) answer.append(num + " ");
        }

        System.out.println(answer);
    }
}
