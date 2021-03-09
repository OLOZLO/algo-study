package _210309.boj16946;

import java.util.*;

// 아이디어
//      1. 빈 곳(0)들을 bfs로 그룹핑 시켜, 그룹번호를 2차원 배열에 저장해둠.
//          - 아래는 입력예제 2를 그룹핑시킨 결과.
//              00110
//              22000
//              20304
//              05060
//      2. 각 그룹 번호가 몇 번 등장하는지 1차원 배열에 기억해둠.
//          - 아래는 입력예제 2의 그룹 번호 개수를 저장한 배열.
//              0231111 (1이 두 번, 2가 세 번, ...)
//      3. 이제 원본 배열(map)을 돌며, 벽일 경우 상하좌우에 존재하는 그룹 번호로 해당 그룹 번호 개수를 다 누적시킴.
//          - 입력예제 2의 (0,1) 좌표에 존재하는 벽은 아래와 오른쪽에 각각 그룹번호 2와 1이 존재.
//              > 그룹번호 2의 개수는 3, 그룹번호 1의 개수는 2.
//              > 벽도 부숴야 하므로, 1 + 3 + 2 = 6 이 해당 좌표에서의 이동할 수 있는 칸의 개수가 된다.
public class Main_ms {
    static int N, M;
    static int[][] map;
    static boolean[][] visit;
    static int[][] group;
    static int groupId;
    static int[] groupCnt;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        map = new int[N][M];
        visit = new boolean[N][M];
        group = new int[N][M];
        groupId = 1;

        // 빈 공간(0)과 벽(1)을 입력받아 map에 저장.
        for (int i = 0; i < N; i++) {
            String s = sc.next();
            for (int j = 0; j < M; j++)
                map[i][j] = s.charAt(j) - '0';
        }

        // 맵을 돌면서, 빈 공간(0)이면 bfs를 수행.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (visit[i][j] || map[i][j] == 1) continue;
                bfs(i, j);
                groupId++;
            }
        }

        groupCnt = new int[groupId]; // 각 그룹들이 몇 번 등장하는지 기억하기 위한 1차원 배열.

        // 맵을 돌며, 각 그룹이 등장할 때마다 배열 요소를 증가시켜, 맵에서의 그룹 개수를 저장.
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                groupCnt[group[i][j]]++;

        StringBuilder answer = new StringBuilder(); // 정답은 StringBuilder 객체에 누적시킴.

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0) answer.append(0);
                else answer.append(set(i, j) % 10);
            }
            answer.append("\n");
        }

        System.out.println(answer);
    }

    // 빈 공간(0)에 대한 bfs 탐색.
    static void bfs(int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        visit[x][y] = true;
        queue.add(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] now = queue.poll();

            group[now[0]][now[1]] = groupId; // group이라는 2차원 배열에 그룹 번호를 마킹함.

            for (int d = 0; d < 4; d++) {
                int nx = now[0] + dx[d];
                int ny = now[1] + dy[d];

                if (!inRange(nx, ny) || visit[nx][ny] || map[nx][ny] == 1) continue;

                visit[nx][ny] = true;
                queue.add(new int[]{nx, ny});
            }
        }
    }

    static int set(int x, int y) {
        HashSet<Integer> set = new HashSet<>(); // 중복 제거 위해 "set"에 그룹 번호를 넣음.

        // 현 좌표에서 "상하좌우"만을 검색.
        // 네 위치 각각에 그룹이 존재하면, set에 그룹 번호를 담아둔다.
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (!inRange(nx, ny) || group[nx][ny] == 0) continue;
            set.add(group[nx][ny]);
        }

        // set에 담긴 그룹번호를 인덱스로 해서 그룹 개수 배열(groupCnt)의 요소를 누적시킴.
        for (int groupId : set)
            map[x][y] += groupCnt[groupId];

        return map[x][y];
    }

    static boolean inRange(int x, int y) {
        if (x < 0 || x > N - 1 || y < 0 || y > M - 1) return false;
        return true;
    }
}
