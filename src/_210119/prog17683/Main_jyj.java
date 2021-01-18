package _210119.prog17683;

import java.util.*;

public class Main_jyj {
    public String solution(String m, String[] musicinfos) {
        String answer = "";
        int maxTime = 0;
 
        // #이 붙어있는 음을 모두 치환해준다
        m = replaceMelode(m);

        for (String musicInfo : musicinfos) {
            // 음악 정보를 , 기준으로 배열에 넣어준다.
            String[] info = musicInfo.split(",");

            // 음악 재생 시간을 계산
            int hour = Integer.parseInt(info[1].split(":")[0]) - Integer.parseInt(info[0].split(":")[0]);
            int minute = Integer.parseInt(info[1].split(":")[1]) - Integer.parseInt(info[0].split(":")[1]);
            int runTime = hour * 60 + minute;

            // 방송된 음악의 음을 cords 변수에 저장            
            String cords = replaceMelode(info[3]);

            String realPlayStr = "";
            
            // 실제로 재생된 음표를 구한다.
            for (int i = 0; i < runTime; i++) {
                realPlayStr += cords.charAt(i % cords.length());
            }
            
            // 기억한 멜로디가 실제로 재생된 멜로디에 포함된다면
            if (realPlayStr.contains(m)) {
                // 가장 긴 길이의 멜로디를 찾는다.
                if (maxTime < realPlayStr.length()) {
                    maxTime = realPlayStr.length();
                    answer = info[2];
                }
            }
        }

        if (answer.equals("")) {
            return "(None)";
        }

        return answer;
    }
    
        
    private static String replaceMelode(String m) {
        return m.replace("C#", "c")
                .replace("D#", "d")
                .replace("E#", "e")
                .replace("F#", "f")
                .replace("G#", "g")
                .replace("A#", "a")
                .replace("B#", "a");
    }
}
