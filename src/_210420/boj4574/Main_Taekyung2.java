package _210420.boj4574;

import java.util.Scanner;

public class Main_Taekyung2 {

    /**
     * [골드1] 스도미노쿠
     * 1. 결과 : 실패 ( 시간 부족 )
     * 2. 시간 복잡도 : O(tc * 9 * 9 * 4 * 10 * 10)
     * 	- 이유
     *    테스트 케이스 * 가로 * 세로 * 4방향 * (숫자1 ~ 10 * 숫자 1 ~ 10)도미노
     *
     * 	- 접근 방식
     * 		1) 기본적은 백트래킹 스도쿠에서, 2칸을 세트로 채워야 하는 도미노가 추가되었음
     *
     * 		2) 백트래킹 하면서 방문처리, 해제 꼼꼼하게 해주면 어려운 구현은 없는 듯
     *
     * 3. 후기
     *	- 시간에 쫓겨서 풀다보니 계속 실수가 나오는데, 차근차근 푸는 연습을 해야겠음
     *
     */


    static int N, tc = 0;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    static int[][] map;
    static boolean exit;
    static boolean[][] col, row, square, domino;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            tc++;
            N = sc.nextInt();
            exit = false;
            if(N == 0) break;

            map = new int[9][9];
            row = new boolean[9][10]; // row[y][n] = y번 행에 숫자 n을 썼는가
            col = new boolean[9][10]; // col[x][n] = x번 열에 숫자 n을 썼는가
            square = new boolean[9][10]; // square[k][n] = k번 째 사각형에 숫자 n을 썼는가
            domino = new boolean[10][10]; // domino[a][b] = a, b로 이루어진 도미노를 썼는가

            // 도미노 입력
            for(int i = 0; i < N; i++) {
                int n1 = sc.nextInt();
                chk(n1, sc.next());
                int n2 = sc.nextInt();
                chk(n2, sc.next());
                domino[n1][n2] = true;
                domino[n2][n1] = true;
            }

            // 숫자 입력
            for(int i = 1; i < 10; i++) {
                chk(i, sc.next());
                domino[i][i] = true;
            }

            backtrack(0);
        }
    }

    static void backtrack(int cnt) {
        // 답을 구했을 때 리턴 플래그
        if(exit) return;
        // 모든 맵을 다 채웠음
        if(cnt == 81) {
            System.out.println("Puzzle " + tc);
            for(int[] a : map) {
                for(int b : a)
                    System.out.print(b);
                System.out.println();
            }
            exit = true;
            return;
        }

        int y = cnt / 9, x = cnt % 9;

        // 이미 채운 곳이라면 다음 칸으로
        if(map[y][x] != 0) {
            backtrack(cnt + 1);
        }
        else {
            // 현재 칸을 채울 수
            for(int n = 1; n < 10; n++) {
                if(numChk(y, x, n))
                    continue;

                row[y][n] = col[x][n] = square[getIdx(y, x)][n] = true;
                map[y][x] = n;

                // 4방향 체크, 도미노 뒤집어서 확인 안하려고
                for(int d = 0; d < 4; d++) {
                    int ny = y + dy[d];
                    int nx = x + dx[d];
                    if(ny < 0 || nx < 0 || ny >= 9 || nx >= 9 || map[ny][nx] != 0)
                        continue;

                    // 다음 칸을 채울 수
                    for(int nn = 1; nn < 10; nn++) {
                        if(domino[n][nn] || numChk(ny, nx, nn))
                            continue;

                        // 선택한 숫자, 도미노 체크
                        domino[n][nn] = domino[nn][n] = true;
                        row[ny][nn] = col[nx][nn] = square[getIdx(ny, nx)][nn] = true;
                        map[ny][nx] = nn;

                        backtrack(cnt + 1);

                        // 백트래킹 끝났음, 선택한 숫자 도미노 체크 해제
                        domino[n][nn] = domino[nn][n] = false;
                        row[ny][nn] = col[nx][nn] = square[getIdx(ny, nx)][nn] = false;
                        map[ny][nx] = 0;
                    }
                }
                // 체크 해제
                row[y][n] = col[x][n] = square[getIdx(y, x)][n] = false;
            }
            map[y][x] = 0;
        }
    }

    // 행, 열, 정사각형에 이 숫자를 쓸 수 있는지 여부
    static boolean numChk(int y, int x, int n) {
        return row[y][n] || col[x][n] || square[getIdx(y, x)][n];
    }

    // 초기 도미노, 숫자 대입
    static void chk(int num, String point) {
        int y = point.charAt(0) - 'A';
        int x = point.charAt(1) - '1';
        map[y][x] = num;
        row[y][num] = col[x][num] = square[getIdx(y, x)][num] = true;
    }

    // 정사각형 인덱스 반환
    static int getIdx(int y, int x) {
        return (y / 3) * 3 + x / 3;
    }
}
