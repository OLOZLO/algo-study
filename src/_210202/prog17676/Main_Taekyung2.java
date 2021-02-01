package _210202.prog17676;

import java.util.ArrayList;

public class Main_Taekyung2 {
    public static class Log {
        int t, se;
        Log(int t, int se) {
            this.t = t;
            this.se = se;
        }
    }
    public int solution(String[] lines) {
        ArrayList<Log> logs = new ArrayList<>();
        for(String log : lines) {
            String[] time = log.split(" ");
            String[] arr = time[1].split(":");
            int ms = Integer.parseInt(arr[0]) * 3600 * 1000
                        + Integer.parseInt(arr[1]) * 60 * 1000
                        + (int)(Double.parseDouble(arr[2]) * 1000);
            int w = (int)(Double.parseDouble(time[2].substring(0, time[2].length() - 1)) * 1000);
            logs.add(new Log(ms - w + 1, 0));
            logs.add(new Log(ms + 999, 1));
        }
        int cnt = 0, answer = 0;
        logs.sort((o1, o2) -> {
            if (o1.t == o2.t) return o1.se - o2.se;
            return o1.t - o2.t;
        });
        for(Log l : logs) {
            if(l.se == 0) cnt++;
            else if(l.se == 1) cnt--;
            answer = Math.max(cnt, answer);
        }
        return answer;
    }
}