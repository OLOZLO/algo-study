package _210504.boj15905;

import java.util.Scanner;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(100,000)
 *               최대 100,000부터 1까지 탐색한다. 상수시간만큼 소요
 *
 * 3. 접근 방식
 *      뒤에서부터 등수를 메기고, 5등을 넘어간다면 같은 문제 수의 학생에게 치킨을 줘야함
 *      뒤에서부터 모든 사람의 수를 더하고 5를 빼면 같은 문제 수지만 등수 이외의 학생이 나온다.
 * 
 *
 */
public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int MAX = 100_000;
        int N = sc.nextInt();
        int[] count = new int[MAX + 1];

        while (N-- > 0) {
            count[sc.nextInt()]++; // 같은 문제 수를 푼 학생을 누적
            sc.nextInt(); // 점수 필요없으니까 날림
        }

        int sum = 0;
        for (int i = MAX; i > 0; i--) { // 뒤에서부터 등수 센다
            sum += count[i]; // 여태까지 푼 학생 누적
            if (sum >= 5) { // 5등을 넘었나요?
                System.out.println(sum - 5); // 상 못받는 학생수를 구할 수 있음
                return;
            }
        }

    }
}
