package _210504.boj12764;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_hz {

    /*
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(NlogN)
     * 3. 풀이
     *    1) 우선순위 큐에 (사람idx, 시간, 들어갔는지/나왔는지 여부)를 저장.
     *    2) 우선순위 큐에 저장된 값을 하나씩 꺼내면서
     *      2-1) 싸지방에 들어온 경우
     *          2-1-1) 들어갈 방 번호(idx)보다 작으면서 빈 방이 있는 경우(!tmp.isEmpty) 해당 방에 입장
     *          2-1-2) 그렇지 않을 경우 들어갈 방 번호에 입장
     *          방문 처리
     *      2-2) 싸지방에서 나가는 경우
     *          idx보다 작은 빈 방 목록(tmp)에 저장
     *          방문 처리 해지
     *
     * */

    public static class Time implements Comparable<Time> {
        int idx, min;   // 사람idx, 시간(분)
        boolean enter;  // 입장 T, 퇴장 F

        Time(int idx, int min, boolean enter) {
            this.idx = idx;
            this.min = min;
            this.enter = enter;
        }

        @Override
        public int compareTo(Time o) {
            return Integer.compare(this.min, o.min);
        }
    }

    public static int stoi(String s) {return Integer.parseInt(s);}
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = stoi(br.readLine());
        StringTokenizer st;

        PriorityQueue<Time> timeline = new PriorityQueue<>();

        for (int n = 1; n <= N; n++) {
            st = new StringTokenizer(br.readLine());
            timeline.add(new Time(n, stoi(st.nextToken()), true));
            timeline.add(new Time(n, stoi(st.nextToken()), false));
        }
        
        HashMap<Integer, Integer> visit = new HashMap<>(); // 사람의 idx, 앉은 자리
        int[] cnt = new int[N];
        int idx = 0;

        // 싸지방에서 퇴장 시 빈 자리를 저장하는 pq. 작은 순으로 정렬
        PriorityQueue<Integer> tmp = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });

        while(!timeline.isEmpty()) {
            Time now = timeline.poll();

            if (now.enter) {    // 입장 시
                if (tmp.isEmpty()) {    // idx보다 작은 빈 자리가 없을 경우 idx에 앉고 idx++
                    cnt[idx]++;
                    visit.put(now.idx, idx);
                    idx++;
                } else {    // idx보다 작은 빈 자리가 있으면 해당 자리에 앉음
                    cnt[tmp.peek()]++;
                    visit.put(now.idx, tmp.peek());
                    tmp.poll();
                }
            } else {    // 퇴장 시 tmp에 앉았던 자리 tmp에 저장
                tmp.add(visit.get(now.idx));
                visit.remove(now.idx);
            }
        }

        System.out.println(idx);
        for (int i = 0; i < idx; i++) System.out.print(cnt[i]+" ");
    }
}
