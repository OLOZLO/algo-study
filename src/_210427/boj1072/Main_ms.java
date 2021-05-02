package _210427.boj1072;

import java.math.BigInteger;
import java.util.Scanner;

/*
    1. 결과 : 실패(틀렸습니다)
    2. 시간복잡도 : O(1)
        - 한 번의 계산.
    3. 풀이
        1. 앞으로의 모든 게임은 이기므로, 게임 횟수(X)와 이긴 게임(Y)는 같은 숫자씩 증가한다.
        2. 승률은 항상 증가하는 쪽으로 변화한다.
        3. 승률(Z)이 변하기 위한 조건은 아래 수식으로 표현할 수 있다. (a : 추가로 게임을 하는 판 수)
            -> y/x + 1/100 <= (y+a)/(x+a)
            -> ...
            -> a >= x^2 / (99x-100y)
        4. 위 수식의 분모가 음수면 Z가 절대 변하지 않는다고 판단했음. 따라서 -1을 출력. -> 다시 확인해 보고 주석 남기겠음.
        5. 아닌 경우, 위 수식을 계산한 결과를 답으로 출력하자.
            - long 범위를 넘어가므로, BigInteger를 사용했음.
            - 나눈 결과의 나머지가 0이면, 딱 떨어졌으므로 그 답이 정답.
            - 나머지 존재 시, 1을 증가시켜줘야 됨.
*/

public class Main_ms {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt();
        int Y = sc.nextInt();

        if (99 * X - 100 * Y < 0) System.out.println(-1);
        else {
            BigInteger XX = new BigInteger(String.valueOf(X));
            BigInteger YY = new BigInteger(String.valueOf(Y));

            BigInteger bi1 = XX.multiply(XX);
            BigInteger bi2 = XX.multiply(new BigInteger(String.valueOf(99)));
            BigInteger bi3 = YY.multiply(new BigInteger(String.valueOf(100)));
            BigInteger bi4 = bi2.subtract(bi3);

            BigInteger m = bi1.divide(bi4);
            BigInteger r = bi1.remainder(bi4);

            if (r.equals(new BigInteger("0"))) System.out.println(m);
            else System.out.println(m.add(new BigInteger("1")));
        }
    }
}
