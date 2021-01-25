package _210126.prog17684;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main_ms {
    public static ArrayList<Integer> solution(String msg) {
        ArrayList<Integer> answer = new ArrayList<>();
        Map<String, Integer> hashMap = new HashMap<>();

        int count = 1;

        // A~Z 까지 알파벳들을 미리 사전에 넣어둡니다.
        for (int i = 65; i <= 90; i++) {
            hashMap.put(Character.toString((char) i), count++);
        }

        int start = 0;
        while (start < msg.length()) {
            int end = start;

            // end 를 한 칸씩 늘려가며, start 를 시작으로 map 에 포함되어 있는 가장 긴 문자열을 찾습니.
            while (end + 1 <= msg.length() && hashMap.containsKey(msg.substring(start, end + 1))) {
                end++;
            }

            answer.add(hashMap.get(msg.substring(start, end))); // 위에서 사전내 존재하는 가장 긴 문자열을 찾았으면, 해당 단어의 색인을 반환 리스트에 넣습니다.

            // 다음 글자가 있다면, 새로 만들어진 w+c 문자열을 다음 번 색인으로 사전에 추가해줍니다.
            if (end < msg.length()) {
                hashMap.put(msg.substring(start, end + 1), count++);
            }

            start = end;
        }

        return answer;
    }

    public static void main(String[] args) {
        solution("KAKAO");
//        solution("TOBEORNOTTOBEORTOBEORNOT");
//        solution("ABABABABABABABAB");
    }
}
