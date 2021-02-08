package _210202.prog17676;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class Main_girawhale {
    public int solution(String[] lines) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        int[][] timetable = new int[lines.length][2]; //시작시간, 끝시간으로 저장

        for (int i = 0; i < lines.length; i++) {
            String[] split = lines[i].split(" ");

            int end = (int) (LocalTime.parse(split[1], dtf).toNanoOfDay() / 1_000_000); // 시간을 모두 초로 변경.
                                                                                        // 1초 = 10^9나노초, 0이 6개 필요없으니까 없애버림
            int T = (int) (Double.parseDouble(split[2].substring(0, split[2].length() - 1)) * 1000); // 0.001초를 1로 변환했으니까 1000을 곱해줌
            int start = end - T + 1; //종료시간이 주어지니까 시작시간을 소요시간에서 빼서 구해주는데
            if (start < 0) start = 0; //전날부터 시작했을수도 있으니까 0보다 작으면 0으로 바꿔준다

            timetable[i] = new int[]{start, end + 1000}; // 1초간 처리량 구한다니까 걍 미리 늘려서 계산 쉽게 하자...
        }

        int max = 0;
//        int[] cnt = new int[24 * 60 * 60 * 1000]; // 190~327ms, 390MB
        Map<Integer, Integer> map = new HashMap<>(); // 8~1200ms, 53~480MB
        for (int i = 0; i < lines.length; i++) { // 걍 로그 다 돌기
            // 로그 돌 때마다 내가 있는 있는 위치에 +1, 그러면 해당 초 때 돌고있는 트래픽수가 저장됨
            // 종료횟수에 +1000을 했으니까 다음날로 넘어갈 수 있어 하루 최대 초로 Min해줌
            for (int t = timetable[i][0]; t < Math.min(timetable[i][1], 24 * 60 * 60 * 1000); t++) {
//                max = Math.max(++cnt[t], max);

                // put해주면 기존에 있던 값 나오는데 없을때 put해주면 null나오니까 그거 막을라고 이랬어여...
                max = Math.max(max, Optional.ofNullable(map.put(t, map.getOrDefault(t, 0) + 1)).orElse(0));
            }
        }

        return max;
    }
}