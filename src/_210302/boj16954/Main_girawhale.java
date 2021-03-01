package _210302.boj16954;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_girawhale {
    public static void main(String[] args) {
        char[] newLine = ".".repeat(8).toCharArray(); // 그냥.. 귀찮아서... 이렇게 했어여...
        int[] dy = {-1, -1, -1, 0, 0, 0, 1, 1, 1}, dx = {-1, 0, 1, -1, 0, 1, -1, 0, 1};

        Scanner sc = new Scanner(System.in);
        char[][] map = new char[8][8];
        for (int i = 0; i < 8; i++)
            map[i] = sc.next().toCharArray();

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visit;
        queue.add(new int[]{7, 0});

        while (!queue.isEmpty()) {
            int size = queue.size();
            visit = new boolean[8][8]; // 같은 이동 횟수마다 방문처리

            while (size-- > 0) {
                int[] cur = queue.poll();

                if (map[cur[0]][cur[1]] == '#') continue;
                if (cur[0] == 0 && cur[1] == 7) { // 이것도 해보고 0줄오면 바로 반환도 해봤는데 얼마 차이 안나서 냅둠
                    System.out.println(1);
                    return;
                }

                for (int k = 0; k < 9; k++) {
                    int ny = cur[0] + dy[k], nx = cur[1] + dx[k];
                    if (ny < 0 || nx < 0 || ny >= 8 || nx >= 8 || visit[ny][nx] || map[ny][nx] == '#') continue;
                    visit[ny][nx] = true;
                    queue.add(new int[]{ny, nx});
                }
            }

            for (int i = 7; i > 0; i--) // 벽 이동 부분.
                map[i] = map[i - 1]; // 위에 벽을 그대로 가져오기 위해 그냥 주소값만 바꿔치기
            map[0] = newLine; // 값만 참조하면 되니까 빈 라인 만들어 놓고 주소값 바꿔치기
        }
        System.out.println(0);
    }
}
