package _210309.prog60058;

public class Main_Taekyung2 {
    public String solution(String p) {
        if(p.length() == 0) return "";
        int m = isBalance(p);
        String u = p.substring(0, m), v = p.substring(m);
        if(isCorrect(u)) return u + solution(v);
        else return "(" + solution(v) + ")" + reverse(u);
    }

    String reverse(String p) {
        char[] c = p.substring(1, p.length() - 1).toCharArray();
        for(int i = 0; i < c.length; i++)
            c[i] = (c[i] == '(') ? ')' : '(';
        return new String(c);
    }

    int isBalance(String p) {
        int i = 0, cnt = 0;
        for(; i < p.length(); i++) {
            if(p.charAt(i) == '(') cnt++;
            else cnt--;
            if(cnt == 0) break;
        }
        return i + 1;
    }

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