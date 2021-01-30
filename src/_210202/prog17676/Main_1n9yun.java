package _210202.prog17676;

import java.util.ArrayList;
import java.util.Comparator;

public class Main_1n9yun {
    double stod(String s) {return Double.parseDouble(s);}
    public int solution(String[] lines) {
        ArrayList<Integer> startTimes = new ArrayList<>();
        ArrayList<Integer> endTimes = new ArrayList<>();

        for (String s : lines) {
            String[] split = s.split(" ");
            String[] timeSplit = split[1].split(":");

            int end = (int) ((stod(timeSplit[0]) * 3600 + stod(timeSplit[1]) * 60 + stod(timeSplit[2])) * 1000);
            int start = end - ((int) (stod(split[2].substring(0, split[2].length() - 1)) * 1000) - 1);

            startTimes.add(start);
            endTimes.add(end);
        }

        int answer = 0;
        int temp = 0;
        startTimes.sort(Comparator.comparingInt(o -> o));
        endTimes.sort(Comparator.comparingInt(o -> o));

        int endIdx = 0;
        for (int startTime : startTimes) {
            temp++;
            for (; endIdx < endTimes.size(); endIdx++) {
                if (startTime - 999 > endTimes.get(endIdx)) temp--;
                else break;
            }
            answer = Math.max(answer, temp);
        }
        return answer;
    }
}
