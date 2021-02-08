package _210209.prog17685;

import java.util.Arrays;

public class Main_Taekyung2 {
    public int solution(String[] words) {
        int answer = 0;
        Arrays.sort(words);
        for (int i = 0; i < words.length; i++) {
            int max, pre = 0, post = 0;
            String s;
            // 정렬시켜 놓은 거에서 현재꺼 앞이랑 뒤에꺼 비교해서 겹치는게 많은거 + 1이 타이핑 횟수
            for (int j = 0; j < words[i].length(); j++) {
                s = words[i].substring(0, j + 1);
                if (i > 0 && words[i - 1].length() > j) {
                    if (words[i - 1].startsWith(s)) {
                        pre = s.length();
                    }
                }
                if (i < words.length - 1 && words[i + 1].length() > j) {
                    if (words[i + 1].startsWith(s))
                        post = s.length();
                }
            }
            max = Math.max(pre, post);
            // 하나도 안겹치면 그냥 씀
            if (max < words[i].length())
                max = max + 1;
            answer += max;
        }
        return answer;
    }
}