package _210216.boj2931;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_hz {
    static char[][] map;
    static int R, C;
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static boolean[][] block = {{false, false, false, false}, {true, true, false, false}
            , {true, false, false, true}, {false, false, true, true}, {false, true, true, false}
            , {false, true, false, true}, {true, false, true, false},{true, true, true, true}};

    static class Pos {
        int i, j;

        Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        R = Integer.parseInt(s[0]);
        C = Integer.parseInt(s[1]);
        map = new char[R+1][C+1];

        Pos start = null;
        Pos end = null;

        for (int i = 1; i <= R; i++) {
            String in = br.readLine();
            for (int j = 1; j <= C; j++) {
                map[i][j] = in.charAt(j-1);

                // 지도를 입력받으면서 |, -, +는 각각 5, 6, 7로 바꿔서 저장하고, 시작, 끝나는 위치 저장
                if (map[i][j] == '.') continue;
                if (map[i][j] == '|') map[i][j] = '5';
                else if (map[i][j] == '-') map[i][j] = '6';
                else if (map[i][j] == '+') map[i][j] = '7';
                else if (map[i][j] == 'M') start = new Pos(i, j);
                else if (map[i][j] == 'Z') end = new Pos(i, j);
            }
        }

        // 끝나는 위치 주변에 블록이 하나라도 있나 확인 (문제에 나와있는 조건임) 있으면 true, 없으면 false
        boolean aroundZ = false;
        for (int d = 0; d < dir.length; d++) {
            int ni = end.i + dir[d][0];
            int nj = end.j + dir[d][1];

            if (ni <= 0 || ni > R || nj <= 0 || nj > C || map[ni][nj] == '.') continue;
            aroundZ = true;
        }

        boolean[][] visited = new boolean[R+1][C+1];
        visited[start.i][start.j] = true;

        // 시작 위치 주변에 블록이 하나만 인접해있다 했으니, 그 블록을 찾아서 dfs 돌림
        for (int d = 0; d < dir.length; d++) {
            int ni = start.i + dir[d][0];
            int nj = start.j + dir[d][1];

            if (ni <= 0 || ni > R || nj <= 0 || nj > C || map[ni][nj] == '.') continue;
            visited[ni][nj] = true;
            dfs(new Pos(ni, nj), false, visited, aroundZ);
        }
    }
    
    public static void dfs(Pos p, boolean pipe, boolean[][] visited, boolean aroundZ) {

        if (map[p.i][p.j] == '.' && pipe) { // 해당 위치가 .이고 블록을 사용했을 경우
            boolean[] check = new boolean[4];   // 지금 위치에서 내 사방에 파이프가 있나 확인할꺼야

            for (int d = 0; d < dir.length; d++) {
                int ni = p.i + dir[d][0];
                int nj = p.j + dir[d][1];

                // 이거는 내 오른쪽에 있는 파이프는 왼쪽이 뚫려있어야하고, 아래에 있는 파이프는 위쪽으로 뚫려있어야하고
                // 왼쪽에 있는 파이프는 오른쪽으로 뚫려있어야하고, 위에 있는 파이프는 아래쪽으로 뚫려있어야 하는거 확인하기 위해서
                int nd = 1;
                if (d == 0) nd = 2;
                else if (d == 1) nd = 3;
                else if (d == 2) nd = 0;

                if (ni <= 0 || ni > R || nj <= 0 || nj > C || map[ni][nj] == '.' || map[ni][nj] == 'M') continue;
                // 혹시 내 사방중에 Z가 있으면서 Z 주변에 블록이 없을 때는 Z랑 연결될 수도 있고
                // 내 사방의 블럭들 중에서 내쪽으로 파이프가 연결되있을 경우 check를 해줌
                if ((map[ni][nj] == 'Z' && !aroundZ)
                    || (map[ni][nj]-'0' > 0 && map[ni][nj]-'0' < 8 && block[map[ni][nj]-'0'][nd]))
                    check[d] = true;
            }

            // 1~7의 블럭 중에 지금 내가 필요한 모양의 블럭과 일치하는 것이 있나 확인! 있으면 그 블럭 설치하면 된다!
            for (int b = 1; b < block.length; b++) {
                if (check[0] == block[b][0] && check[1] == block[b][1]
                        && check[2] == block[b][2] && check[3] == block[b][3]) {
                    System.out.print(p.i+" "+p.j+" ");
                    if (b < 5) System.out.println(b);
                    else if (b == 5) System.out.println("|");
                    else if (b == 6) System.out.println("-");
                    else if (b == 7) System.out.println("+");
                    System.exit(0);
                }
            }
        }

        else if (map[p.i][p.j] != '.') {    // 해당 위치에 파이프가 존재 할 경우 
            if (map[p.i][p.j] == 'Z') return;

            for (int d = 0; d < dir.length; d++) {
                if (block[map[p.i][p.j]-'0'][d]) {  // 4방향 중 파이프가 연결되어있는 곳이 다음에 갈 수 있는 경로
                    int ni = p.i + dir[d][0];
                    int nj = p.j + dir[d][1];

                    if (ni <= 0 || ni > R || nj <= 0 || nj > C) return;
                    if (map[ni][nj] != '.' && !visited[ni][nj]) {   // 만약 그 곳을 방문하지 않고 파이프가 연결되어있으면
                        visited[ni][nj] = true;
                        dfs(new Pos(ni, nj), pipe, visited, aroundZ);
                    } else if (map[ni][nj] == '.' && !pipe){        // 만약 파이프가 존재하지 않고, 파이프를 설치하지 않았으면
                        visited[ni][nj] = true;
                        dfs(new Pos(ni, nj), true, visited, aroundZ);   // 거기에 파이프를 설치할꺼야
                    }
                }
            }
        }
    }
    
}
