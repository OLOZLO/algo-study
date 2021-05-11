package _210504.boj12886;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_hz {

    /*
     * 1. 결과 : 성공
     * 2. 시간복잡도 : 모르겠습니다...
     * 3. 풀이
     *    : bfs를 사용하여 완전탐색을 진행하였음. 
     *      이때 큐에 들어가는 돌들의 배열은 항상 정렬하여 중복되는 경우를 최대한 방지하고자 함.
     *      시간초과가 나서 미처 고려하지 못한 방문처리를 위해 HashSet을 사용함
     *
     * */

    // 방문처리를 하기 위한 객체 ( equals, haseCode는 자동생성 했습니다...)
    public static class Stone {
        int a, b, c;

        Stone(int[] arr) {
            this.a = arr[0];
            this.b = arr[1];
            this.c = arr[2];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Stone stone = (Stone) o;
            return a == stone.a &&
                    b == stone.b &&
                    c == stone.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] stones = new int[3];
        stones[0] = Integer.parseInt(st.nextToken());
        stones[1] = Integer.parseInt(st.nextToken());
        stones[2] = Integer.parseInt(st.nextToken());
        Arrays.sort(stones); // 방문처리의 일관성과 계산을 편하게 하기 위해 정렬했습니다.

        Queue<int[]> q = new LinkedList<>();
        HashSet<Stone> visited = new HashSet<>();
        q.add(stones);
        visited.add(new Stone(stones));

        boolean possible = false;

        while(!q.isEmpty()) {
            int[] now = q.poll();

            if (now[0] == now[1] && now[0] == now[2]) { // 세 돌의 크기가 다 같은 경우
                possible = true;
                break;
            }

            // 아래의 두 if 절은 동일. 제일 작은 돌과 그 밖의 돌들을 교환하기 위한 절 (함수로 뺄까 했지만 시간이 없었습니다ㅠ)
            // 두 돌이 같지 않고, 돌을 교환한 뒤 서로의 개수가 상반되는 경우(무한루프에 빠짐)가 아닐 경우
            // 돌을 교환하고 정렬시킨 뒤 이미 방문 해 본 돌 세트가 아닐경우 큐에 추가
            if (now[0] < now[1] && now[1] != now[0]*2) {
                int[] tmp = {now[0]*2, now[1]-now[0], now[2]};
                Arrays.sort(tmp);
                if (!visited.contains(new Stone(tmp))) {
                    visited.add(new Stone(tmp));
                    q.add(tmp);
                }
            }

            if (now[0] < now[2] && now[2] != now[0]*2) {
                int[] tmp = {now[0]*2, now[1], now[2]-now[0]};
                Arrays.sort(tmp);
                if (!visited.contains(new Stone(tmp))) {
                    visited.add(new Stone(tmp));
                    q.add(tmp);
                }
            }
        }

        System.out.println(possible ? 1 : 0);
    }

}
