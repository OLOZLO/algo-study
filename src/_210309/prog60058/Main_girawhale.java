package _210309.prog60058;

public class Main_girawhale {
    public String solution(String p) {
        return solve(p);
    }

    String solve(String s) {
        if (s.length() == 0) return s; // ""이면 걍 return

        int idx = 0, cnt = 0;
        for (; idx < s.length(); idx++) { // u를 자르기 위함. 균형잡힌 괄호가 될때까지 반복
            cnt += s.charAt(idx) == '(' ? 1 : -1;
            if (cnt == 0) break;
        }
        String u = s.substring(0, ++idx), v = solve(s.substring(idx)); // 균형 맞춰서 자르고 v는 solve한번 돌림

        if (check(u)) return u + v; // u가 올바른 괄호면 걍 붙여서 반환
        else
            return "(" + v + ")" + // 아니면 괄호 싸고
                    u.substring(1, u.length() - 1).chars() // 앞뒤 잘라서 뒤집어 반환
                            .mapToObj(i -> i == '(' ? ')' : '(')
                            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
    }

    boolean check(String str) {
        int cnt = 0;
        for (char ch : str.toCharArray()) {
            if (ch == '(') cnt++;
            else if (--cnt < 0) return false;
        }
        return true;
    }
}
