package _210119.prog17683;

public class Main_Taekyung2 {
    public String solution(String m, String[] musicinfos) {
        String answer = "(None)";
        int time = 0;
        m = change(m);
        for (String music : musicinfos) {
            String[] info = music.split(",");
            // 재생 시작 시간과 끝 시간의 차이만큼 재생 시간 계산
            int start_minute = (60 * Integer.parseInt(info[0].substring(0, 2)) + Integer.parseInt(info[0].substring(3)));
            int end_minute = (60 * Integer.parseInt(info[1].substring(0, 2)) + Integer.parseInt(info[1].substring(3)));
            int minute = end_minute - start_minute;
            // 조건이 일치하는 곡이 여러 개일 때 가장 재생 시간이 긴 노래를 고른다
            if (minute > time) {
                String s = change(info[3]);
                StringBuffer sb = new StringBuffer();
                // 재생 시간만큼 음을 만듬
                int iter = minute / s.length();
                int nmg = minute % s.length();
                sb.append(s.repeat(iter));
                sb.append(s, 0, nmg);
                // 네오가 기억하고 있는 멜로디가 포함되어 있으면 답, 재생 시간 비교를 위해 time에 minute 대입
                if (sb.toString().contains(m)) {
                    answer = info[2];
                    time = minute;
                }
            }
        }
        return answer;
    }

    // 두글자 묶음인거 하나로 만들기
    public String change(String s) {
        return s.replace("C#", "1")
                .replace("D#", "2")
                .replace("F#", "3")
                .replace("G#", "4")
                .replace("A#", "5");
    }
}