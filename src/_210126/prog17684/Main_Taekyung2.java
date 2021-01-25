package _210126.prog17684;

import java.util.ArrayList;
import java.util.HashMap;

public class Main_Taekyung2 {
    public static int[] solution(String msg) {
        HashMap<String,Integer> map = new HashMap<>();
        // 사전 초기화
        for(int i = 1; i <= 26; i++){
            map.put(String.valueOf((char)('A' + i - 1)), i);
        }

        ArrayList<Integer> list = new ArrayList<>();

        for(int i = 0; i < msg.length(); ){
            int j;
            for(j = i; j < msg.length(); j++){
                String str = msg.substring(i, j + 1);
                if(!map.containsKey(str)){
                    break;
                }
            }
            // 일치한 만큼 사전에 넣음
            String str = msg.substring(i, j);
            list.add(map.get(str));
            if(j <= msg.length() - 1){
                String next = str + msg.charAt(j);
                map.put(next, map.size() + 1);
            }
            i += j - i;
        }

        int[] result = new int[list.size()];
        for(int i = 0; i < list.size(); i++){
            result[i] = list.get(i);
        }
        return result;
    }
}
