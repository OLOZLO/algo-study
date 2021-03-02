package _210302.prog42891;

import java.util.*;

public class Main_hz {

    static class Food {
        int idx, time;

        Food(int idx, int time) {
            this.idx = idx;
            this.time = time;
        }
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{3, 1, 2}, 5));
        System.out.println(solution(new int[]{4, 2, 3, 6, 7, 1, 5, 8}, 16));
        System.out.println(solution(new int[]{4, 2, 3, 6, 7, 1, 5, 8}, 27));
        System.out.println(solution(new int[]{1, 1, 1, 1}, 4));
    }

    public static int solution(int[] food_times, long k) {

        long sum = 0;
        for (int time : food_times)
            sum += time;

        if (sum <= k) return -1;

        PriorityQueue<Food> pq = new PriorityQueue<>(new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                return Integer.compare(o1.time, o2.time);
            }
        });

        for (int i = 0; i < food_times.length; i++) {
            pq.add(new Food(i+1, food_times[i]));
        }

        long time = 0;
        int prev = 0;

        // 음식 별 필요시간을 오름차순 정렬하면 소요시간을 한번에 줄일 수 있음
        // 소요시간이 K 보다 작을때까지만 계속 뭉텅뭉텅 음식 먹음
       while (time + (pq.peek().time-prev)*pq.size() <= k) {
            time += (pq.peek().time-prev)*pq.size();
            prev = pq.peek().time;
            pq.poll();
        }

        List<Food> list = new ArrayList<>();
        while(!pq.isEmpty()) list.add(pq.poll());

        Collections.sort(list, new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                return Integer.compare(o1.idx, o2.idx);
            }
        });
        
        // K시간까지 남은 시간을 남은 음식의 개수로 나눈 나머지를 이요하면 먹을 음식 번호를 구할 수 있음
        return list.get((int) ((k-time)%list.size())).idx;
    }
}
