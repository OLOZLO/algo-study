package _210601.boj20040;

import java.util.Scanner;

/**
 * [골드3] 색종이 - 3
 * 1. 결과 : 맞았습니다.
 * 2. 시간복잡도 : O(N^3)
 *
 * 3. 후기
 * 	- 분류에 누적합 보고 풀었슴니다
 * 	- 비슷한거를 많이 풀어봐야 풀이가 생각날 듯, 한 번에 떠올리지 못해따
 *
 */

public class Main_Taekyung2 {
    static int N, ret = 0;
    static int[][] board = new int[101][101];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        for(int i = 0; i < N; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            // 도화지에 색종이를 마킹합니다
            for(int x = 0; x < 10; x++)
                for(int y = 0; y < 10; y++)
                    board[y + b][x + a] = 1;
        }

        // 각각의 칸에서 세로로 몇칸을 내려갈 수 있는지 구합니다
        for(int i = 100; i > 1; i--)
            for(int j = 0; j < 100; j++)
                if(board[i - 1][j] != 0)
                    board[i - 1][j] += board[i][j];

        // 각각의 칸에서 오른쪽으로 가로 길이를 늘리면서 가장 큰 직사각형을 찾습니다
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                if(board[i][j] == 0)
                    continue;
                int h = board[i][j];
                for(int k = j; k < 100; k++) {
                    // 가로 칸들 중에 높이가 가장 낮은 값으로 직사각형 넓이 구함
                    h = Math.min(h, board[i][k]);
                    ret = Math.max(ret, h * (k - j + 1));
                }
            }
        }
        System.out.println(ret);
    }
}
