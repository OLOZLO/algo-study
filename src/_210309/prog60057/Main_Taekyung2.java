package _210309.prog60057;

public class Main_Taekyung2 {
    public int solution(String s) {
        int n = s.length(), ret = n;
        // 길이 1부터 n / 2까지 다 해본다
        for(int i = 1; i <= n / 2; i++) {
            String prev = "", next;
            int cnt = 1, tmp = 0;
            for(int j = 0; j < n; j += i) {
                if(j + i >= n)  next = s.substring(j);
                else            next = s.substring(j, j + i);
                // 이전 값과 같으면 카운트를 늘린다
                if(prev.equals(next)) cnt++;
                else { // 이전 값과 다르면, 카운트 해놨던 것 길이와 다음 문자열 길이를 더함
                    tmp += next.length();
                    tmp += chk(cnt);
                    prev = next;
                    cnt = 1;
                }
            }
            // 마지막에 남은 카운트 한거 더해준다
            tmp += chk(cnt);
            ret = Math.min(tmp, ret);
        }
        return ret;
    }

    // 앞에 개수 붙일 때 한 자리면 1, 두 자리면 2
    int chk(int c) {
        if(c > 1 && c < 10) return 1;
        else if(c >= 10) return 2;
        return 0;
    }
}
