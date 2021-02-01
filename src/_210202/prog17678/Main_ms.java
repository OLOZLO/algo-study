package _210202.prog17678;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main_ms {
    public static String solution(int n, int t, int m, String[] timetable) {
        String answer = "";

        // timetable 을 빠른시간 순으로 정렬.
        Arrays.sort(timetable, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] o1_splited = o1.split(":");
                String[] o2_splited = o2.split(":");

                if (o1_splited[0].equals(o2_splited[0]))
                    return o1_splited[1].compareTo(o2_splited[1]);
                return o1_splited[0].compareTo(o2_splited[0]);
            }
        });

        Map<Integer, String> map = new HashMap<>();
        String now = "09:00"; // 셔틀의 첫 운행 시간은 09:00!

        for (int i = 0; i < n; i++) {
            map.put(i, now);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime next = LocalTime.parse(now, dtf).plusMinutes(t); // t분 간격의 운행 시간을 구함.

            now = next.toString();
        }

        List<String>[] schedule = new ArrayList[map.size()];
        for (int i = 0; i < schedule.length; i++)
            schedule[i] = new ArrayList<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        int idx_schedule = 0;
        int idx_timetable = 0;

        // 리스트를 돌며, 셔틀의 각 운행 시간에 탑승할 수 있는 크루들의 도착 시간을 기록한다.
        while (idx_schedule < schedule.length && idx_timetable < timetable.length) {
            LocalTime lt_schedule = LocalTime.parse(map.get(idx_schedule), dtf);
            LocalTime lt_timetable = LocalTime.parse(timetable[idx_timetable], dtf);

            if (schedule[idx_schedule].size() + 1 <= m && (lt_timetable.isBefore(lt_schedule) || lt_timetable.equals(lt_schedule))) {
                schedule[idx_schedule].add(timetable[idx_timetable]);
                idx_timetable++;
            } else
                idx_schedule++;
        }

        int last = schedule.length - 1; // 제일 마지막에 운행하는 셔틀을 기준으로 콘의 도착 시간을 정할 수 있다.

        // 마지막으로 운행하는 셔틀의 자리가 비어 있다면, 콘은 해당 셔틀에 탑승할 수 있으므로, 셔틀 운행 시간에 딱 맞춰 도착해도 된다.
        if (schedule[last].size() + 1 <= m) {
            answer = map.get(last);
        }

        // 그렇지 않다면, 해당 셔틀을 탑승하는 크루들의 도착 시간보다 1분 먼저 도착했을 때 탈 수 있나를 확인하면서, 콘의 도착 시간을 늘려 나간다.
        else {
            for (int j = 0; j < schedule[last].size(); j++) {
                LocalTime tmp = LocalTime.parse(schedule[last].get(j), dtf).minusMinutes(1);

                if (j + 1 <= m) {
                    answer = tmp.toString();
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        solution(1, 1, 5, new String[]{"08:00", "08:01", "08:02", "08:03"});
//        solution(2, 10, 2, new String[]{"09:10", "09:09", "08:00"});
//        solution(2, 1, 2, new String[]{"09:00", "09:00", "09:00", "09:00"});
//        solution(1, 1, 5, new String[]{"00:01", "00:01", "00:01", "00:01", "00:01"});
//        solution(1, 1, 1, new String[]{"23:59"});
//        solution(10, 60, 45, new String[]{"23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59"});
    }
}
