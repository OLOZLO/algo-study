package _210202.prog17676;

import java.util.ArrayList;

public class Main_Taekyung2 {
    // t : ms로 바꾼 시간, se : 0이면 start, 1이면 end
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
            logs.add(new Log(ms - w + 1, 0)); // 시작 시간이랑, 0 넣음
            logs.add(new Log(ms + 999, 1)); // 끝 시간이랑, 1 넣음, 끝 시간에서 1초까지 비교해야 되서 999ms 더했음
        }
        int cnt = 0, answer = 0;
        // 시간 기준으로 오름차순 정렬, 시작 시간, 끝 시간도 포함시켜야 되서 시간 같으면 시작인 로그부터
        logs.sort((o1, o2) -> {
            if (o1.t == o2.t) return o1.se - o2.se;
            return o1.t - o2.t;
        });
        for(Log l : logs) {
            // 정렬시켜 놓은거 돌면서 시작 로그면 +1, 끝나는 로그면 -1, 가장 최대인게 답
            if(l.se == 0) cnt++;
            else if(l.se == 1) cnt--;
            answer = Math.max(cnt, answer);
        }
        return answer;
    }
}