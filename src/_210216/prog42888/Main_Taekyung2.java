package _210216.prog42888;

import java.util.*;

public class Main_Taekyung2 {
    // id랑 들어왔는지 나갔는지
    static class Print {
        String uid;
        int io;
        Print(String uid, int io) {
            this.uid = uid;
            this.io = io;
        }
    }
    public String[] solution(String[] record) {
        HashMap<String, String> user = new HashMap<>();
        Queue<Print> q = new LinkedList<>();
        for(String s : record) {
            String[] log = s.split(" ");
            // enter랑 change면 맵에 닉네임 갱신
            // enter랑 leave면 큐에 들어갔는지 나갔는지 기록
            switch(log[0]) {
                case "Enter" -> {
                    user.put(log[1], log[2]);
                    q.add(new Print(log[1], 0));
                }
                case "Leave" -> q.add(new Print(log[1], 1));
                case "Change" -> user.put(log[1], log[2]);
            }
        }
        String[] answer = new String[q.size()];
        int i = 0;
        while(!q.isEmpty()) {
            Print cur = q.poll();
            answer[i++] = user.get(cur.uid) + (cur.io == 0 ? "님이 들어왔습니다." : "님이 나갔습니다.");
        }
        return answer;
    }
}
