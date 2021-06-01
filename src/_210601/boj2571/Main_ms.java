package _210601.boj2571;

import java.util.Scanner;

/*
 *   https://ddb8036631.github.io/boj/2571_색종이-3/
 *   1. 결과 : 시간안에 못풀어서 제출 안했습니다. -> 맞았습니다.
 *   2. 시간복잡도 : O(100*100^6)
 *       - 이유
 *           - 색종이 최대 100개.
 *           - 각 색종이당 6중 for문을 돌게 된다. 좌표 범위가 최대 100이므로, 100^6
 *           - 따라서, 100 * 100^6 라고 생각했다.
 *   3. 접근 방식
 *       - 완전 탐색.
 *       - 먼저, 색종이 미리 다 그려놓는다. 그 담에, 자를 수 있는 직사각형을 하나하나 다 확인해가면서 최대값을 갱신하는 작업을 수행한다.
 */

public class Main_ms {
    static final int SIZE = 100;
    static int[][] map;
    static int answer = 100; // answer : 색종이끼리 하나도 안겹쳐도 최소 100이라는 넓이가 나오므로 이렇게 초기화 했다.

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        map = new int[SIZE][SIZE];

        for (int n = 0; n < N; n++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            // 좌하단 좌표를 입력받아 이로부터 +9까지 좌표들을 마킹하자.
            for (int x = a; x < a + 10; x++)
                for (int y = b; y < b + 10; y++)
                    map[x][y]++;
        }

        // (sx, sy)는 잘릴 직사각형의 그림상 좌하단을 가리킴. (ex, ey)는 우상단을 가리킴.
        // 먼저 sx, sy를 설정하자.
        for (int sx = 0; sx < SIZE - 1; sx++) {
            for (int sy = 0; sy < SIZE - 1; sy++) {
                if (map[sx][sy] == 0) continue; // 0이면 나가리~

                // 다음으로 ex, ey를 설정하자.
                for (int ex = sx + 1; ex < SIZE; ex++) {
                    for (int ey = sy + 1; ey < SIZE; ey++) {
                        if (map[ex][ey] == 0) break; // 끝점이 0이면, 더이상 y축으로의 증가는 무의미함. break 걸고 x축을 증가시키자.

                        int now = (ex - sx + 1) * (ey - sy + 1); // 넓이를 구해보자. 미리 구하는 이유는, check() 호출이 불필요한 경우 호출하지 않기 위함.

                        if (now <= answer) continue; // 넓이가 answer 이하면 최대값 구하는 작업에 소용 없으므로 check() 호출도 하지 말고 건너 뛰자.
                        if (check(sx, sy, ex, ey)) // 올바르게 잘렸다면, 최대 넓이를 갱신하자.
                            answer = Math.max(answer, now);
                    }
                }
            }
        }

        System.out.println(answer);
    }

    static boolean check(int sx, int sy, int ex, int ey) {
        for (int x = sx; x <= ex; x++)
            for (int y = sy; y <= ey; y++)
                if (map[x][y] == 0) return false;

        return true;
    }
}
