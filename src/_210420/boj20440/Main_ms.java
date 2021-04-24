package _210420.boj20440;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
    https://ddb8036631.github.io/알고리즘%20풀이/백준_20440_니가-싫어-싫어-너무-싫어-싫어-내게-오지-마-내게-찝적대지마1/

    1. 결과 : 성공
    2. 시간복잡도 : O(N^2) ? 맞는지는 모르겠습니다.
        - Line 54의 for문 안에 있는 Line 58의 while문이 중첩되서.
    3. 후기
        - 아이디어는 블로그에 적어놨습니다.
        - 우선순위 큐를 생각해내기 어려웠습니다.
*/

public class Main_ms {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        Pair[] pairs = new Pair[N]; // pairs : 입력으로 들어온 모기들의 입장 및 퇴장 시간을 보관합니다.

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int Te = Integer.parseInt(st.nextToken()); // Te : 모기의 입장 시간
            int Tx = Integer.parseInt(st.nextToken()); // Te : 모기의 퇴장 시간

            pairs[i] = new Pair(Te, Tx, 1);
        }

        // 모기의 입장 시간을 오름차순 기준으로 정렬해줍니다.
        Arrays.sort(pairs, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.start, o2.start);
            }
        });

        // 모기의 퇴장 시간을 오름차순 기준으로 정렬해줍니다.
        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.end, o2.end);
            }
        });

        int maxCnt = 0, Tem = 0, Txm = 0; // 각각 모기의 최대 마릿수, 시간대의 연속 구간을 의미합니다.

        for (Pair p : pairs) {
            // p : 다음 번 방에 들어오는 모기
            // 다음 번 모기 p가 현재 방 안에 있는 모기들의 퇴장 시간 전에 입장할 수 있을 때까지 모기를 방에서 모기를 뺍니다.
            // 여기서 제거된 모기들은 다음 번 모기가 들어와도 그 최대값을 늘릴 수 없기 때문입니다.
            while (!pq.isEmpty() && p.start >= pq.peek().end) pq.poll();

            pq.add(p); // 다음 번 모기를 방에 넣습니다.

            // 다음 번 모기를 넣음으로써, 최대 마릿수가 변동되면 적용시켜줍니다.
            // 이 때, Txm은 방에서 가장 빨리 나가는 모기의 퇴장 시간으로 설정해줍니다.
            if (pq.size() > maxCnt) {
                maxCnt = pq.size();
                Tem = p.start;
                Txm = pq.peek().end;
            }

            // 최대 마릿수가 같고, 시간대가 겹친다면 Txm만 조정해줍니다.
            else if (pq.size() == maxCnt && Txm == p.start) {
                Txm = p.end;
            }
        }

        System.out.println(maxCnt + "\n" + Tem + " " + Txm);
    }

    static class Pair {
        int start, end;

        public Pair(int start, int end, int cnt) {
            this.start = start;
            this.end = end;
        }
    }
}
