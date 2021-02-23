package _210223.boj2151;

import java.util.*;

public class Main_Taekyung2 {
    static char[][] map;
    static int N, sx = -1, sy, ex, ey, ret = Integer.MAX_VALUE;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    // 대각선 거울 두 개, 빛 들어갔을 때 나오는 방향
    static int[][] mirror = {{1, 0, 3, 2}, {3, 2, 1, 0}};
    // (r, c)로 d 방향으로 들어갈 때 사용한 최소 거울 갯수
    static int[][][] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        map = new char[N][N];
        visited = new int[N][N][4];
        for(int i = 0; i < N; i++) {
            String s = sc.next();
            for(int j = 0; j < N; j++) {
                map[i][j] = s.charAt(j);
                Arrays.fill(visited[i][j], Integer.MAX_VALUE);
                // 시작 위치, 끝 위치
                if(map[i][j] == '#') {
                    if(sx == -1) {
                        sy = i; sx = j;
                    } else {
                        ey = i; ex = j;
                    }
                }
            }
        }
        Queue<int[]> q = new LinkedList<>();
        // 4방향으로 시작
        for(int d = 0; d < 4; d++)
            q.add(new int[]{sy, sx, d, 0}); // y, x, 방향, 거울 개수
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            // 끝 위치 도달했음
            if(cur[0] == ey && cur[1] == ex) {
                ret = Math.min(ret, cur[3]);
                continue;
            }
            // 가던 방향으로 직진
            int ny = cur[0] + dy[cur[2]], nx = cur[1] + dx[cur[2]];
            if(ny < 0 || nx < 0 || ny >= N || nx >= N || map[ny][nx] == '*') continue;
            // 거울 설치 위치 만났음
            if(map[ny][nx] == '!') {
                // 거울 두 가지 넣어 봄
                for(int i = 0 ; i < 2; i++) {
                    int nd = mirror[i][cur[2]], nc = visited[ny][nx][nd];
                    // 이전꺼보다 거울 개수 적으면 갱신
                    if(nc <= cur[3] + 1) continue;
                    nc = cur[3] + 1;
                    q.add(new int[]{ny, nx, nd, nc});
                }
            }
            // 설치 안하고 그냥 감
            if(visited[ny][nx][cur[2]] > cur[3]) {
                visited[ny][nx][cur[2]] = cur[3];
                q.add(new int[]{ny, nx, cur[2], cur[3]});
            }
        }
        System.out.println(ret);
    }
}
