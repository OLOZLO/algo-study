package _210309.prog60058;

import java.util.Stack;

public class Main_hz {

    public static void main(String[] args) {
        System.out.println(solution("(()())()"));
        System.out.println(solution(")("));
        System.out.println(solution("()))((()"));
    }

    public static String solution(String p) {
        String answer = "";

        if (isRightBracket(p)) return p;
        answer = getRightBracket(p);

        return answer;
    }

    
    public static String getRightBracket(String w) {
        // 1. 입력이 빈 문자열일 경우, 빈 문자열 반환
        if (w.equals("")) return w;

        // 2. 균형잡힌 괄호 문자열 u, v로 분리합니다.
        int n = seperate(w);
        String u = w.substring(0, n+1);
        String v = w.substring(n+1, w.length());

        // 3-1. u가 올바른 괄호 문자열이면, v에 대해 1단계부터 다시 수행
        if (isRightBracket(u)) return u+getRightBracket(v);
        
        StringBuilder sb = new StringBuilder();
        sb.append("(");                             // 4-1. 빈 문자열에 ( 붙이기
        sb.append(getRightBracket(v));              // 4-2. 문자열 v에 대해 1단계부터 수행한 결과물 이어붙이기
        sb.append(")");                             // 4.3 ) 붙이기
        for (int i = 1; i < u.length()-1; i++) {    // 4-4. u의 첫번째, 마지막 문자 제거하고, 나머지 문자 괄호 뒤집기
            if (u.charAt(i) == '(') sb.append(")");
            else sb.append("(");
        }

        return sb.toString();
    }

    // 올바른 괄호 문자열인지 판별하는 함수
    public static boolean isRightBracket(String s) {
        Stack<Character> st = new Stack<>();
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') st.push('(');
            else {
                if (st.size() == 0) return false;
                st.pop();
            }
        }
        return true;
    }

    // 균형잡힌 문자열로 분리
    public static int seperate(String s) {
        int left = 0, right = 0;

        // 즉 (의 개수와, )의 개수가 같아질 때의 인덱스 리턴
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') left++;
            else right++;

            if (left == right) return i;
        }

        return 0;
    }
}
