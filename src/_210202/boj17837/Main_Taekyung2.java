package _210202.boj17837;

import java.util.Scanner;

public class Main_Taekyung2 {

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
    static StringBuilder[][] stack;
    static int[] dy = {0, 0, 0, -1, 1}, dx = {0, 1, -1, 0, 0};
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
            stack[y][x].append((char)('A' + i));
        }
        int cnt = 0;
        while(cnt++ < 1000) {
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
            if(ny <= 0 || ny > N || nx <= 0 || nx > N || map[ny][nx] == 2) {
                cur.d = (cur.d % 2 == 0) ? --cur.d : ++cur.d;
                ny = cur.y + dy[cur.d];
                nx = cur.x + dx[cur.d];
                if(ny <= 0 || ny > N || nx <= 0 || nx > N || map[ny][nx] == 2) continue;
            }
            if(map[ny][nx] == 1) {
                stack[ny][nx].append(new StringBuilder(s).reverse().toString());
                stack[cur.y][cur.x].delete(idx, stack[cur.y][cur.x].length());
            }
            else {
                stack[ny][nx].append(s);
                stack[cur.y][cur.x].delete(idx, stack[cur.y][cur.x].length());
            }
            for(int j = 0; j < s.length(); j++) {
                pieces[s.charAt(j) - 'A'].y = ny;
                pieces[s.charAt(j) - 'A'].x = nx;
            }
            if(stack[ny][nx].length() >= 4) return true;
        }
        return false;
    }
}