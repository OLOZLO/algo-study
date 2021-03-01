package _210302.boj16954;

import java.util.*;

public class Main_Taekyung2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] map = new char[8][8];
        int ret = 0;
        int[] dy = {0, -1, -1, 0, 1, 1, 1, 0, -1}, dx = {0, 0, 1, 1, 1, 0, -1, -1, -1};
        for(int i = 0; i < 8; i++)
            map[i] = sc.next().toCharArray();
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{7, 0, 8});
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            if(cur[0] == 0) ret = 1;
            for (int d = 0; d < 9; d++) {
                int ny = cur[0] + dy[d], nx = cur[1] + dx[d];
                if (ny < 0 || nx < 0 || ny >= cur[2] || nx >= 8 || map[ny][nx] == '#') continue;
                if(ny > 0 && map[--ny][nx] == '.')
                    q.add(new int[]{ny, nx, cur[2] - 1});
            }
        }
        System.out.println(ret);
    }
}