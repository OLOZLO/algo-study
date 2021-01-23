package _210126.prog17684;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main_girawhale {
    class Solution {
        public int[] solution(String msg) {
            List<Integer> ans = new ArrayList<>();
            Map<String, Integer> map = new HashMap<>();

            // 모든 알파벳을 미리 Map에 넣어둠
            for (int i = 0; i < 26; i++) map.put((char) ('A' + i) + "", i + 1);

            char[] arr = msg.toCharArray();
            int idx = 1;
            StringBuilder key = null; // Map에서 찾아볼 String

            while (idx <= msg.length()) {
                //처음에 --idx를 통해 앞서 put했던 문자열의 마지막 인덱스부터 시작
                key = new StringBuilder(arr[--idx] + "");

                // 이미 Map에 존재하면 key에 덧붙이고 없다면 새로 사전에 등록해야 하기때문에 탈출
                while (++idx < msg.length() && map.containsKey(key.toString()))
                    key.append(arr[idx]);

                // 끝도 났는데 문자열도 이미 존재하면 탈출
                if (idx == msg.length() && map.containsKey(key.toString())) break;

                ans.add(map.get(key.substring(0, key.length() - 1))); // 마지막에 없던거 나왔으니까 뒤에거 자르고 답에 추가
                map.put(key.toString(), map.size() + 1); // 없던거 사전에 추가
            }
            ans.add(map.get(key.toString())); // 반복문 나오면서 들고 있던 키도 list에 추가

            return ans.stream().mapToInt(i -> i).toArray(); // List<Integer> to int array
        }
    }
}
