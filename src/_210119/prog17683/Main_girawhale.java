package _210119.prog17683;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Main_girawhale {
    Map<String, String> map = new HashMap<>();

    public String solution(String m, String[] musicinfos) {
        map.put("C#", "c");
        map.put("D#", "d");
        map.put("F#", "f");
        map.put("G#", "g");
        map.put("A#", "a");

        m = changeStr(m);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        int ansLen = 0;
        String ans = "(None)";
        for (String musicinfo : musicinfos) {
            String[] infos = musicinfo.split(",");
            int playTime = (int) Duration.between(LocalTime.parse(infos[0], dtf), LocalTime.parse(infos[1], dtf)).toMinutes();

            String sheet = changeStr(infos[3]);
            String play = new String(new char[playTime / sheet.length()]).replace("\0", sheet);
            play += infos[3].substring(0, playTime % sheet.length());
            play = changeStr(play);

            if (play.contains(m) && ansLen < playTime) {
                ansLen = playTime;
                ans = infos[2];
            }
        }

        return ans;
    }

    public String changeStr(String str) {
        for (String key : map.keySet())
            str = str.replace(key, map.get(key));
        return str;
    }
}
