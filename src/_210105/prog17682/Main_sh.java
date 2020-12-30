package _210105.prog17682;

import java.util.Arrays;

public class Main_sh {
    class Solution {
        public int solution(String dartResult) {
            String[] result = dartResult.split("(?<=\\D)(?=\\d)");
            int[] score = new int[result.length];

            for (int i = 0; i < result.length; i++) {
                char[] s = result[i].toCharArray();

                for (int j = 0; j < s.length; j++) {
                    if (Character.isDigit(s[j]))
                        score[i] = score[i] * 10 + s[j] - '0';

                    else if (Character.isAlphabetic(s[j])) {
                        if (s[j] == 'D')
                            score[i] *= score[i];
                        else if (s[j] == 'T')
                            score[i] *= score[i] * score[i];

                    } else {
                        if (s[j] == '*') {
                            if (i > 0)
                                score[i - 1] *= 2;
                            score[i] *= 2;
                        } else score[i] *= -1;
                    }
                }
            }
            return Arrays.stream(score).sum();
        }
    }
}