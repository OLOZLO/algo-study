package _210216.boj2931;

import java.util.Scanner;

public class Main_girawhale {
    static int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1}; //상, 하, 좌, 우
    static char[][] map;
    static boolean[][] visit;
    static int R, C;
    static char[] pipe = {'+', '1', '2', '3', '4', '|', '-'};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        map = new char[R][C];
        visit = new boolean[R][C];

        int[] start = null;
        for (int i = 0; i < R; i++) {
            char[] in = sc.next().toCharArray();

            for (int j = 0; j < C; j++) {
                if (in[j] == 'M') start = new int[]{i, j};
                map[i][j] = in[j];
            }
        }

        visit[start[0]][start[1]] = true;
        for (int i = 0; i < 4; i++) { // 시작점 주위로 연결된 파이프 찾아서 걔부터 DFS
            int ny = start[0] + dy[i], nx = start[1] + dx[i];
            if (ny < 0 || nx < 0 || ny >= R || nx >= C || map[ny][nx] == '.' || map[ny][nx] == 'Z') continue;
            solve(ny, nx, getNextDir(map[ny][nx], i));
        }

    }

    static void solve(int y, int x, int dir) {
        for (int i = 0; i < 4; i++) {
            if (((1 << i) & dir) == 0) continue; // 갈 수 없는 방향이면 pass

            int ny = y + dy[i], nx = x + dx[i];
            if (visit[ny][nx]) continue;

            if (map[ny][nx] == '.') { // 가야되는 위친데 '.'이면 파이프 설치해야댐
                for (char p : pipe) {
                    int d = getNextDir(p, i);
                    if (d != 0 && ckSetPipe(ny, nx, d)) { // 파이프에 들어가지 못하거나, 파이프에 들어갔는데 해당 방향들에 모두 파이프가 연결이 안되어있다면 pass
                        System.out.println((ny + 1) + " " + (nx + 1) + " " + p); // 파이프 설치 가능하니까 반환하고 종료
                        System.exit(0);
                    }
                }
            }

            visit[ny][nx] = true;
            solve(ny, nx, getNextDir(map[ny][nx], i));
        }
    }

    static boolean ckSetPipe(int y, int x, int dir) {
        for (int i = 0; i < 4; i++) {
            if (((1 << i) & dir) == 0) continue;

            int ny = y + dy[i], nx = x + dx[i];
            if (ny < 0 || nx < 0 || ny >= R || nx >= C
                    || map[ny][nx] == '.' || getNextDir(map[ny][nx], i) == 0) return false; // 파이프 연결되있는데 못가는 방향이면 false 반환
        }
        return true;
    }

    static int getNextDir(char pipe, int in) { // 정리하려다가 실패... 진입 방향이랑, 파이프로 다음에 갈 수있는 방향 비트 체크한거....
        if (pipe == '+')
            return 1 << 4 - 1;
        else if (pipe == '|') {
            if (in == 0) return 1 << 0;
            else if (in == 1) return 1 << 1;
        } else if (pipe == '-') {
            if (in == 2) return 1 << 2;
            else if (in == 3) return 1 << 3;
        } else if (pipe == '1') {
            if (in == 2) return 1 << 1;
            else if (in == 0) return 1 << 3;
        } else if (pipe == '2') {
            if (in == 1) return 1 << 3;
            else if (in == 2) return 1 << 0;
        } else if (pipe == '3') {
            if (in == 3) return 1 << 0;
            else if (in == 1) return 1 << 2;
        } else if (pipe == '4') {
            if (in == 3) return 1 << 1;
            else if (in == 0) return 1 << 2;
        }
        return 0; // 해당 진입 방향으로 파이프에 들어가지 못하면 0...
    }
}
