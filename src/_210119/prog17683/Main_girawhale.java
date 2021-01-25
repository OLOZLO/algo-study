package _210119.prog17683;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main_girawhale {
    public String solution(String m, String[] musicinfos) {
        m = changeStr(m);

        //입력받은 시간 HH:mm을 변환하기 위한 DateTimeFormatter
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        //저장된 음악의 재생시간과 제목 초기화
        int ansLen = 0;
        String ans = "(None)";

        // for문을 통해 악보를 하나씩 살펴봄
        for (String musicinfo : musicinfos) {
            String[] infos = musicinfo.split(",");
            // DateTimeFormatter와 LocalTime을 이용해 입력 받은 시간의 차를 반환(Duration)
            // Duration의 값을 분으로 받아 재생시간으로 저장
            int playTime = (int) Duration.between(LocalTime.parse(infos[0], dtf), LocalTime.parse(infos[1], dtf)).toMinutes();

            String sheet = changeStr(infos[3]); //악보

            // str을 n번 반복하는 문자열 생성 : new String(new char[n]).replace("\0", str)
            // playTime이 sheet의 길이보다 길다면 그만큼 반복재생되었기때문에 재생된 악보를 기존악보를 반복해 붙여줌
            String play = new String(new char[playTime / sheet.length()]).replace("\0", sheet);
            // 만약 노래가 중간에 끊겼다면 나머지 연산을 통해 끊긴만큼 악보를 잘라 append
            play += sheet.substring(0, playTime % sheet.length());

            // m이 재생된 악보에 포함되고, 기존에 재생된 길이보다 길다면 값을 갱신
            if (play.contains(m) && ansLen < playTime) {
                ansLen = playTime;
                ans = infos[2];
            }
        }

        return ans;
    }

    // #이 붙는 음을 소문자로 변경해 반환
    public String changeStr(String str) {
        return str.replace("A#", "a")
                .replace("C#", "c")
                .replace("D#", "d")
                .replace("F#", "f")
                .replace("G#", "g");
    }
}
