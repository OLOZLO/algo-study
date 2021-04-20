package _210413.boj17143;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
    https://ddb8036631.github.io/알고리즘%20풀이/백준_17143_낚시왕/

    1. 결과 : 성공
    2. 시간복잡도 : O(C * R * 10^4) ? 맞는지는 모르겠습니다.
        - 가장 바깥 쪽, 열의 크기 C만큼 돔. -> C
            - 상어를 한 마리 고름. 최악의 경우 R. -> R
            - 상어를 이동시킴.
                - 상어 최대 10^4마리 존재할 수 있음. 각각 최대 200만큼 이동할 수 있음. -> 10^4 * 200
                - 이동된 상어를 저장해 둔 해시맵을 돌며 배열을 갱신시킴. 상어 최대 10^4마리. -> 10^4

    3. 후기
        - 아이디어는 블로그에 적어놨습니다.
        - 주석은 블로그에 열심히 달아놨는데, 그걸로 봐주시면 안될까요? 여기다 달면 너무 더러워질 것 같아요.
        - 시간 초과를 받고나서 상어를 이동시키는 부분을 고쳐야겠다고 생각은 했지만 어떻게 바꿔야 할 지 몰라서 인터넷좀 찾아봤습니다. 배열 크기로 사이클을 계산해내는 생각은 못 할 것 같네요..
        - 코드가 많이 길지만, 일단 풀었고 나름 정리좀 했다는 데에 만족하겠습니다.
        - 삼성 문제라던데, 이런 구현 문제 나오면 시간 안에 못 풀 것 같습니다.
*/

public class Main_ms {
    static int R, C, M;
    static Shark[][] map;
    static Shark[] sharks;
    static HashMap<Point, Shark> movedSharks;
    static int[] dx = {0, -1, 1, 0, 0}, dy = {0, 0, 0, 1, -1};
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new Shark[R + 1][C + 1];
        sharks = new Shark[M + 1];
        movedSharks = new HashMap<>();

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int r, c, s, d, z;
            r = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            s = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());
            z = Integer.parseInt(st.nextToken());

            s = (d == 1 || d == 2) ? s % (2 * (R - 1)) : s % (2 * (C - 1));

            map[r][c] = new Shark(i, r, c, s, d, z);
            sharks[i] = new Shark(i, r, c, s, d, z);
        }

        for (int j = 1; j <= C; j++) {
            fishing(j);
            move();
        }

        System.out.println(answer);
    }

    static void fishing(int now) {
        Shark target = null;

        for (int i = 1; i <= R; i++) {
            if (map[i][now] == null) continue;

            target = map[i][now];
            break;
        }

        if (target == null) return;

        answer += target.z;

        sharks[target.idx] = null;
        map[target.x][target.y] = null;
    }

    static void move() {
        movedSharks.clear();

        for (int i = 1; i <= M; i++) {
            Shark target = sharks[i];

            if (target == null) continue;

            int[] movedPosDir = getMovedPosDir(target);
            Shark movedShark = new Shark(target.idx, movedPosDir[0], movedPosDir[1], target.s, movedPosDir[2], target.z);
            Point key = new Point(movedPosDir[0], movedPosDir[1]);

            if (movedSharks.containsKey(key)) {
                Shark origin = movedSharks.get(key);

                if (origin.z < movedShark.z) {
                    movedSharks.put(key, movedShark);
                    sharks[origin.idx] = null;
                } else {
                    sharks[movedShark.idx] = null;
                }
            } else {
                movedSharks.put(key, movedShark);
            }

            map[target.x][target.y] = null;
        }

        for (Map.Entry<Point, Shark> entry : movedSharks.entrySet()) {
            Point key = entry.getKey();
            Shark val = entry.getValue();

            map[key.x][key.y] = val;
            sharks[val.idx] = val;
        }
    }

    static int[] getMovedPosDir(Shark shark) {
        int speed = shark.s;
        int x = shark.x, y = shark.y;
        int dir = shark.d;

        while (speed-- > 0) {
            int nx = x + dx[dir], ny = y + dy[dir];

            if (!inRange(nx, ny)) {
                dir = changeDir(dir);
                x = x + dx[dir];
                y = y + dy[dir];
            } else {
                x = nx;
                y = ny;
            }
        }

        return new int[]{x, y, dir};
    }

    static int changeDir(int dir) {
        if (dir == 1) return 2;
        else if (dir == 2) return 1;
        else if (dir == 3) return 4;
        else return 3;
    }

    static boolean inRange(int x, int y) {
        return x >= 1 && x <= R && y >= 1 && y <= C;
    }

    static class Shark {
        int idx;
        int x, y, s, d, z;

        public Shark(int idx, int x, int y, int s, int d, int z) {
            this.idx = idx;
            this.x = x;
            this.y = y;
            this.s = s;
            this.d = d;
            this.z = z;
        }
    }
}
