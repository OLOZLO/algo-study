package _210525.boj15658;

import java.util.Scanner;

/*
 *   1. 결과 : 맞았습니다.
 *   2. 시간복잡도 : O(4^N)
 *       - 이유
 *           - 2 <= N <= 11, 피연산자의 최대 개수는 11개.
 *           - 11개의 피연산자 사이에 10개의 연산자가 등장 가능.
 *           - 각각에 4개의 사칙 연산 연산자가 가능.
 *           - 따라서, O(4^N)..?
 *   3. 접근 방식
 *       - 완전 탐색.
 *       - 메서드 호출을 1번 째 요소로 시작. 즉, 두 번째 피연산자부터 연산자를 적용시킴.
 */

public class Main_ms {
    static int N;                    // N   : 피연산자의 개수.
    static int[] arr;                // arr : 피연산자가 담긴 배열.
    static int[] opCnt = new int[4]; // opCnt : +,-,*,/ 각 연산자의 개수
    static int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        arr = new int[N];

        for (int i = 0; i < N; i++) arr[i] = sc.nextInt();
        for (int i = 0; i < 4; i++) opCnt[i] = sc.nextInt();

        dfs(1, arr[0]);

        System.out.println(max + "\n" + min);
    }

    /*
    void dfs(int idx, int sum)
        - 파라미터
            - idx : 현재 바라보는 인덱스.
            - sum : 중간 계산 결과 값.
        - 리턴 값 : 없음.
        - 수행 과정
            - idx가 배열 끝 인덱스를 넘어간다면, min 과 max 갱신.
            - idx가 배열 범위 내에 들어온다면, 각 연산자의 개수를 하나 낮춘 채 파라미터를 조정(다음 인덱스와 sum에 연산자 적용된 계산 값 전달) 후 재귀 호출.
     */
    static void dfs(int idx, int sum) {
        if (idx == N) {
            min = Math.min(min, sum);
            max = Math.max(max, sum);

            return;
        }

        if (opCnt[0] > 0) {
            opCnt[0]--;
            dfs(idx + 1, sum + arr[idx]);
            opCnt[0]++;
        }

        if (opCnt[1] > 0) {
            opCnt[1]--;
            dfs(idx + 1, sum - arr[idx]);
            opCnt[1]++;
        }

        if (opCnt[2] > 0) {
            opCnt[2]--;
            dfs(idx + 1, sum * arr[idx]);
            opCnt[2]++;
        }

        if (opCnt[3] > 0) {
            opCnt[3]--;
            dfs(idx + 1, sum / arr[idx]);
            opCnt[3]++;
        }
    }
}
