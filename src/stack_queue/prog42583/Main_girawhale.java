package stack_queue.prog42583;

import java.util.LinkedList;
import java.util.Queue;

public class Main_girawhale {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<int[]> queue = new LinkedList<>();
        int time = 0, idx = 0;

        while (idx < truck_weights.length) {
            if (!queue.isEmpty() && time == queue.peek()[1]) {
                int[] truck = queue.poll();
                weight += truck[0];
            }

            if (weight >= truck_weights[idx]) {
                queue.add(new int[]{truck_weights[idx], time + bridge_length});
                weight -= truck_weights[idx++];
            }

            time++;
        }

        return time + bridge_length;
    }
}
