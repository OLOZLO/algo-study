package _210202.prog17678;

public class Main_Taekyung2 {
    public String solution(int n, int t, int m, String[] timetable) {
        int cur = 0, answer = 0;
        int[] cnt = new int[24 * 60];
        for (String s : timetable) {
            char[] ch = s.toCharArray();
            int minute = (ch[3] - '0') * 10 + (ch[4] - '0') + ((ch[0] - '0') * 10 + (ch[1] - '0')) * 60;
            cnt[minute]++;
        }
        for(int i = 0; i < n; i++) {
            int remain = m;
            while(cnt[cur] < remain && cur <= (t * i) + 540)
                remain -= cnt[cur++];
            if(cur <= (t * i) + 540)
                cnt[cur] -= remain;
            answer = cur - 1;
        }
        return String.format("%02d:%02d", answer / 60, answer % 60);
    }
}