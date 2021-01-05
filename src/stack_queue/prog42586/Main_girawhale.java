package stack_queue.prog42586;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main_girawhale {
    public int[] solution(int[] progresses, int[] speeds) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < progresses.length; i++)
            queue.add((int) (Math.ceil((100.0 - progresses[i]) / speeds[i])));

        List<Integer> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            int day = queue.poll();
            int cnt = 1;

            while (!queue.isEmpty() && day >= queue.peek()) {
                cnt++;
                queue.poll();
            }
            ans.add(cnt);
        }

        return ans.stream().mapToInt(Integer::intValue).toArray();
    }
}
