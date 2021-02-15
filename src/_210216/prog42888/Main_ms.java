package _210216.prog42888;

import java.util.*;

public class Main_ms {
    public static List<String> solution(String[] record) {
        List<String> answer = new ArrayList<>();

        // Map - [유저 아이디]와 [닉네임]을 키-밸류 쌍으로 갖음. 유저의 닉네임 변경을 반영시키는 데에 사용함.
        // List - Enter, Leave 에 대한 로그를 순서대로 기록하는 데에 사용함.
        Map<String, String> map = new HashMap<>();
        List<Pair> list = new ArrayList<>();

        for (String s : record) {
            String[] splited = s.split(" ");

            // Enter 시에는 맵에 새로운 [유저 아이디]-[닉네임]을 추가하고, Enter 로그를 기록해 둠.
            if (splited[0].equals("Enter")) {
                map.put(splited[1], splited[2]);
                list.add(new Pair(splited[1], "Enter"));
            }
            // Leave 시에는 Leave 로그만 기록함.
            else if (splited[0].equals("Leave")) {
                list.add(new Pair(splited[1], "Leave"));
            }
            // Change 시에는 맵에만 새로 put 해서, 변경된 닉네임을 반영시켜 놓음.
            else if (splited[0].equals("Change")) {
                map.put(splited[1], splited[2]);
            }
        }

        // record 다 돌면서 위 작업 끝나면,
        // List 돌며, 저장된 로그 정보에다가 유저 아이디로 최종 닉네임을 반영(map.get)시켜 answer에 저장함.
        for (Pair pair : list) {
            answer.add(map.get(pair.uid) + "님이 " + (pair.status == "Enter" ? "들어왔습니다." : "나갔습니다."));
        }

        return answer;
    }

    private static class Pair {
        String uid;
        String status;

        public Pair(String uid, String status) {
            this.uid = uid;
            this.status = status;
        }
    }

    public static void main(String[] args) {
        solution(new String[]{"Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234", "Enter uid1234 Prodo", "Change uid4567 Ryan"});
    }
}
