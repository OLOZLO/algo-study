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
                int tmp = Integer.parseInt(matcher.group(1));

                switch (matcher.group(2)) {
                    case "D":
                        tmp *= tmp;
                        break;
                    case "T":
                        tmp *= tmp * tmp;
                }

                switch (matcher.group(3)) {
                    case "*":
                        if (!score.isEmpty())
                            score.push(score.pop() * 2);
                        tmp *= 2;
                        break;
                    case "#":
                        tmp *= -1;
                        break;
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