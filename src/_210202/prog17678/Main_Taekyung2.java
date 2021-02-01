package _210202.prog17678;

public class Main_Taekyung2 {
    public String solution(int n, int t, int m, String[] timetable) {
        int cur = 0, answer = 0;
        // 00:00 부터 23:59까지
        int[] cnt = new int[24 * 60];
        for (String s : timetable) {
            char[] ch = s.toCharArray();
            int minute = ((ch[0] - '0') * 10 + (ch[1] - '0')) * 60 + (ch[3] - '0') * 10 + (ch[4] - '0');
            cnt[minute]++; // 기다리는 사람들 시간에 기록
        }
        for(int i = 0; i < n; i++) {
            int remain = m; // 남은 자리
            while(cnt[cur] < remain && cur <= (t * i) + 540) // 남은 자리가 기다리는 사람 보다 많고, 현재 시간이 버스 시간이 안됬으면
                remain -= cnt[cur++]; // 기다리는 사람들 탑승
            if(cur <= (t * i) + 540)
                cnt[cur] -= remain; // 기다리는 사람이 더 많으면, 일단 남는 자리만큼 태움
            answer = cur - 1; // 1분은 먼저 와야지
        }
        return String.format("%02d:%02d", answer / 60, answer % 60);
    }
}