package _210601._210608.boj1669;

import java.util.Scanner;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(1)..? 아마...
 *
 * 3. 접근 방식
 *      - 1 + 2 + 3 + ... + 3 + 2 + 1 형태로 반복된다는 것을 발견
 *      - 그래서 값 저장해놀라고 찍어봤더니 저런 값은 제곱값이더라... 배웠는지 아닌지 기억도 안남
 *      - 암튼 그래서 제곱 넘기 전까지 값 증가시키고 봤더니 경우가 2가지
 *          1. n * n - n 보다 차가 작으면 (n^2 - 1)
 *          2. 아니면 (n^2 - 2)
 * 4. 후기
 *      음... 첨엔 갱장히 당황... 모든 경우를 결국 손으로 써봐야 규칙성 파악이 되는듯
 *      일단 손으로 쓰는 연습을 하는게 좋을 듯
 *
 */
public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int diff = -(sc.nextInt() - sc.nextInt()); // 값 자체는 필요없고 차만 필요해서 이렇게 저장함
        if (diff == 0) { // 값이 같으면 키가 클 필요가 없움
            System.out.println(0);
            return;
        }

        long n = 0; // 이거 왜 long인지 모르겠음... 왜져?
        while (n * n < diff) {
            n++;
        }

        System.out.println(n * 2 - ((n * n - n < diff) ? 1 : 2));
    }
}
