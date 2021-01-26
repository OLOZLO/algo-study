package _210126.prog17684;

import java.util.ArrayList;
import java.util.HashMap;

public class Main_1n9yun {
    public int[] solution(String msg) {
        ArrayList<Integer> answer = new ArrayList<>();
        HashMap<String, Integer> dict = new HashMap<>();
        int idx = 1;

//        기본 문자들 넣어놓기
        for(int i=0;i<26;i++) dict.put(Character.toString((char)('A' + i)), idx++);

        for(int i=0;i<msg.length();i++){
            StringBuilder w = new StringBuilder(Character.toString(msg.charAt(i)));
            while(++i < msg.length() && dict.containsKey(w.toString() + msg.charAt(i))){
                w.append(msg.charAt(i));
            }
            answer.add(dict.get(w.toString()));

            if(i < msg.length()) {
                dict.put(w.toString() + msg.charAt(i--), idx++);
            }
        }

        return answer.stream().mapToInt(i->i).toArray();
    }

}
