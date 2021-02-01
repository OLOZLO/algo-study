package _210202.prog17676;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class Main_ms {
    public static int solution(String[] lines) {
        final int NANO = 1000000000;
        int answer = 0;
        String[] S = new String[lines.length];
        String[] T = new String[lines.length];

        for (int i = 0; i < lines.length; i++) {
            int len = lines[i].length();
            lines[i] = lines[i].substring(0, len - 1); // 맨 뒤에 문자 s 제거

            String[] tmp = lines[i].split(" ");
            S[i] = tmp[0] + " " + tmp[1]; // 응답 완료 시간 : yyyy-MM-dd HH:mm:ss.SSS 형식
            T[i] = tmp[2];                // 처리 시간
        }

        ArrayList<Time> list = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        for (int i = 0; i < lines.length; i++) {
            LocalDateTime start = LocalDateTime.parse(S[i], dtf).minusNanos((long) (Double.parseDouble(T[i]) * NANO)).plusNanos((long) (0.001 * NANO)); // 처리 시작 시간을 구함.
            list.add(new Time(start, LocalDateTime.parse(S[i], dtf))); // Time 객체는 시작 시간, 종료 시간으로 구성됨.
        }

        Collections.sort(list); // 시작시간(시작시간 같다면 종료시간으로) 기준으로 오름차순 정렬

        for (int i = 0; i < list.size(); i++) {
            // i 번째 로그를 기준으로 잡음.
            LocalDateTime start = list.get(i).start; // i 번 로그의 시작시간
            LocalDateTime end = list.get(i).end;     // i 번 로그의 종료시간
            LocalDateTime startPlusOne = start.plusSeconds(1).minusNanos((long) (0.001 * NANO)); // i 번 로그의 시작시간으로부터 1초 지난 시간
            LocalDateTime endPlusOne = end.plusSeconds(1).minusNanos((long) (0.001 * NANO));     // i 번 로그의 종료시간으로부터 1초 지난 시간

            int cntStart = 0, cntEnd = 0;
            for (int j = 0; j < list.size(); j++) { // 모든 로그들을 다 돔.
                // j 번째 로그를 타겟으로 잡음.
                LocalDateTime targetStart = list.get(j).start; // 타겟의 시작시간
                LocalDateTime targetEnd = list.get(j).end;     // 타겟의 종료시간

                // https://ddb8036631.github.io/알고리즘%20풀이/프로그래머스_카카오2018_추석-트래픽/
                // 위 링크 그림으로 설명 대체
                // 저 1초 구간 안에 들어있으면 카운트 세는 거임.
                if ((targetStart.isBefore(startPlusOne) || targetStart.equals(startPlusOne)) && (targetEnd.isAfter(start) || targetEnd.equals(start)))
                    cntStart++;

                if ((targetStart.isBefore(endPlusOne) || targetStart.equals(endPlusOne)) && (targetEnd.isAfter(end) || targetEnd.equals(end)))
                    cntEnd++;

                // 시작시간으로부터 1초 구간 내에 처리량이 더 많은지,
                // 종료시간으로부터 1초 구간 내에 처리량이 더 많은지 비교 후 갱신.
                answer = Math.max(answer, cntStart);
                answer = Math.max(answer, cntEnd);
            }
        }

        return answer;
    }

    public static class Time implements Comparable<Time> {
        LocalDateTime start;
        LocalDateTime end;

        public Time(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Time o) {
            return this.start.equals(o.start) ? this.end.compareTo(o.end) : this.start.compareTo(o.start);
        }
    }

    public static void main(String[] args) {
        solution(new String[]{"2016-09-15 00:00:00.000 3s"});
//        solution(new String[]{"2016-09-15 01:00:04.001 2.0s", "2016-09-15 01:00:07.000 2s"});
//        solution(new String[]{"2016-09-15 01:00:04.002 2.0s", "2016-09-15 01:00:07.000 2s"});
//        solution(new String[]{"2016-09-15 20:59:57.421 0.351s", "2016-09-15 20:59:58.233 1.181s", "2016-09-15 20:59:58.299 0.8s", "2016-09-15 20:59:58.688 1.041s", "2016-09-15 20:59:59.591 1.412s",
//                "2016-09-15 21:00:00.464 1.466s", "2016-09-15 21:00:00.741 1.581s", "2016-09-15 21:00:00.748 2.31s", "2016-09-15 21:00:00.966 0.381s", "2016-09-15 21:00:02.066 2.62s"});
    }
}
