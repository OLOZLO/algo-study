package _210608.boj1669;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
 *   1. 결과 : 런타임 에러(NegativeArraySize)
 *   2. 접근 방식
 *       - BFS 탐색.
 *       - Y값이 int 범위 최대값일 수 있으므로, 오버플로우가 나서 배열 크기가 음수로 잡힌느 것 같습니다.
 *       - 근데 이거 말고도, 배열 크기가 너무 커서 힙에 안잡힐 거 같네요. 다른 방법이 떠오르지 않아 2번 문제로 넘어갔습니다.
 */

public class Main_ms {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt();
        int Y = sc.nextInt();

        if (X == Y) {
            System.out.println(0);
            System.exit(0);
        }

        boolean[] visit = new boolean[Y + 1];
        int answer = 0;
        int[] dx = {-1, 0, 1};
        Queue<Pair> queue = new LinkedList<>();
        visit[X] = true;
        queue.add(new Pair(X, 0));
        int cnt = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int s = 0; s < size; s++) {
                Pair now = queue.poll();

                if (now.height == Y) {
                    answer = cnt;
                    break;
                }

                for (int d = 0; d < 3; d++) {
                    int nd = now.delta + dx[d];

                    if (nd < 0 || now.height + nd > Y) continue;

                    if (!visit[now.height + nd]) {
                        visit[now.height + nd] = true;
                        queue.add(new Pair(now.height + nd, nd));
                    }
                }
            }

            cnt++;
        }

        System.out.println(answer);
    }

    static class Pair {
        int height, delta;

        public Pair(int height, int delta) {
            this.height = height;
            this.delta = delta;
        }
    }
}
