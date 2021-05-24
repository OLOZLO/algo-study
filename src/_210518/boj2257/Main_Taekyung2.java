package _210518.boj2257;

import java.util.ArrayDeque;
import java.util.Scanner;


/**
 * [실버3] 화학식량
 * 1. 결과 : 성공
 * 2. 시간 복잡도 : O(식 길이)
 *
 * 3. 풀이
 * 	(1) 괄호 처리니까 스택으로 풀자
 *
 *
 * 4. 후기
 * 	- 문자들을 저장할지, 질량값만 저장할지 고민을 좀 했었는데, 더하기만 하면되서 그냥 인트로 했음
 * 	- 적으면서 시뮬레이션 잘해보고 코딩해야 실수 안할 듯
 */


public class Main_Taekyung2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String expression = sc.next();
        ArrayDeque<Integer> stk = new ArrayDeque<>();
        stk.push(0);
        int cur = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // H, C, O 중 하나라면 지금까지의 값에 알맞은 질량을 더해준다
            if(Character.isAlphabetic(c)) {
                cur = getValue(c); // 뒤에 숫자가 나올 때를 위한 임시 저장
                stk.push(stk.pop() + cur);
            } else if(c == '(') {
                stk.push(0); // 여는 괄호가 나오면 새롭게 수를 만든다
            } else if(c == ')') {
                cur = stk.pop(); // 괄호 안에 있던 식의 질량
                stk.push(stk.pop() + cur); // 괄호 이전까지의 질량에다가 더해준다
            } else if(Character.isDigit(c)) {
                // 숫자일 때
                // 앞에서 먼저 한번 더했기 때문에 1 빼고 곱해줌
                stk.push(stk.pop() + (cur * (c - '1')));
            }
        }
        //마지막에 남는 값이 답
        System.out.println(stk.pop());
    }

    // 원자에 맞는 질량 리턴
    public static int getValue(char c) {
        if(c == 'H') return 1;
        else if(c == 'C') return 12;
        else return 16;
    }
}
