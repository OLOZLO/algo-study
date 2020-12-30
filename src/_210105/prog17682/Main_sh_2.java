package _210105.prog17682;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main_sh_2 {
    class Solution {
        public int solution(String dartResult) {
            Pattern pattern = Pattern.compile("([0-9]+)([SDT])([*#]?)");
            Matcher matcher = pattern.matcher(dartResult);

            Deque<Integer> score = new ArrayDeque<>();
            while (matcher.find()) {
                char[] s = matcher.group().toCharArray();
                int tmp = 0;

                for (int j = 0; j < s.length; j++) {
                    if (Character.isDigit(s[j]))
                        tmp = tmp * 10 + s[j] - '0';

                    else if (Character.isAlphabetic(s[j])) {
                        if (s[j] == 'D')
                            tmp *= tmp;
                        else if (s[j] == 'T')
                            tmp *= tmp * tmp;

                    } else {
                        if (s[j] == '*') {
                            if (!score.isEmpty())
                                score.push(score.pop() * 2);
                            tmp *= 2;
                        } else tmp *= -1;
                    }
                }
                score.push(tmp);
            }

            int ans = 0;
            while (!score.isEmpty())
                ans += score.pop();

            return ans;
        }
    }
}