package _210504.boj12764;

import java.io.*;
import java.util.*;

public class Main_Taekyung2 {

    /*
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(NlogN)
     * 3. 풀이
     *    - 정렬, 우선순위큐 이용해서 풀었슴다
     *    - 얼마전에 거의 똑같은거 풀어본 기억이 나서 쉽게 풀 수 있었슴니다.
     *
     * */

    static int N;
    static Marin[] marins;
    static int[] ret;

    static int stoi(String s) { return Integer.parseInt(s); }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = stoi(br.readLine());
        marins = new Marin[N];
        ret = new int[N];

        for(int i = 0; i < N; i++) {
            String[] s = br.readLine().split(" ");
            marins[i] = new Marin(stoi(s[0]), stoi(s[1]));
        }
        // 시작 시간 오름차순으로 정렬합니다
        Arrays.sort(marins, Comparator.comparingInt(m -> m.start));

        // 컴퓨터 자리 난 곳 중에 번호 낮은거 뽑기 위한 최소힙
        PriorityQueue<Integer> minpq = new PriorityQueue<>();
        // (끝나는 시간, 사용한 자리) 형태로 저장, 끝나는 시간 오름차순으로 최소 힙
        PriorityQueue<int[]> endpq = new PriorityQueue<>(Comparator.comparingInt(m -> m[0]));

        // 0번 자리부터 채워나감
        int seat = 0;
        for(Marin m : marins) {
            // 퇴장 시간 지난 애들은 우선순위큐에서 뺌, 뺀 자리 좌석은 빈자리 우선순위큐에 넣음
            while(!endpq.isEmpty() && m.start >= endpq.peek()[0])
                minpq.add(endpq.poll()[1]);

            // 남은 자리가 없으면 자리 하나 만들고 넣음
            if(minpq.isEmpty()) {
                endpq.add(new int[]{m.end, seat});
                ret[seat++]++;
            } else {
                //남은 자리 있으면 제일 낮은 번호 좌석 부여
                int min = minpq.poll();
                endpq.add(new int[]{m.end, min});
                ret[min]++;
            }
        }

        StringBuilder sb = new StringBuilder();

        sb.append(seat).append('\n');
        for(int i = 0; i < seat; i++)
            sb.append(ret[i]).append(" ");

        System.out.println(sb);
    }

    static class Marin {
        int start, end;

        Marin(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
