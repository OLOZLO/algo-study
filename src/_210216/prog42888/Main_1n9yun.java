package _210216.prog42888;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main_1n9yun {
    class Item{
        String command, uid;

        Item(String command, String uid) {
            this.command = command;
            this.uid = uid;
        }
    }
    public String[] solution(String[] record) {
        Map<String, String> user = new HashMap<>();

        ArrayList<Item> answer = new ArrayList<>();
        for(String input : record){
            String[] split = input.split(" ");
//            enter, change일때 map에 <uid, username> 갱신/저장
            if(!split[0].equals("Leave")) user.put(split[1], split[2]);
//            enter, leave일 때 메세지 리스트에 추가 <command, uid>
            if(!split[0].equals("Change")) answer.add(new Item(split[0], split[1]));
        }

        return answer.stream()
//                각 메세지의 uid를 username으로 바꾸고 command 메세지 추가한 내용으로 매핑
                .map(m->user.get(m.uid) + (m.command.equals("Enter") ? "님이 들어왔습니다." : "님이 나갔습니다."))
//                String[]로 리턴
                .toArray(String[]::new);
    }
}