package _210202.boj5827;

import java.util.*;

public class Main_Taekyung2 {

    static class Point {
        int y, x;
        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
    static int N, M;
    static Point C, D; static char[][] map;
    static int[][] count;
    static Queue<Point> q = new LinkedList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        map = new char[N][M];
        count = new int[N][M]; // (N, M)에 중력을 몇 번 뒤집어서 갈 수 있는지 체크
        for(int i = 0; i < N; i++) {
            String s = sc.next();
            Arrays.fill(count[i], -1); // -1로 초기화 해놓음
            for(int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j);
                if(map[i][j] == 'C')
                    C = new Point(i, j);
                else if(map[i][j] == 'D')
                    D = new Point(i, j);
            }
        }
        C = down(C.y, C.x, 1); // 처음에 선장을 미리 바닥으로 내린다
        if(check(C, 0)) { // 중력을 뒤집지 않고 갈 수 있는 곳 체크, 성공했으면 닥터 구한거
            System.out.println(count[D.y][D.x]);
            return;
        }
        while (!q.isEmpty()) { // bfs 돌면서 닥터 찾았는지 확인
            Point cur = q.poll();
            if (check(down(cur.y, cur.x, (count[cur.y][cur.x] % 2 == 0) ? -1 : 1), count[cur.y][cur.x] + 1))
                break; // 중력 방향 뒤집고, 카운트 늘려서 닥터 구할 수 있는지 확인, 구했으면 종료
        }
        System.out.println(count[D.y][D.x]); // -1로 초기화해놔서 안구해졌으면 -1 출력, 구했으면 정답 출력
    }

    public static boolean check(Point p, int cnt) {
        if(p == null || count[p.y][p.x] != -1) return false; // 체크할 셀이 null이거나 이미 방문했으면 false
        count[p.y][p.x] = cnt; // 중력 바꾼 횟수 기록
        if(map[p.y][p.x] == 'D') return true;
        q.add(p); // 큐에 넣어서 bfs 돌림
        int d = (cnt % 2 == 0) ? 1 : -1; // 짝수번 바꿨으면 처음 방향, 홀수번이면 반대 방향
        return (check(move(p.y, p.x, -1, d), cnt) || check(move(p.y, p.x, 1, d), cnt)); // 현재 위치 왼쪽이랑 오른쪽 체크
    }

    public static Point move(int py, int px, int x, int d) {
        px += x;
        if (px < 0 || px >= M || map[py][px] == '#') return null; // 옆으로 못가면 null 리턴
        return down(py, px, d); // 왼쪽 or 오른쪽으로 한칸 가서 중력 방향으로 내림
    }

    public static Point down(int py, int px, int d) {
        while(true) { // 밑으로 내리는 작업, 순서 중요함
            if(map[py][px] == 'D') break;
            if(py + d < 0 || py + d >= N) return null;
            if(map[py + d][px] == '#') break;
            py += d;
        }
        return new Point(py, px); // 밑으로 내린 위치 반환
    }
}