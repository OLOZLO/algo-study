package _210504.boj12886;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O() = ...??, 이건 모르겟슴다
 *              크기순으로 돌을 정렬했기 때문에 넉넉잡아서 제일 작은 돌은 500을 넘지 않고, 두번째 돌은 1000을 넘지 않음
 *              때문에 정점은 최대 500,000라고 생각함
 *
 *              정점마다 최대 3번의 탐색이 가능하기 때문에 500,000 * 3의 간선이 생성
 *              BFS의 시간 복잡도 = O(V+E) = O(500,000 + 1,500,000) => 안전
 *
 *              정렬은 3log3이니까 제낌...
 *
 * 3. 접근 방식
 *      손으로 몇 번 계산해보니까 경우의 수가 너무 많았음
 *      => 모두 탐색을 해봐야겠다는 생각이 듬
 *
 *      1) 돌의 합이 3의 배수가 아니면 제낌 => return
 *      2) 가능한 돌 교환을 진행.
 *      3) 돌을 정렬. 돌의 크기가 1, 2번째로만 visit처리를 함
 *          어차피 3번째 돌은 sum-(1+2)로 동일하기 때문
 *      4) 모든 돌이 같으면 탈출 => return 1;
 *         끝까지 탐색 못하면 => return 0;
 *
 */
public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] groups = {{0, 1, 2}, {0, 2, 1}, {1, 2, 0}}; // [교환1, 교환2, 교환 X]
        int[] stone = {sc.nextInt(), sc.nextInt(), sc.nextInt()};

        if (Arrays.stream(stone).sum() % 3 != 0) { // 3의 배수 판별
            System.out.println(0);
            return;
        }

        // 크기가 작은 1,2번째 돌로만 visit처리
        // 정렬 없이 1500, 1500도 해봤는데 시간은 얼마 차이도 안나고 공간복잡도만 증가함
        boolean[][] visit = new boolean[500][1000];

        Queue<int[]> queue = new LinkedList<>();
        queue.add(stone);

        Arrays.sort(stone);
        visit[stone[0]][stone[1]] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            if ((cur[0] == cur[1]) && (cur[1] == cur[2])) {
                System.out.println(1);
                return;
            }

            for (int[] group : groups) {
                int s1 = cur[group[0]];
                int s2 = cur[group[1]];

                int min = Math.min(s1, s2);
                int max = Math.max(s1, s2);

                int[] next = {max - min, min * 2, cur[group[2]]}; // 교환하고 나머지는 그냥 넣음
                Arrays.sort(next); // 작은 순 정렬

                if (visit[next[0]][next[1]]) continue;
                queue.add(next);
                visit[next[0]][next[1]] = true;
            }
        }

        System.out.println(0);
    }
}
