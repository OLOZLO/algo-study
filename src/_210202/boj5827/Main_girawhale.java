package _210202.boj5827;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main_girawhale {
    static int[] dx = {1, -1}, gravity = {1, -1}, doctor;
    static int N, M;
    static boolean[][] map;
    static boolean ck; // 박사랑 만났는가?

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();

        map = new boolean[N][M];
        int[] captain = null;

        for (int i = 0; i < N; i++) {
            char[] in = sc.next().toCharArray();
            for (int j = 0; j < M; j++) {
                switch (in[j]) {
                    case 'D':
                        doctor = new int[]{i, j};
                        map[i][j] = true;
                        break;
                    case 'C':
                        captain = new int[]{i, j};
                        map[i][j] = true;
                        break;
                    default:
                        map[i][j] = in[j] == '.';
                }
            }
        }

        int[][] dist = new int[N][M]; // 중력을 뒤집은 횟수를 저장할 배열
        for (int i = 0; i < N; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
        // y좌표, x좌표, 중력 방향, 중력 변화수
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[3]));

        captain = fallIntoCell(captain[0], captain[1], 0); // 시작에 공중에 떠있을 수 있으므로 한 번 떨어트려봄
        if (captain != null) queue.add(new int[]{captain[0], captain[1], 0, 0}); // 시작하자마자 외부로 빨려가면 null이니까 해당 경우 처리

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            if (dist[cur[0]][cur[1]] <= cur[3]) continue; // 이미 중력 방향 전환이 더 적게 일어난 경우가 있으므로 continue
            dist[cur[0]][cur[1]] = cur[3]; // 없다면 저장


            for (int k = 0; k < 2; k++) {
                int[] n = fallIntoCell(cur[0], cur[1] + dx[k], cur[2]); // 좌우로만 한 칸 움직인 다음 떨궈봄
                if (ck) { // 만났으면 return
                    System.out.println(cur[3]);
                    System.exit(0);
                }
                if (n == null || dist[n[0]][n[1]] <= cur[3]) continue; // 우주로 갔거나 이미 작은 횟수로 방문하면 continue

                queue.add(new int[]{n[0], n[1], cur[2], cur[3]}); //아니면 추가
            }


            int[] n = fallIntoCell(cur[0], cur[1], 1 - cur[2]); // 중력 index가 0, 1 두개니까 1 - 중력index하면 다른 중력 나옴
            if (ck) {
                System.out.println(cur[3] + 1);
                System.exit(0);
            }
            if (n == null || dist[n[0]][n[1]] <= cur[3] + 1) continue;

            queue.add(new int[]{n[0], n[1], 1 - cur[2], cur[3] + 1}); //위와 동일
        }

        System.out.println(-1); // Queue 빌 때까지 박사랑 못만났으니까 -1
    }

    static int[] fallIntoCell(int y, int x, int idx) {
        if (y < 0 || x < 0 || y >= N || x >= M || !map[y][x]) return null; // 이미 벗어난 좌표가 들어오면 null반환

        while (true) {
            if (y == doctor[0] && x == doctor[1]) { // 떨어지면서 박사랑 만났는지 체크
                ck = true;
                return null;
            }
            y += gravity[idx]; // 아니라면 계속 추락

            if (y < 0 || y >= N) return null; // 만약 우주밖으로 나간다면 null을 반환
            if (!map[y][x]) return new int[]{y - gravity[idx], x}; // 바닥이랑 만났을 경우 바닥 위의 좌표를 반환
        }
    }
}
