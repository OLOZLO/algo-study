package _210202.boj17837;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] dy = {0, 0, -1, 1}, dx = {1, -1, 0, 0}, reverse = {1, 0, 3, 2}; // reverse는 나한테서 뒤집은 경우 변할 방향 index
        int N = sc.nextInt(), K = sc.nextInt();

        int[][] map = new int[N][N];
        Deque<Integer>[][] stack = new ArrayDeque[N][N]; // 각 칸에 말을 순서대로 쌓을 stack생성

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = sc.nextInt();
                stack[i][j] = new ArrayDeque<>();
            }
        }

        int[][] chess = new int[K][3]; // y좌표, x좌표, 방향 index
        for (int i = 0; i < K; i++) {
            chess[i] = new int[]{sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt() - 1};

            stack[chess[i][0]][chess[i][1]].push(i); // 말이 있는 위치의 stack에 추가해줌
        }

        int turn = 0;
        while (turn <= 1000) { // turn이 1000이하일 때까지!
            turn++;

            for (int i = 0, chance = 1; i < K; i++) {
                int[] piece = chess[i];
                int ny = piece[0] + dy[piece[2]], nx = piece[1] + dx[piece[2]];

                if (ny < 0 || nx < 0 || ny >= N || nx >= N || map[ny][nx] == 2) { // 밖으로 벗어나거나, 파란색인경우
                    if (chance == 0) { // 찬스 없으면 돌아가렴
                        chance = 1; // 찬스 갱신
                        continue;
                    }

                    piece[2] = reverse[piece[2]]; // 방향 뒤집기
                    i--;
                    chance--; // 다시하게 해주는 대신 찬스 하나 깜
                    continue;
                }

                if (map[ny][nx] == 0) { // 하얀 칸
                    Deque<Integer> tmp = new ArrayDeque<>(); // 순서대로 진입하니까 tmp 하나 만들어두고 이동하면
                    /*
                    기존  1       tmp 3       next 1
                         2    ->     2   ->       2
                         3           1            3
                     */

                    while (true) { // 현재 움직이고 있는 말이 나올 때까지 기존 위치의 있는말을 모두 tmp로 이동
                        tmp.push(stack[piece[0]][piece[1]].pop());
                        chess[tmp.peek()][0] = ny;
                        chess[tmp.peek()][1] = nx;
                        if (tmp.peek() == i) break; // 현재 움직이려는 말 나왔당 ㅌㅌ
                    }
                    while (!tmp.isEmpty()) // 모두 이동했으면 다시 tmp의 말들을 다음 칸으로 이동시킴
                        stack[ny][nx].push(tmp.pop());

                } else if (map[ny][nx] == 1) { // 빨간색!
                    Deque<Integer> next = stack[ny][nx]; // 빨간색은 뒤집어서 들어가니까 스택 그대로 뒤집으면서 넣어주면 됨

                    while (true) {
                        next.push(stack[piece[0]][piece[1]].pop());// 기존에 있던 칸에서 다음 칸으로 이동
                        chess[next.peek()][0] = ny;
                        chess[next.peek()][1] = nx;
                        if (next.peek() == i) break;
                    }
                }

                if (stack[ny][nx].size() >= 4) { // 이동한 칸이 4이상이면 게임 종료
                    System.out.println(turn);
                    System.exit(0);
                }
                chance = 1;
            }

        }

        System.out.println(-1);
    }
}
