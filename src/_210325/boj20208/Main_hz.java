package _210325.boj20208;

import java.util.Scanner;

public class Main_hz {
    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int[][] map;
    static boolean[][] visited;
    static int N, M, H, result;
    // 초기 체력 M (이동할 수 있는 거리) 이동 시 체력이 1만큼 줄어듬, 민초우유 마시면 체력 H만큼 증가해서 초기체력 이상으로 증가 가능
    // 체력 0이면 이동 못해. 얼마나 많은 민초우유 마실 수 있는지

    public static class Pos {
        int i, j, power, max;

        Pos(int i, int j, int power, int max) {
            this.i = i;
            this.j = j;
            this.power = power;
            this.max = max;
        }

        @Override
        public String toString() {
            return "Pos{" +
                    "i=" + i +
                    ", j=" + j +
                    ", power=" + power +
                    ", max=" + max +
                    '}';
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        H = sc.nextInt();

        map = new int[N][N];
        visited = new boolean[N][N];
        Pos start = null;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = sc.nextInt();

                if (map[i][j] == 1) {
                    start = new Pos(i, j, M, 0);
                }
            }
        }

        result = 0;
        dfs(start, 0, true);
        System.out.println(result);

    }
    
    public static void dfs(Pos p, int depth, boolean start) {
        // 지금까지 걸어온 길 < power 일 때만 더 갈 수 있음. 같으면 멈춰야됨
        System.out.println(p);

        if (!start && map[p.i][p.j] == 1) {
            result = Math.max(result, p.max);
            return;
        }

        if (p.power == 0) return;

        for (int d = 0; d < dir.length; d++) {
            int ni = p.i + dir[d][0];
            int nj = p.j + dir[d][1];

            if (ni >= 0 && ni < N && nj >= 0 && nj < N && !visited[ni][nj]) {
                visited[ni][nj] = true;
                if (map[ni][nj] == 2) {
                    dfs(new Pos(ni, nj, p.power+H-1, p.max+1), depth+1, false);
                } else {
                    dfs(new Pos(ni, nj, p.power-1, p.max), depth+1, false);
                }
                visited[ni][nj] = false;
            }
        }
        
    }
}
