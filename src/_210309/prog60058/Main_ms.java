package _210309.prog60058;

import java.util.Stack;

public class Main_ms {
    public static String solution(String p) {
        String answer = divide(p); // divide 메소드 호출로, 최종적인 "올바른 괄호 문자열"을 받아오자.

        return answer;
    }

    // divide 메소드는 아래 동작을 함.
    //      1. 먼저 파라미터 p를 u랑 v로 나누자. (p=u+v)
    //      2. 문자열 u가 "올바른 괄호 문자열" 이면, v에 대해 1부터 다시 수행.
    //          2-1. 수행한 결과 문자열을 u에 이어 붙인 후 리턴.
    //      3. 문자열 u가 "올바른 괄호 문자열"이 아니면 아래 과정을 수행.
    //          3-1. 빈 문자열에 첫 번째 문자로 '('를 붙인다.
    //          3-2. v에 대해 1단계부터 재귀적으로 수행한 결과 문자열을 이어 붙인다.
    //          3-3. 3-2의 결과 문자열 뒤에 ')'를 붙인다.
    //          3-4. u의 첫 번째와 마지막 문자를 제외한 나머지 문자열의 괄호 방향을 뒤집어서 뒤에 붙인다.
    //                  (는 )로, )는 (로 바꾸자.
    //          3-5. 3-1 ~ 3-4 과정을 통해 만들어진 문자열을 리턴.
    //          3-6. 3을 통해 만들어진 문자열은 아래와 같다.
    //                  ( + 3-2 결과 + ) + 괄호 뒤집어 만들어진 문자열 결과
    static String divide(String p) {
        if (p.length() == 0) return "";

        int endIdx = getEndIdx(p) + 1;
        String u = p.substring(0, endIdx);
        String v = p.substring(endIdx);

        if (isRight(u)) {
            return u + divide(v);
        } else {
            StringBuilder tmp = new StringBuilder("(");
            tmp.append(divide(v));
            tmp.append(")");

            for (int i = 1; i < u.length() - 1; i++) {
                if (u.charAt(i) == '(') tmp.append(")");
                else tmp.append("(");
            }

            return tmp.toString();
        }
    }

    // getEndIdx : 문자열 u의 끝나는 인덱스를 리턴하는 메소드
    // (와 )의 개수가 같아지는 지점의 인덱스를 리턴한다.
    static int getEndIdx(String p) {
        int left = 0, right = 0, idx = 0;

        while (idx < p.length()) {
            if (p.charAt(idx) == '(') left++;
            else if (p.charAt(idx) == ')') right++;

            if (left == right) break;

            idx++;
        }

        return idx;
    }

    // isRight 메소드는 균형잡힌 괄호 문자열이면 true를, 균형잡힌 괄호 문자열이 아니면 false를 리턴.
    static boolean isRight(String u) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < u.length(); i++) {
            if (u.charAt(i) == '(') stack.push('(');
            else if (!stack.isEmpty()) stack.pop();
        }

        return stack.isEmpty() ? true : false;
    }

    public static void main(String[] args) {
        solution("(()())()");
//        solution(")(");
//        solution("()))((()");
    }
}
