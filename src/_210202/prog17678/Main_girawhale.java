package _210202.prog17678;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Main_girawhale {
    public String solution(int N, int T, int M, String[] timetable) {
        //String형태 시간을 모두 분형태로 바꿈
        int[] times = Arrays.stream(timetable).mapToInt(t ->
                Integer.parseInt(t.substring(0, 2)) * 60
                        + Integer.parseInt(t.substring(3, 5))).toArray();

        // 정렬 가능한 TreeMap으로 시간순 정렬하고, 나온 횟수 저장
        TreeMap<Integer, Integer> timeCnt = new TreeMap<>(); // <줄 선 시간, 해당 시간에 줄선 사람 수>
        for (int time : times)
            timeCnt.put(time, timeCnt.getOrDefault(time, 0) + 1);

        int time = 9 * 60; // 젤 처음은 9시 운행함

        for (int n = 0; n < N; n++) { // 셔틀 운행 가능횟수만큼 반복
            int busTime = n * T + 9 * 60; // 현재 셔틀 운행하는 시간
            int cnt = 0; // 지금 운행하는 버스에 탄사람!

            Map.Entry<Integer, Integer> entry = null; // 운행하는 버스에 마지막으로 탄 entry
            while (!timeCnt.isEmpty() && busTime >= timeCnt.firstKey()) { // 운행 시간보다 먼저 서야 버스에 탈 수 잇슴
                entry = timeCnt.pollFirstEntry();
                cnt += entry.getValue(); // 줄 선사람 일단 다 셈

                if (cnt >= M) { // 근데 M보다 크면 못태우니까
                    timeCnt.put(entry.getKey(), cnt - M); // 넘친만큼 다시 빼서 map에 넣어주고
                    break; //ㅌㅌ
                }
            }
            if (n == N - 1) { // 마지막 버스인가요?
                if (cnt >= M) time = entry.getKey() - 1; // 근데 사람도 많이 탓으면 마지막으로 탄사람보다 먼저 서야되여...
            } else time += T; // 아니면 젤 늦게 서두댐~
        }

        return String.format("%02d:%02d", time / 60, time % 60);
    }
}
