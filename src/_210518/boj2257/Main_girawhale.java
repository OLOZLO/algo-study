package _210518.boj2257;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(N)
 *               문자열 전체를 많아야 2번 반복 => 2N
 *               따라서 O(N)
 *
 * 3. 접근 방식
 *      Stack을 활용
 *      그냥 문자열 첨부터 돌면서 괄호 묶이면 계산해서 넣는 방식으로 함
 *
 */
public class Main_girawhale {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        Deque<Integer> stack = new ArrayDeque<>();
        Map<Character, Integer> map = new HashMap<>();

        map.put('(', -1);
        map.put('H', 1);
        map.put('C', 12);
        map.put('O', 16);

        for (char ch : input.toCharArray()) {
            if (Character.isDigit(ch)) { // 숫자
                stack.push(stack.pop() * (ch - '0')); // 앞에거 곱하기
            } else if (ch == ')') { // 괄호 닫히면 계산해서 한번에 누적
                int sum = 0;
                while (stack.peek() != -1) {
                    sum += stack.pop();
                }
                stack.pop();

                stack.push(sum);
            } else {
                stack.push(map.get(ch));
            }
        }
        System.out.println(stack.stream().mapToInt(i -> i).sum());
    }
}