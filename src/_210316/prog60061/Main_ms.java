package _210316.prog60061;

import java.util.ArrayList;

// https://ddb8036631.github.io/알고리즘%20풀이/프로그래머스_카카오2020_기둥과보/

public class Main_ms {
    public static ArrayList<int[]> solution(int n, int[][] build_frame) {
        Point[][] map = new Point[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                map[i][j] = new Point(false, false); // 만든 클래스 타입의 배열을 쓰니까, 일단 기둥 보 둘 다 없다고 설정한 채 객체를 생성해두자.
            }
        }

        for (int i = 0; i < build_frame.length; i++) {
            // x, y, a, b 순서로 받아두자.
            int x = build_frame[i][0], y = build_frame[i][1], a = build_frame[i][2], b = build_frame[i][3];

            // b가 0이면 삭제를 의미
            // 삭제인 경우.
            if (b == 0) {
                // a가 0이면 기둥을 의미.
                // 입력으로 기둥이 들어오면, 먼저 해당 자리의 기둥을 삭제한 채(false로 설정) 모든 건축물들이 문제의 조건을 만족시키는지 확인.
                // 조건 만족 한다면 false 처리해둔채 다음 작업으로 넘어가고,
                // 조건 만족 안한다면 true 로 설정해 다시 설치해두자.
                if (a == 0) {
                    map[y][x].gidung = false;
                    if (!canRemove(n, map))
                        map[y][x].gidung = true;
                }

                // a가 1이면 보를 의미.
                // 입력으로 보가 들어오면, 먼저 해당 자리의 보를 삭제한 채(false 설정) 모든 건축물들이 문제의 조건을 만족시키는지 확인.
                // 조건 만족 한다면 false 처리해둔채 다음 작업으로 넘어가고,
                // 조건 만족 안한다면 true 로 설정해 다시 설치해두자.
                else {
                    map[y][x].bo = false;
                    if (!canRemove(n, map))
                        map[y][x].bo = true;
                }
            }

            // b가 1이면 설치를 의미
            // 설치인 경우.
            else {
                // 입력으로 기둥이 들어오면, 기둥 설치가 가능하면 설치.
                if (a == 0 && canBuildGidung(y, x, map)) {
                    map[y][x].gidung = true;
                }
                // 입력으로 보가 들어오면, 보 설치가 가능하면 설치.
                else if (a == 1 && canBuildBo(n, y, x, map)) {
                    map[y][x].bo = true;
                }
            }
        }

        ArrayList<int[]> answer = new ArrayList<>();
        // 아래 for문 자체로 이미 x->y->a 순서대로 각각 오름차순으로의 정렬 효과가 있다. 따로 정렬할 필요가 없다.
        for (int j = 0; j <= n; j++) {
            for (int i = 0; i <= n; i++) {
                if (map[i][j].gidung) answer.add(new int[]{j, i, 0});
                if (map[i][j].bo) answer.add(new int[]{j, i, 1});
            }
        }

        return answer;
    }

    // canBuildGidung() : 해당 (x, y) 좌표에 기둥을 설치할 수 있는지 판단. 설치 가능 : true, 불가능 : false 리턴.
    static boolean canBuildGidung(int x, int y, Point[][] map) {
        // return 문 왼쪽부터 순서대로
        // 1. 바닥 위에 있는 경우.
        // 2. 왼쪽 끝 부분이 보인 경우.
        // 3. 오른쪽 끝 부분이 보인 경우.
        // 4. 다른 기둥 위에 있는 경우.
        return x == 0 || (y - 1 >= 0 && map[x][y - 1].bo) || map[x][y].bo || (x - 1 >= 0 && map[x - 1][y].gidung);
    }

    // canBuildBo() : 해당 (x, y) 좌표에 보를 설치할 수 있는지 판단. 설치 가능 : true, 불가능 : false 리턴.
    static boolean canBuildBo(int n, int x, int y, Point[][] map) {
        // return 문 왼쪽부터 순서대로
        // 1. 왼쪽 끝 부분이 기둥 위인 경우.
        // 2. 오른쪽 끝 부분이 기둥 위인 경우.
        // 3. 양쪽 끝 부분이 보인 경우.
        return (x - 1 >= 0 && map[x - 1][y].gidung) || (x - 1 >= 0 && y + 1 <= n && map[x - 1][y + 1].gidung) || (y - 1 >= 0 && y + 1 <= n && map[x][y - 1].bo && map[x][y + 1].bo);
    }

    // canRemove() : 지울 수 있는지 확인하는 메소드.
    // 이미 main에서 해당 건축물을 "지운채" 이 메소드가 실행되므로,
    // 우리는 각 좌표에 건축물이 존재하는데? 그 조건이 모순이 되는 경우를 찾으면된다.
    // 예를 들어, 기둥이 존재하는데, 기둥을 설치할 수 없는 상황 !canBuildGidung() 이라면 "삭제할 수 없다고 판단"할 수 있다.
    static boolean canRemove(int n, Point[][] map) {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (map[i][j].gidung && !canBuildGidung(i, j, map)) return false;
                if (map[i][j].bo && !canBuildBo(n, i, j, map)) return false;
            }
        }

        return true;
    }

    static class Point {
        boolean gidung, bo;

        public Point(boolean gidung, boolean bo) {
            this.gidung = gidung;
            this.bo = bo;
        }
    }

    public static void main(String[] args) {
        solution(5, new int[][]{{1, 0, 0, 1}, {1, 1, 1, 1}, {2, 1, 0, 1}, {2, 2, 1, 1}, {5, 0, 0, 1}, {5, 1, 0, 1}, {4, 2, 1, 1}, {3, 2, 1, 1}});
//        solution(5, new int[][]{{0, 0, 0, 1}, {2, 0, 0, 1}, {4, 0, 0, 1}, {0, 1, 1, 1}, {1, 1, 1, 1}, {2, 1, 1, 1}, {3, 1, 1, 1}, {2, 0, 0, 0}, {1, 1, 1, 0}, {2, 2, 0, 1}});
    }
}
