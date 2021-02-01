package _210202.boj17837;

import java.util.Scanner;

public class Main_Taekyung2 {

    // y값, x값, 방향
    static class Piece {
        int y, x, d;

        Piece(int y, int x, int d) {
            this.y = y;
            this.x = x;
            this.d = d;
        }
    }
    static int N, K;
    static int[][] map;
    static StringBuilder[][] stack; // 체스말 쌓아놓는 용도
    static int[] dy = {0, 0, 0, -1, 1}, dx = {0, 1, -1, 0, 0}; // 방향
    static Piece[] pieces;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        pieces = new Piece[K + 1];
        map = new int[N + 1][N + 1];
        stack = new StringBuilder[N + 1][N + 1];
        for(int i = 1; i <= N; i++)
            for(int j = 1; j <= N; j++) {
                map[i][j] = sc.nextInt();
                stack[i][j] = new StringBuilder();
            }

        for(int i = 0; i < K; i++) {
            int y = sc.nextInt();
            int x = sc.nextInt();
            int d = sc.nextInt();
            pieces[i] = new Piece(y, x, d);
            stack[y][x].append((char)('A' + i)); // A부터 순서대로 위치시킨다
        }
        int cnt = 0;
        while(cnt++ < 1000) { // 1000번 돌리면 탈출
            if(simulation()) break;
        }
        System.out.println(cnt > 1000 ? -1 : cnt);
    }

    public static boolean simulation() {
        for(int i = 0; i < K; i++) {
            Piece cur = pieces[i];
            int ny = cur.y + dy[cur.d];
            int nx = cur.x + dx[cur.d];
            int idx = stack[cur.y][cur.x].indexOf(Character.toString((char)('A' + i)));
            String s = stack[cur.y][cur.x].substring(idx);
            // 범위 벗어나거나 파란색
            if(ny <= 0 || ny > N || nx <= 0 || nx > N || map[ny][nx] == 2) {
                // 방향 반대로하고 한 칸 이동
                cur.d = (cur.d % 2 == 0) ? --cur.d : ++cur.d;
                ny = cur.y + dy[cur.d];
                nx = cur.x + dx[cur.d];
                // 이동 시킨 곳이 범위 벗어나거나 파란색이면 스킵
                if(ny <= 0 || ny > N || nx <= 0 || nx > N || map[ny][nx] == 2) continue;
            }
            // 빨간색이면 위치 반대로 해서 다음 칸에 쌓음
            if(map[ny][nx] == 1) stack[ny][nx].append(new StringBuilder(s).reverse().toString());
            else stack[ny][nx].append(s);
            stack[cur.y][cur.x].delete(idx, stack[cur.y][cur.x].length());
            // 옮기려던거 위에꺼도 같이 옮김
            for(int j = 0; j < s.length(); j++) {
                pieces[s.charAt(j) - 'A'].y = ny;
                pieces[s.charAt(j) - 'A'].x = nx;
            }
            // 4개 이상이면 true
            if(stack[ny][nx].length() >= 4) return true;
        }
        return false;
    }
}