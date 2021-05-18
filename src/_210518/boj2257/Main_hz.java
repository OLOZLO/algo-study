package _210518.boj2257;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main_hz {

    /**
     * 1. 결과 : 실패 -> 성공
     * 2. 시간복잡도 : O(100)...?
     * 3. 실패 이유
     *  : 괄호가 중복되는 경우를 고려하지 못했습니다...
     * 4. 풀이
     *  : 일반적으로 괄호쌍 확인하기위해 스택을 사용하는 것처럼
     *    1) 여는 괄호 => 스택에 -1 삽입
     *    2) 닫는 괄호 => 여는 괄호가 나올 때까지 스택에서 꺼내고, 꺼낸 값들의 합을 다시 스택에 저장
     *    3) 숫자 => 스택에서 가장 최근에 저장된 값을 꺼내 숫자만큼 곱한 뒤 다시 스택에 저장
     *    4) C, H, O => 각 원소에 해당하는 원소값 스택에 저장
     *
     * */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        Stack<Integer> st = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char now = s.charAt(i);

            if (now == '(') st.add(-1); // 여는 괄호를 -1로 저장
            else if (now == ')') {  // 닫는 괄호를 만났을 때
                int sub = 0;

                // 괄호로 묶인 원자들은 하나의 새로운 원자와 같은 작용을 하기 때문에 괄호 안의 원자값들을 다 더해 하나의 원자값으로 만듦
                while (!st.isEmpty()) {
                    int pop = st.pop();

                    if (pop == -1) break;
                    else sub += pop;
                }
                st.add(sub);
            } else if (Character.isDigit(now)) {
                st.add(st.pop()*(now-'0'));
            } else {
                if (now == 'H') st.add(1);
                else if (now == 'C') st.add(12);
                else if (now == 'O') st.add(16);
            }
        }

        int result = 0;
        while(!st.isEmpty()) result += st.pop();

        System.out.println(result);
    }
}
