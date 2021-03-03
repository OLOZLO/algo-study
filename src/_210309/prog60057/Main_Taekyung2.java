package _210309.prog60057;

public class Main_Taekyung2 {
    public int solution(String s) {
        int n = s.length(), ret = n;
        for(int i = 1; i <= n / 2; i++) {
            String prev = "", next;
            int cnt = 1, tmp = 0;
            for(int j = 0; j < n; j += i) {
                if(j + i >= n)  next = s.substring(j);
                else            next = s.substring(j, j + i);
                if(prev.equals(next)) cnt++;
                else {
                    tmp += next.length();
                    tmp += chk(cnt);
                    prev = next;
                    cnt = 1;
                }
            }
            tmp += chk(cnt);
            ret = Math.min(tmp, ret);
        }
        return ret;
    }

    int chk(int c) {
        if(c > 1 && c < 10) return 1;
        else if(c >= 10) return 2;
        return 0;
    }
}
