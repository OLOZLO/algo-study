package _210302.boj16954;

import java.util.*;

public class Main_Taekyung2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] map = new char[8][8];
        int ret = 0;
        // 상하좌우 대각선 제자리 9방향
        int[] dy = {0, -1, -1, 0, 1, 1, 1, 0, -1}, dx = {0, 0, 1, 1, 1, 0, -1, -1, -1};
        for(int i = 0; i < 8; i++)
            map[i] = sc.next().toCharArray();
        Queue<int[]> q = new LinkedList<>();
        // y, x, 땅 높이 -> 벽을 내리는게 아니고 땅을 올렸음
        q.add(new int[]{7, 0, 8});
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            // 제일 윗칸 올라갔으면 (0, 7)까지 갈 수 있음
            if(cur[0] == 0) ret = 1;
            for (int d = 0; d < 9; d++) {
                int ny = cur[0] + dy[d], nx = cur[1] + dx[d];
                if (ny < 0 || nx < 0 || ny >= cur[2] || nx >= 8 || map[ny][nx] == '#') continue;
                // 땅 올릴 때 #이면 못 감
                if(ny > 0 && map[--ny][nx] == '.')
                    q.add(new int[]{ny, nx, cur[2] - 1});
            }
        }
        System.out.println(ret);
    }
}