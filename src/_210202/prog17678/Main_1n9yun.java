package _210202.prog17678;

import java.util.Arrays;

public class Main_1n9yun {
    public String solution(int n, int t, int m, String[] timetable) {
        int[] timeline = new int[timetable.length];
        for(int i=0;i<timeline.length;i++) timeline[i] = toMinutes(timetable[i]);
        Arrays.sort(timeline);

        int base = toMinutes("09:00");
        int lineIdx = 0;
        int lastTime = 0;
        for(int i=0;i<n;i++){
            int departure = base + i*t;
            int limit = m;

            while(lineIdx < timeline.length && timeline[lineIdx] <= departure && limit > 0){
                limit--;
                if(limit == 0) lastTime = timeline[lineIdx] - 1;
                lineIdx++;
            }
            if(limit > 0) lastTime = departure;
        }

        return String.format("%02d:%02d", lastTime / 60, lastTime % 60);
    }
    int toMinutes(String s){
        String[] split = s.split(":");
        return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
    }
}