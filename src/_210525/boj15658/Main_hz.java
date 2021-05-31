package _210525.boj15658;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_hz {

    /**
     * [실3] 연산자 끼워넣기 (2)
     *
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(4^N)
     *    - 2 <= N <= 11, N-1 <= 연산자의 갯수 <= 4N
     *    - 피연산자와 피연산자 사이에 최악의 경우 모든 연산자를 대입할 수 있으므로 4^N이라 생각했습니다.
     * 3. 풀이 : dfs를 이용하여 완전탐색을 진행했습니다.
     *
     * */

    public static int[] arr;
    public static int min, max;

    public static int stoi(String s) {return Integer.parseInt(s);}
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = stoi(br.readLine());
        arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) arr[i] = stoi(st.nextToken());

        int[] op = new int[4];  // 주어진 연산자의 개수 ([0] + [1] - [2] * [3] /)
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 4; i++) op[i] = stoi(st.nextToken());

        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
        dfs(1, op, arr[0]);

        System.out.println(max);
        System.out.println(min);
    }

    public static void dfs(int idx, int[] op, int val) {

        if (idx >= arr.length) {    // 식을 완성한 경우 max, min 값 갱신
            min = Math.min(min, val);
            max = Math.max(max, val);
            return;
        }

        for (int i = 0; i < 4; i++) {
            // 해당 연산자를 끼워넣을 수 있는 경우, 해당 연산자를 사용하여 계산한 값을 인자로 넘김
            if (op[i] > 0) {
                op[i]--;

                if (i == 0) dfs(idx+1, op, val+arr[idx]);
                else if (i == 1) dfs(idx+1, op, val-arr[idx]);
                else if (i == 2) dfs(idx+1, op, val*arr[idx]);
                else dfs(idx+1, op, val/arr[idx]);

                op[i]++;
            }
        }
    }
}
