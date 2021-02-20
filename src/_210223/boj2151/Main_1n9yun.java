package _210223.boj2151;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_1n9yun {
    static class Item{
//        세로 가로
        int r, c, dir, count;

        public Item(int r, int c, int dir, int count) {
            this.r = r;
            this.c = c;
            this.dir = dir;
            this.count = count;
        }
    }
    static int delta[][] = {{-1,0}, {1,0}, {0,-1}, {0,1}};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        char[][] map = new char[n][];

//        시작하는 문 위치는 상관없음
        int doorRow = 0, doorCol = 0;
        for(int i=0;i<n;i++){
            map[i] = sc.next().toCharArray();
            for(int j=0;j<map[i].length;j++){
                if(map[i][j] == '#') {
                    doorRow = i;
                    doorCol = j;
                }
            }
        }

//        세로 방향인지 가로방향에서 왔는지만 알면 되지만 그게 0, 2로 정했으니까 3
        boolean[][][] check = new boolean[n][n][3];
        check[doorRow][doorCol][0] = true;
        check[doorRow][doorCol][2] = true;
        Queue<Item> q = new LinkedList<>();
        q.add(new Item(doorRow, doorCol, 0, 0));
        q.add(new Item(doorRow, doorCol, 2, 0));

        while(!q.isEmpty()){
            Item item = q.poll();

            if(map[item.r][item.c] == '#' && !(item.r == doorRow && item.c == doorCol)){
                System.out.println(item.count - 1);
                return;
            }

//            반대되는 방향으로 이동
            int nDir = item.dir * -1 + 2;
            for(int dir=nDir;dir<=nDir+1;dir++){
                int nRow = item.r + delta[dir][0];
                int nCol = item.c + delta[dir][1];

                while(true){
//                    거울이나 문을 만날 때까지 그 방향으로 이동
                    if(!(0<=nRow && nRow<n && 0<=nCol && nCol<n) || check[nRow][nCol][nDir] || map[nRow][nCol] == '*') break;
                    if(map[nRow][nCol] == '!' || map[nRow][nCol] == '#'){
                        check[nRow][nCol][nDir] = true;
                        q.add(new Item(nRow, nCol, nDir, item.count + 1));
                    }
                    nRow += delta[dir][0];
                    nCol += delta[dir][1];
                }
            }
        }
    }
}
