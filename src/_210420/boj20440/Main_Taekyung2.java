package _210420.boj20440;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main_Taekyung2 {

    /**
     * [골드4] 니가 싫어 싫어 ~~~..
     * 1. 결과 : 성공
     * 2. 시간 복잡도 : O(N * logN)
     * 	- 이유
     *    정렬, 우선순위큐 -> NlogN
     *
     * 	- 접근 방식
     * 		1) 카카오 문제 중에 비슷한거 풀어본 적 있어서 응용했음
     *
     * 		2) 숫자 범위가 커서, 범위 늘려가면서 카운트하는 건 안될 것 같고, 우선순위큐로 상대 비교하면서 어떻게 해보려고 했음
     *
     * 3. 후기
     *	- 스택, 큐, pq 이런거 쓰는 문제 좀 더 풀어봐야겠음
     *
     */


    static int N;
    static Bug[] bugs;

    static int stoi(String s) { return Integer.parseInt(s); }
    public static void main(String[] args) throws IOException {
        // 모기 최대 100_0000 -> br 사용
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = stoi(br.readLine());
        bugs = new Bug[N];

        for(int i = 0; i < N; i++) {
            String[] s = br.readLine().split(" ");
            bugs[i] = new Bug(stoi(s[0]), stoi(s[1]));
        }

        // 모기를 들어온 시간 오름차순으로 정렬
        Arrays.sort(bugs, (b1, b2) -> {
            if(b1.start == b2.start)
                return b1.end - b2.end;
            return b1.start- b2.start;
        });

        // cnt = 가장 많았을 때 몇 마리, [S, E] 연속 범위
        int cnt = 0, S = 0, E = 0;

        // 모기가 나간 시간 오름차순으로, pq에 넣는다
        // pq 크기는 동시에 있을 수 있는 모기의 수
        PriorityQueue<Bug> pq = new PriorityQueue<>((b1, b2) -> {
            if(b1.end == b2.end)
                return b1.start - b2.start;
            return b1.end - b2.end;
        });

        // 모기가 들어온 시간 순서대로 pq에 넣을 것
        for(Bug b : bugs) {
            // 지금 모기가 들어온 시간보다 pq.peek()의 나간 시간이 클 때까지 pop
            while(!pq.isEmpty() && pq.peek().end <= b.start)
                pq.poll();
            // pq에 넣어줌
            pq.add(b);

            // 모기 숫자가 늘었음
            if(cnt < pq.size()) {
                cnt = pq.size();
                // 늘어난 개수에 맞게, 범위 조정
                S = b.start;
                E = pq.peek().end;
                // 모기 하나를 넣었는데 cnt가 똑같고, 연속되는 범위j
            } else if(cnt == pq.size() && E == b.start) {
                // 끝 범위를 조정
                E = pq.peek().end;
            }
        }

        System.out.println(cnt);
        System.out.println(S + " " + E);
    }


    static class Bug {
        int start, end;

        Bug(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
