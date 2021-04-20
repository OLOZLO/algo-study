package _210413.boj17143;

import java.util.Scanner;

public class Main_Taekyung2 {
    /**
     * [골드2] 낚시왕
     * 1. 결과 : 실패 ( 시간 부족 )
     * 2. 시간복잡도 : O(C * M * s)
     * 3. 아이디어
     *      - 복잡한 구현, 중간 중간 실수해서 시간이 오래걸렸음
     *      - 기능별로 다 쪼개서 풀면서 디버깅해야 실수 없이 잘 풀 듯
     *      - 구현 자체는 시키는대로 하면 됨
     *      - 속도가 시간복잡도에 영향이 커서, 의미 없는 계산을 줄여주자
     *
     */

    static int R, C, M;
    // rd = 반대 방향
    // 바다
    static int[][] map;
    // 상어 배열
    static Shark[] sharks;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        M = sc.nextInt();

        map = new int[R + 1][C + 1];
        sharks = new Shark[M];

        for(int idx = 0; idx < M; idx++) {
            int r = sc.nextInt();
            int c = sc.nextInt();
            int s = sc.nextInt();
            int d = sc.nextInt();
            int z = sc.nextInt();

            // s 정규화, 시간 복잡도 중요
            s %= 2 * (d <= 2 ? R - 1 : C - 1);

            // 상어 크기 저장
            map[r][c] = z;
            sharks[idx] = new Shark(r, c, s, d, z);
        }

        System.out.println(simulation());
    }


    public static int simulation() {
        int sum = 0;

        // 어부 한 칸씩 이동
        for(int fishman = 1; fishman <= C; fishman++) {
            // 1. 상어 잡기
            for (int depth = 1; depth <= R; depth++) {
                // 상어 찾으면 없앰
                if (map[depth][fishman] > 0) {
                    sum += map[depth][fishman];
                    map[depth][fishman] = 0;
                    break;
                }
            }

            // 2. 상어 이동
            // 임시 배열 생성
            int[][] copy = new int[R + 1][C + 1];

            for(Shark shark : sharks) {
                // 이미 죽은 상어면 넘어 감
                if(shark.z == 0) continue;
                // 죽은 상어들 표시
                if(map[shark.y][shark.x] != shark.z) {
                    shark.z = 0;
                    continue;
                }

                // 상어 이동
                shark.move();

                // 제일 큰 상어 빼고 죽인다
                copy[shark.y][shark.x] = Math.max(shark.z, copy[shark.y][shark.x]);
            }

            map = copy;
        }
        return sum;
    }


    static class Shark {
        int y, x, s, d, z;
        static int[] dy = {0, -1, 1, 0, 0}, dx = {0, 0, 0, 1, -1}, rd = {0, 2, 1 ,4, 3};

        Shark(int y, int x, int s, int d, int z) {
            this.y = y;
            this.x = x;
            this.s = s;
            this.d = d;
            this.z = z;
        }

        void move() {
            int iter = s;

            // 속도 만큼 이동
            while(iter-- > 0) {
                // 격자 끝이면 방향 바꿔준다
                if((d == 1 && y == 1) || (d == 2 && y == R) || (d == 3 && x == C) || (d == 4 && x == 1))
                    d = rd[d];

                y += dy[d];
                x += dx[d];
            }
        }
    }
}