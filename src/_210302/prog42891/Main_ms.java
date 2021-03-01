package _210302.prog42891;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

// https://ddb8036631.github.io/알고리즘%20풀이/프로그래머스_카카오2019_무지의-먹방-라이브/
public class Main_ms {
    public static long solution(int[] food_times, long k) {
        long sum = 0;
        long prev = 0;


        for (int time : food_times) sum += time;

        if (sum <= k) return -1; // 다 돌며 섭취 시간을 더한 값이 k 이하면, k 초 후에는 먹을 음식이 없음..

        sum = 0;

        // 우선순위 큐는 섭취 시간 오름차순으로 정렬.
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        // 우선순위 큐에 각 음식의 {섭취 시간, 인덱스}를 객체로 넣어줌.
        for (int i = 0; i < food_times.length; i++) {
            pq.add(new int[]{food_times[i], i});
        }

        // 맨 앞 음식을 아예 배제해버릴 수 있는지를 판단. 음식의 섭취시간 0으로 만들 수 있는지를 판단.
        //  1. 경과된 시간을 고려해서, peek한 음식까지 해치울 수 있으면 큐에서 poll.
        //  2. 안된다면 인덱스로 답을 추려내자.
        while (sum + (pq.peek()[0] - prev) * pq.size() <= k) {
            sum += (pq.peek()[0] - prev) * pq.size();
            prev = pq.peek()[0];
            pq.poll();
        }

        ArrayList<int[]> list = new ArrayList<>(pq); // // 남은 음식들은 리스트에 넣고 인덱스를 골라내자.

        // 리스트는 인덱스 오름차순으로 정렬.
        Collections.sort(list, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        // k 초 후에 어느 음식을 섭취할 지는, 경과 시간 sum 을 고려해 그 인덱스를 찾아서 리턴해주자.
        return list.get((int) ((k - sum) % list.size()))[1] + 1;
    }

    public static void main(String[] args) {
        solution(new int[]{3, 1, 2}, 5);
//        solution(new int[]{8, 6, 4}, 15);
    }
}
