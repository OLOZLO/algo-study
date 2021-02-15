package _210216.prog42888;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main_hz {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new String[]{"Enter uid1234 Muzi", "Enter uid4567 Prodo","Leave uid1234","Enter uid1234 Prodo","Change uid4567 Ryan"})));
    }

    public static String[] solution(String[] record) {
        String[] answer;

        HashMap<String, String> uid = new HashMap<>();  // 유저의 uid와 닉네임을 저장하는 Map
        List<String> name = new ArrayList<>();  // 순서대로 입장/퇴장한 유저의 uid 저장 리스트
        List<String> info = new ArrayList<>();  // 순서대로 입장/퇴장 메세지 저장 리스트

        for (int i = 0; i < record.length; i++) {
            String[] s = record[i].split(" ");

            if (s[0].equals("Enter")) {
                uid.put(s[1], s[2]);
                name.add(s[1]);
                info.add("님이 들어왔습니다.");
            } else if (s[0].equals("Leave")) {
                name.add(s[1]);
                info.add("님이 나갔습니다.");
            } else if (s[0].equals("Change")) {
                uid.put(s[1], s[2]);
            }
        }

        answer = new String[name.size()];
        for (int i = 0; i < name.size(); i++) { // record 길이만큼 name에 해당되는 유저 닉네임과 메세지 출력
            answer[i] = uid.get(name.get(i))+info.get(i);
        }

        return answer;
    }
}
