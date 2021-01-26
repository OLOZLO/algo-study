package _210202.prog17678;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Main_girawhale {
    public String solution(int N, int T, int M, String[] timetable) {
        int[] times = Arrays.stream(timetable).mapToInt(t ->
                Integer.parseInt(t.substring(0, 2)) * 60
                        + Integer.parseInt(t.substring(3, 5))).toArray();

        TreeMap<Integer, Integer> timeCnt = new TreeMap<>();
        for (int time : times)
            timeCnt.put(time, timeCnt.getOrDefault(time, 0) + 1);

        int time = 9 * 60;

        for (int n = 0; n < N; n++) {
            int busTime = n * T + 9 * 60;
            int cnt = 0;

            Map.Entry<Integer, Integer> entry = null;
            while (!timeCnt.isEmpty() && busTime >= timeCnt.firstKey()) {
                entry = timeCnt.pollFirstEntry();
                cnt += entry.getValue();

                if (cnt >= M) {
                    timeCnt.put(entry.getKey(), cnt - M);
                    break;
                }
            }
            if (n == N - 1) {
                if (cnt >= M) time = entry.getKey() - 1;
            } else time += T;
        }

        return String.format("%02d:%02d", time / 60, time % 60);
    }
}
