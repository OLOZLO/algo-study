package _210504.boj15905;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*
    1. 결과 : 성공
    2. 시간복잡도 : O(NlogN)
        - 이유 : 리스트 크기 N을 순회하는 시간보다, 정렬하는데 드는 시간이 더 크므로.
    3. 풀이
        1. 입력을 리스트에 담고, 푼 개수(solved) 내림차순, 같다면 패널티 총합(penalty) 오름차순 정렬한다.
        2. 리스트를 순회하며 등수(cnt)를 하나씩 늘려간다.
            - 5등이면 fifth 객체에 정보(푼 개수, 패널티 총합)을 담는다.
            - 6등을 넘어가면서 동시에 5등과 푼 개수가 같다면 answer 개수를 1 증가시킨다.
*/

public class Main_ms {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        ArrayList<Pair> list = new ArrayList<>();

        while (N-- > 0) {
            st = new StringTokenizer(br.readLine(), " ");
            int solved = Integer.parseInt(st.nextToken());  // solved : 해결한 문제 개수
            int penalty = Integer.parseInt(st.nextToken()); // penalty : 패널티 총합

            list.add(new Pair(solved, penalty));
        }

        Collections.sort(list);  // 해결한 문제 내림차순으로(같다면 패널티 총합 오름차순으로) 정렬한다.
        int cnt = 0, answer = 0; // cnt : 등수, answer : 5등과 푼 문제 수는 같지만 패널티 차이로 수상하지 못한 학생들의 수
        Pair fifth = null;       // fifth : 5등 정보를 담아둘 객체

        for (Pair now : list) {
            cnt++; // 한 명 들렀으니 등수 증가시킨다.

            if (cnt == 5) {
                fifth = now; // 5등이면 fifth에 정보를 보관한다.
            }

            if (cnt > 5 && fifth != null && fifth.solved == now.solved) { // 5등보다 밑인데(6이상), 5등과 푼 문제 수가 같다면 체크한다.
                answer++;
            }
        }

        System.out.println(answer);
    }

    static class Pair implements Comparable<Pair> {
        int solved, penalty;

        public Pair(int solved, int penalty) {
            this.solved = solved;
            this.penalty = penalty;
        }


        @Override
        public int compareTo(Pair p) {
            return this.solved == p.solved ? this.penalty - p.penalty : p.solved - this.solved;
        }
    }
}
