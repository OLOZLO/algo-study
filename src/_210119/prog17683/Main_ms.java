package _210119.prog17683;

// https://ddb8036631.github.io/알고리즘%20풀이/프로그래머스_카카오2018_방금그곡/
public class Main_ms {
    public static String solution(String m, String[] musicinfos) {
        m = convert(m); // 먼저 주어진 m에서 샵을 모두 치환한다.

        String answer = "(None)"; // 조건에 일치하는 음악을 못찾을 것을 미리 방지, 초기 값을 (None)으로 세팅해줬다.
        int max = 0; // 조건에 맞는 음악이 여러 개 있을 시, 재생 시간이 가장 긴 음악을 판단하기 위한 변수 선언.

        for (int i = 0; i < musicinfos.length; i++) {
            String s = musicinfos[i];

            String[] info = s.split(",");

            String[] start = info[0].split(":");
            String[] end = info[1].split(":");
            String name = info[2];
            String akbo = info[3];

            // 음악 재생 시간(playTime)은 종료 시각에서 시작 시각을 빼서 구한다.
            // playTime은 둘의 hour가 같다면 minute의 차이가 될 것이고, 다르면 hour 차이를 분으로 변환해 더해준다.
            int playTime = Integer.parseInt(end[1]) - Integer.parseInt(start[1]);
            if (!start[0].equals(end[0])) {
                int hour_diff = Integer.parseInt(end[0]) - Integer.parseInt(start[0]);
                playTime += 60 * hour_diff;
            }

            String melody = makeMelody(akbo, playTime); // 악보와 재생시간으로 멜로디를 구해온다.
            melody = convert(melody);                   // 구한 멜로디에서 샵을 모두 치환한다.

            // 이번 곡도 멜로디 m을 포함하고 있으면?
            if (melody.contains(m)) {
                // 이번 곡의 재생 시간과 이전에 매칭된 곡의 재생 시간을 비교.
                // 이번 곡 재생 시간이 더 길다면? 답을 바꿔주고, max(가장 긴 재생 시간) 값을 업데이트 해줌.
                if (max < playTime) {
                    max = playTime;
                    answer = name;
                }
            }
        }

        return answer;
    }

    // 샵이 붙은 다섯 개의 음 A#, C#, D#, F#, G# 을 각각 a, c, d, f, g 로 사전에 치환한다.
    public static String convert(String melody) {
        melody = melody.replaceAll("A#", "a");
        melody = melody.replaceAll("C#", "c");
        melody = melody.replaceAll("D#", "d");
        melody = melody.replaceAll("F#", "f");
        melody = melody.replaceAll("G#", "g");

        return melody;
    }

    public static String makeMelody(String akbo, int playTime) {
        String melody = "";
        int cnt = 0;
        int idx = 0;

        // playTime(분)동안 악보의 각 음을 1분에 1개씩 붙인다.
        while (cnt < playTime) {

            // 샵이 붙은 음이라면? 두 문자를 한 음으로 쳐서 붙인다.
            // 다음 음은 2칸을 건더 뛴 (idx + 2) 번째 음이 될 것임.
            if (idx + 1 < akbo.length() && akbo.charAt(idx + 1) == '#') {
                melody += akbo.substring(idx, idx + 2);
                idx += 2;
            }

            // 샵이 붙지 않은 음이라면? 한 문자만 붙인다.
            // 다음 음은 1칸만 건너 뛴 (idx + 1) 번째 음이 될 것임.
            else {
                melody += akbo.charAt(idx);
                idx += 1;
            }

            idx = idx % akbo.length();  // 재생 시간동안 악보를 계속 돌아야 하니까 % 연산으로 인덱스를 맞춰준다.
            cnt++;                      // 1분씩 경과함을 의미.
        }

        return melody;
    }

    public static void main(String[] args) {
        String m = "ABCDEFG";
        String[] musicinfos = {"12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF"};

//        String m = "CC#BCC#BCC#BCC#B";
//        String[] musicinfos = {"03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"};

//        String m = "ABC";
//        String[] musicinfos = {"12:00,12:14,HELLO,C#DEFGAB", "13:00,13:05,WORLD,ABCDEF"};

//        String m = "CCB";
//        String[] musicinfos = {"03:00,03:10,FOO,CCB#CCB", "04:00,04:08,BAR,ABC"};

        solution(m, musicinfos);
    }
}
