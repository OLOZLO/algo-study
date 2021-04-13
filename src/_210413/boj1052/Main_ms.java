package _210413.boj1052;

import java.util.Scanner;

/*
    https://ddb8036631.github.io/알고리즘%20풀이/백준_1052_물병/

    1. 결과 : 성공
    2. 시간복잡도 : 어케 계산해야 되나요?
    3. 후기
        - 아이디어는 블로그에 적어놨습니다.
        - Integer의 스태틱 메소드 bitCount가 있는걸 첨 알았네요 활용하도록 하겠습니다. 제가 어케 풀었는 지 날 것 그대로 올리는 게 더 좋을 거 같아서 수정 안했어요.
        - 시험 시간 동안 아이디어를 생각해 내지 못했습니다. 문제를 어떻게 접근해야 할 지 생각하는 법을 좀 길러야겠습니다.
*/

public class Main_ms {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        int answer = 0;

        while (true) {
            char[] binary = Integer.toBinaryString(N).toCharArray(); // 정수 N을 이진수 포맷의 문자열로 변환한 뒤 문자배열로 변환합니다.
            int cnt = 0;

            for (int i = 0; i < binary.length; i++) if (binary[i] == '1') cnt++; // 1개수를 모두 셉니다.

            if (cnt <= K) { // 1 개수가 K개 이하면, K개 물병으로 병합이 가능한 것이므로 출력하고 빠져나옵니다.
                System.out.println(answer);
                break;
            }

            // K개 이하의 물병을 만들지 못하면, N을 증가시키고 물병 개수도 추가시킨 뒤 위 과정을 반복합니다.
            N++;
            answer++;
        }
    }
}
