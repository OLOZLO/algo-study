package _210202.prog17678;

import java.util.Arrays;

public class Main_1n9yun {
    public String solution(int n, int t, int m, String[] timetable) {
        int[] timeline = new int[timetable.length];
//        시간들 전부 분 단위로 바꿔
        for(int i=0;i<timeline.length;i++) timeline[i] = toMinutes(timetable[i]);
        Arrays.sort(timeline);

        int base = toMinutes("09:00");
        int lineIdx = 0;
        int lastTime = 0;
        for(int i=0;i<n;i++){
//            현재 타임에 출발시간
            int departure = base + i*t;
            int limit = m;
//            현재 탈 수 있는 만큼 시간 이동
//            마지막으로 탄 사람의 시간 저장
            while(lineIdx < timeline.length && timeline[lineIdx] <= departure && limit > 0){
                limit--;
                if(limit == 0) lastTime = timeline[lineIdx] - 1;
                lineIdx++;
            }
//            자리가 남았으면 출발시간에 맞춰서 가도 탈수있음
            if(limit > 0) lastTime = departure;
        }

        return String.format("%02d:%02d", lastTime / 60, lastTime % 60);
    }
    int toMinutes(String s){
        String[] split = s.split(":");
        return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
    }
}