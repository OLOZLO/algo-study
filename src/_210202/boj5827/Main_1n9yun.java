package _210202.boj5827;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_1n9yun {
    static char[][] map;
    static int[][][] fallTo;
    static int[][] delta = {{-1,0},{1,0},{0,-1},{0,1}};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        map = new char[n][m];
        fallTo = new int[n][m][2];
        Pos start = null, target = null;
        for(int[][] sub : fallTo) for(int[] subsub : sub) Arrays.fill(subsub, -1);
        for(int i=0;i<n;i++) {
            map[i] = sc.next().toCharArray();

            for(int j=0;j<m;j++){
                if(map[i][j] == 'C') start = new Pos(i, j);
                else if(map[i][j] == 'D') target = new Pos(i, j);
            }
        }

        int upper = -1;
//        중력 방향
        for(int dir=1;dir>=0;dir--){
            int i, end, r_dir = dir * -1 + 1;
            if(dir == 1) {
                i = upper + 1; end = n;
            }else{
                i = n - 1; end = upper;
            }

            for(; i!=end; i+=delta[dir][0]){
                for(int j=0;j<m;j++){
                    if(map[i][j] == '#' || fallTo[i][j][r_dir] != -1) continue;

                    for(int r=i;r!=end;r+=delta[dir][0]){
                        if(map[r][j] == '#') break;

//                        중력을 뒤집으면 지금 떨어지는 방향과 반대로 이동할 것이므로!
//                        반대 방향에 기록
                        fallTo[r][j][r_dir] = i;
                    }
                }
            }
        }
        start.r = fallTo[start.r][start.c][1];

        Queue<Item> q = new LinkedList<>();

//        여기 dir은 떨어지는 방향
        q.add(new Item(start, 1, 0));
        int[][] check = new int[n][m];
        for(int[] sub : check) Arrays.fill(sub, Integer.MAX_VALUE);
        check[start.r][start.c] = 0;

        int answer = Integer.MAX_VALUE;
        while(!q.isEmpty()){
            Item item = q.poll();
            if(item.pos.r == 0 || item.pos.r == n-1) continue;
            if(item.pos.r == target.r && item.pos.c == target.c) answer = Math.min(answer, item.fliped);

//            좌우 이동
            for(int dir=2;dir<=3;dir++){
                int nCol = item.pos.c + delta[dir][1];
                if(!(0<=nCol && nCol<m)) continue;
                int fallenRow = fallTo[item.pos.r][nCol][item.dir];
                if(0<=fallenRow && fallenRow<n && map[fallenRow][nCol] != '#' && check[fallenRow][nCol] > item.fliped){
                    if(target.c == nCol && met(item.dir, item.pos.r, fallenRow, target.r)){
                        answer = Math.min(answer, item.fliped);
                    }
                    if(fallenRow != 0 && fallenRow != n-1) {
                        check[fallenRow][nCol] = item.fliped;
                        q.add(new Item(new Pos(fallenRow, nCol), item.dir, item.fliped));
                    }
                }
            }
//            flip
            int r_dir = item.dir * -1 + 1;
            int fallenRow = fallTo[item.pos.r][item.pos.c][r_dir];
            if(check[fallenRow][item.pos.c] > item.fliped + 1){
                if(target.c == item.pos.c && met(r_dir, item.pos.r, fallenRow, target.r)){
                    answer = Math.min(answer, item.fliped + 1);
                }
                if(fallenRow != 0 && fallenRow != n-1) {
                    check[fallenRow][item.pos.c] = item.fliped + 1;
                    q.add(new Item(new Pos(fallenRow, item.pos.c), r_dir, item.fliped + 1));
                }
            }
        }

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    static boolean met(int dir, int from, int fallenRow, int targetRow){
        if(dir == 1) return from <= targetRow && targetRow <= fallenRow;
        else return fallenRow <= targetRow && targetRow <= from;
    }
    static class Item{
        Pos pos;
        int dir;
        int fliped;

        public Item(Pos pos, int dir, int fliped) {
            this.pos = pos;
            this.dir = dir;
            this.fliped = fliped;
        }
    }
    static class Pos{
        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}

