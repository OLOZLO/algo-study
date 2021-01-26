package _210202.prog17676;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class Main_girawhale {
    public int solution(String[] lines) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        int[][] timetable = new int[lines.length][2];

        for (int i = 0; i < lines.length; i++) {
            String[] split = lines[i].split(" ");

            int end = (int) (LocalTime.parse(split[1], dtf).toNanoOfDay() / 1_000_000);
            int T = (int) (Double.parseDouble(split[2].substring(0, split[2].length() - 1)) * 1000);
            int start = end - T + 1;
            if (start < 0) start = 0;

            timetable[i] = new int[]{start, end + 1000};
        }

        int max = 0;
//        int[] cnt = new int[24 * 60 * 60 * 1000]; // 190~327ms, 390MB
        Map<Integer, Integer> map = new HashMap<>(); // 8~1200ms, 53~480MB
        for (int i = 0; i < lines.length; i++) {
            for (int t = timetable[i][0]; t < Math.min(timetable[i][1], 24 * 60 * 60 * 1000); t++) {
//                max = Math.max(++cnt[t], max);
                max = Math.max(max, Optional.ofNullable(map.put(t, map.getOrDefault(t, 0) + 1)).orElse(0));
            }

        }

        return max;
    }
}