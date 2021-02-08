package _210202.prog17678;

import java.util.Arrays;

public class Main_girawhale {
    public String solution(int N, int T, int M, String[] timetable) {
        int[] times = Arrays.stream(timetable).mapToInt(t ->
                Integer.parseInt(t.substring(0, 2)) * 60
                        + Integer.parseInt(t.substring(3, 5))).sorted().toArray();

        int idx = 0;
        int time = 9 * 60; // 젤 처음은 9시 운행함
        for (int n = 0; n < N; n++) { // 셔틀 운행 가능횟수만큼 반복
            int busTime = n * T + 9 * 60; // 현재 셔틀 운행하는 시간
            int cnt = 0; // 지금 운행하는 버스에 탄사람!

            while (idx < times.length && times[idx] <= busTime && cnt < M) { // 지금 셔틀버스에 탈 수 있고 버스 정원 이내면 태움
                idx++;
                cnt++;
            }

            if (n == N - 1) { // 마지막 버스인가요?
                if (cnt >= M) time = times[idx - 1] - 1; // 근데 사람도 많이 탓으면 마지막으로 탄사람보다 먼저 서야되여...
            } else time += T; // 아니면 젤 늦게 서두댐~
        }

        return String.format("%02d:%02d", time / 60, time % 60);
    }
}
