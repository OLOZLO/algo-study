package _210504.boj12886;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
    1. 결과 : 성공
    2. 시간복잡도 : O(1500*9) ?
        - while문 안에 for문 안에 for문 존재.
        - 돌 개수 최대 1500개 * 9가지 경우의 수.
    3. 풀이
        1. 맨 먼저 A, B, C를 더해 sum에 저장한다.
        2. sum이 3으로 나눠떨어지지 않으면, 세 그룹이 동일한 돌 개수를 맞추지 못하므로 0을 출력하고 끝낸다.
        3. X와 Y를 지정해 BFS 탐색을 돌린다.
            - 이미 방문한 처리는 2차원 배열로 한다.
            - visit[a][b] == true 는 X값 a로, Y값 b로 돌을 배분한 적이 있다는 의미다.
            - 첨엔 3차원 배열을 구상했지만, 메모리 초과가 발생했다. a와 b로 인해 나머지 한 그룹의 돌 개수가 정해진다는 이유로 2차원으로 충분했다.
        4. BFS 탐색이 끝난 후엔, 우리는 visit[sum/3][sum/3] 만 확인하면 된다. 이 인덱스가 방문처리됐다면, 세 그룹의 돌 개수가 같은 적이 있다는 의미.
*/

public class Main_ms {
    static int[] stones;        // stones : A, B, C 각 그룹의 돌 개수 저장.
    static boolean[][] visit;   // visit[a][b] : 돌 개수 a, b 를 만든 적이 있는 지 체크하기 위함.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        stones = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        visit = new boolean[1501][1501]; // 최악의 경우 : 돌 개수가 세 그룹 모두 500에 가까울 때 한쪽으로 돌을 몰면 최대 1500개까지 가능하다고 생각.
        int sum = 0; // A, B, C 세 그룹에 속한 돌 개수의 총합

        for (int cnt : stones) sum += cnt;

        // 세 그룹에 있는 돌들을 모두 더해서 3으로 딱 안나눠 떨어지면, 세 그룹의 돌 개수가 같을 수가 없음.
        if (sum % 3 != 0) {
            System.out.println(0);
            return;
        }

        Queue<int[]> q = new LinkedList<>();
        visit[stones[0]][stones[1]] = true;
        q.add(new int[]{stones[0], stones[1], stones[2]}); // 세 그룹을 모두 큐에 보관하자!

        while (!q.isEmpty()) {
            int[] now = q.poll();

            // 6가지 경우를 확인해봄.
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // now[i] 를 X, now[j] 를 Y로 설정.
                    if (now[i] < now[j]) {
                        int nx = now[i] + now[i];
                        int ny = now[j] - now[i];

                        if (visit[nx][ny]) continue; // nx개, ny개를 만든 기억이 있다면 더이상 진행 안함. ny는 무조건 1이상이므로 인덱스 검사를 할 필요가 없다.

                        visit[nx][ny] = true;
                        q.add(new int[]{nx, ny, sum - nx - ny}); // poll해서 세 번째 그룹의 돌 개수를 구하지 말고, 미리 계산해 넣어두자.
                    }
                }
            }
        }

        System.out.println(visit[sum / 3][sum / 3] ? 1 : 0); // 삼등분을 했던 적이 있다면 1 출력.
    }
}
