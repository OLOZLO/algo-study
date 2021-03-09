package _210309.prog60058;

public class Main_Taekyung2 {
    public String solution(String p) {
        // 이거는 시키는 대로 하면 됩니다
        if(p.length() == 0) return "";
        int m = isBalance(p);
        String u = p.substring(0, m), v = p.substring(m);
        if(isCorrect(u)) return u + solution(v);
        else return "(" + solution(v) + ")" + reverse(u);
    }

    // 처음이랑 마지막 한 개 떼고, 괄호 뒤집어서 출력하는 거
    String reverse(String p) {
        char[] c = p.substring(1, p.length() - 1).toCharArray();
        for(int i = 0; i < c.length; i++)
            c[i] = (c[i] == '(') ? ')' : '(';
        return new String(c);
    }

    // 균형 잡힌 괄호 나오면, 그 인덱스 리턴
    int isBalance(String p) {
        int i = 0, cnt = 0;
        for(; i < p.length(); i++) {
            if(p.charAt(i) == '(') cnt++;
            else cnt--;
            if(cnt == 0) break;
        }
        return i + 1;
    }

    // 올바른 괄호인지 확인합니다
    boolean isCorrect(String p) {
        int cnt = 0;
        for(int i = 0; i < p.length(); i++) {
            if(p.charAt(i) == '(') cnt++;
            else cnt--;
            if(cnt < 0) return false;
        }
        return cnt == 0;
    }
}