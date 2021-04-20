package _210413.boj1052;

import java.util.Scanner;

/*
1. 결과 : 성공
2. 시간 복잡도 : 최악 O(10^7)
    - 이유 : N이 증가할 수 있는 최대 수는 10^7이기 때문에 1억을 넘지 않아 가능

3. 풀이 : 물을 합칠 수 있는 경우는 같은 숫자일 때만 가능
            따라서, 물은 2^n으로만 증가가 가능하다.
            N이 최소한의 물병을 만들 수 있는 숫자는 N을 2진수로 나타냈을 때의 1의 수와 동일하므로
            N이 K이하의 물병이 나올 수 있도록 계속 증가시켜보면 된다.
*/
public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), K = sc.nextInt();

        int num = N;
        while (Integer.bitCount(num) > K) // 2진수로 변환했을 때의 1의 수를 반환하는 함수
            num++; // K개 이상이면 숫자를 증가시켜보자!

        System.out.println(num - N); // K개 이하가 나오면 처음 숫자에서 얼마나 증가했나 계산해보기
    }
}
