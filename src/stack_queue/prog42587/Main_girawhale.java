package stack_queue.prog42587;

import java.util.PriorityQueue;

public class Main_girawhale {
    public int solution(int[] priorities, int location) {
        int N = priorities.length;
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? Integer.compare(o2[0], o1[0]) : Integer.compare(o1[1], o2[1]));
        for (int i = 0; i < priorities.length; i++)
            queue.add(new int[]{priorities[i], i});

        int idx = 0, pre = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            if (cur[1] < pre)
                queue.add(new int[]{cur[0], cur[1] + N});
            else {
                pre = cur[1] % N;
                idx++;
                if (pre == location)
                    break;
            }
        }

        return idx;
    }
}
