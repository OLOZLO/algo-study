package _210105.prog17682;

public class Main_Taekyung2 {
    public static int solution(String dartResult) {
        char next;
        String numTemp = "";
        int score = 0;
        int answer = 0;
        int num = 0;

        for(int i = 0; i < dartResult.length(); i++) {
            next = dartResult.charAt(i);
            if(next=='S' || next=='D' || next=='T') {
                score = num;
                num = Integer.parseInt(numTemp);
                if(next=='D') {
                    num = num * num;
                } else if(next == 'T') {
                    num = num * num * num;
                }
                answer = answer + num;
                numTemp = "";
            } else if(next == '*') {
                answer = answer + num + score;
                num = 2 * (num);
            } else if(next == '#') {
                num = -num;
                answer += 2 * num;
            } else {
                numTemp = numTemp + next;
            }

        }
        return answer;
    }
}