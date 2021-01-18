package _210119.prog17683;

public class Main_Taekyung2 {
    public String solution(String m, String[] musicinfos) {
        String answer = "(None)";
        int time = 0;
        m = change(m);
        for (String music : musicinfos) {
            String[] info = music.split(",");
            int start_minute = (60 * Integer.parseInt(info[0].substring(0, 2)) + Integer.parseInt(info[0].substring(3)));
            int end_minute = (60 * Integer.parseInt(info[1].substring(0, 2)) + Integer.parseInt(info[1].substring(3)));
            int minute = end_minute - start_minute;
            if (minute > time) {
                String s = change(info[3]);
                StringBuffer sb = new StringBuffer();
                int iter = minute / s.length();
                int nmg = minute % s.length();
                sb.append(s.repeat(iter));
                sb.append(s, 0, nmg);
                if (sb.toString().contains(m)) {
                    answer = info[2];
                    time = minute;
                }
            }
        }
        return answer;
    }

    public String change(String s) {
        return s.replace("C#", "1")
                .replace("D#", "2")
                .replace("F#", "3")
                .replace("G#", "4")
                .replace("A#", "5");
    }
}