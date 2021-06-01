package _210601.boj2529;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 *   https://ddb8036631.github.io/boj/2529_부등호/
 *   1. 결과 : 맞았습니다.
 *   2. 시간복잡도 : O(10!)
 *       - 이유
 *           - 부등호 개수(k)가 최대 9개 -> 피연산자는 최대 10개 등장 가능.
 *           - 재귀 호출시, 하나씩 뽑아 남은 개수들 중 하나를 선택하는 과정이라고 생각했다. 따라서, 10!
 *   3. 접근 방식
 *       - 완전 탐색.
 *       - max와 mind을 문자열로 설정. Long.parseLong() 사용해 값을 갱신했다.
 */

public class Main_ms {
    static int K;          // K : 입력으로 주어지는 부등호의 개수를 저장.
    static String[] oper;  // oper : 입력으로 받는 부등호들 저장하는 배열.
    static boolean[] used; // used : 0~9 숫자의 사용 유무를 저장하는 배열.
    static String max = "0", min = "9876543210"; // 숫자 맨 앞에 0 나올 경우 대비해 문자열로 취급.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        K = Integer.parseInt(br.readLine());
        oper = br.readLine().split(" ");
        used = new boolean[10];

        // 맨 먼저 피연산자 하나를 정해두고 재귀 호출을 한다.
        for (int i = 0; i <= 9; i++) {
            used[i] = true;
            dfs(0, String.valueOf(i), i);
            used[i] = false;
        }

        System.out.println(max + "\n" + min);
    }

    static void dfs(int idx, String s, int prev) {
        // 인덱스 범위 벗어나면 최대, 최소값 갱신시키자.
        if (idx == K) {
            max = Long.parseLong(s) > Long.parseLong(max) ? s : max;
            min = Long.parseLong(s) < Long.parseLong(min) ? s : min;

            return;
        }

        for (int i = 0; i <= 9; i++) {
            if (used[i]) continue;

            // 부등호 올바르게 사용 가능하다면, 선택한 숫자 문자열에 이어붙이고 재귀 통해 쭉 진행하자.
            if ((oper[idx].equals("<") && prev < i) || (oper[idx].equals(">") && prev > i)) {
                used[i] = true;
                dfs(idx + 1, s + i, i);
                used[i] = false;
            }
        }
    }
}
