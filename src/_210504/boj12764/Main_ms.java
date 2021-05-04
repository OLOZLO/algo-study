package _210504.boj12764;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
    1. 결과 : 실패(20%)
    2. 시간복잡도 : O(N)
        - 이유 : 우선순위 큐(PQ)에 최대 N명이 있을 수 있으므로.
    3. 풀이
        1. 입력(시작, 끝시간)을 받아 시작 시간을 오름차순으로 정렬하는 리스트를 만든다.
        2. 끝 시간을 내림차순으로, 끝 시간이 같다면 차지하고 있는 자리 번호를 오름차순으로 정렬하는 PQ를 만든다.
        3. 컴퓨터 자리 번호는 최대 100,000까지 가능.
        4. 시작 시간 오름차순으로 정렬된 리스트를 순회하며 다음 과정을 거친다.
            - 이번 사람과 현재 앉아서 컴터하고 있는 사람들의 끝나는 시간을 비교한다.
                - 이번 사람보다 일찍 끝내는 사람이 존재하면 그 사람을 PQ에서 빼고(내가 시작할 시간에 이미 그 사람은 끝났으니까) 그 자리에 앉힌다. 빼면서 동시에 해당 자리에 앉았음을 표시한다.
                - 그런 사람이 없다면(빠지는 사람이 없다면), 다음 번 의자에 앉힌다.
            - 위 과정 거치면, PQ에는 남아있는 사람들이 있다. 얘네는 하나씩 빼주면서 해당하는 자리 번호의 값을 1 증가해준다.
    4. 후기
        - 왜 틀렸는지, 로직 어디가 잘못됐는지 모르겠다. 도움 주시면 감사요!
*/

public class Main_ms {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        ArrayList<int[]> list = new ArrayList<>();

        // 이용 시작 시간 P와 종료 시각 Q를 입력 받자마자 리스트에 추가.
        while (N-- > 0) list.add(Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray());

        // 0번 인덱스 요소 : 시작 시각
        // 1번 인덱스 요소 : 종료 시각
        // 시작 시각 오름차순으로 정렬함.
        Collections.sort(list, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        // 0번 인덱스 요소 : 종료 시각
        // 1번 인덱스 요소 : 자리의 번호
        // 종료 시각 오름차순으로(같다면 자리 번호 오름차순으로) 정렬함.
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0];
            }
        });

        // 최악의 경우 한 사람당 자리 하나를 차지할 수 있으므로, N개만큼 잡음.
        int[] chairs = new int[100001];

        // cnt : 현재까지 의자가 다 차있을 때, 다음 번 공석을 의미.
        // 예를 들어, 1번 2번 3번 4번이 차있으면 cnt는 5를 가리킴.
        int cnt = 1;

        for (int[] time : list) {
            int idx = Integer.MAX_VALUE; // idx : 얘는 방금 빈 자리 번호를 기억하기 위함.

            if (!pq.isEmpty() && pq.peek()[0] <= time[0]) { // 자리 중 이번 사람이 시작하는 시간보다 먼저 자리를 비우는 사람이 있다면,
                int[] now = pq.poll();                      // 걔를 사용중에서 제거한다.
                idx = Math.min(idx, now[1]);                // 공석이 났으니까 걔의 자리 번호를 idx에 보관하고,
                chairs[now[1]]++;                           // 걔를 뺐으니까 자리 번호에 해당하는 인원수를 늘려준다.
            }

            // 이번 사람을 앉힌다.
            // 공석이 있었다면 그 자리 번호로 앉히고,
            // 공석이 없다면 자리를 새로 할당한다.
            pq.add(new int[]{time[1], idx==Integer.MAX_VALUE ? cnt++ : idx});
        }

        // 사용중인 애들 하나씩 꺼내서,
        // 의자에 이용 표시를 해준다.
        while (!pq.isEmpty()) {
            int[] now = pq.poll();
            chairs[now[1]]++;
        }

        cnt = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < chairs.length; i++) {
            if (chairs[i] == 0) break;

            cnt++;
            sb.append(chairs[i] + " ");
        }

        System.out.println(cnt);
        System.out.println(sb);
    }
}
