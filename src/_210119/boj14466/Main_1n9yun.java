package _210119.boj14466;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_1n9yun {
    static class Item{
        Pair pos;
        int crossed;

        public Item(Pair pos, int crossed) {
            this.pos = pos;
            this.crossed = crossed;
        }
    }
    static class Pair{
        int r, c;

        public Pair(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    static int[][] delta = {{-1,0},{1,0},{0,-1},{0,1}};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();
        int r = sc.nextInt();

        int[][] map = new int[n+1][n+1];
        int[][] position = new int[n+1][n+1];

        for(int i=0;i<r;i++){
            int lr = sc.nextInt();
            int lc = sc.nextInt();
            int rr = sc.nextInt();
            int rc = sc.nextInt();

//            상하좌우 길 비트마스킹
            map[lr][lc] |= 1 << getDirection(rr - lr, rc - lc);
//            반대쪽도요
            map[rr][rc] |= 1 << getDirection(lr - rr, lc - rc);
        }

        Pair[] cow = new Pair[k+1];
        for(int i=1;i<=k;i++){
//            소의 위치 기록해두기
            cow[i] = new Pair(sc.nextInt(), sc.nextInt());
            position[cow[i].r][cow[i].c] = i;
        }

        boolean[][] result = new boolean[k+1][k+1];
        int ans = 0;
        for(int i=1;i<=k-1;i++){
//            길 없이 가는 경우만 따지면 되는데 왜이렇게 했는지 모르겠다
//            귀찮으니까 수정은 하지 않겠다
            boolean[][][] check = new boolean[n+1][n+1][2];
            Queue<Item> q = new LinkedList<>();
            q.add(new Item(new Pair(cow[i].r, cow[i].c), 0));
            check[cow[i].r][cow[i].c][0] = true;

            while(!q.isEmpty()){
                Item item = q.poll();

                for(int dir=0;dir<delta.length;dir++){
                    int nRow = item.pos.r + delta[dir][0];
                    int nCol = item.pos.c + delta[dir][1];

//                    길로 건너려고 하거나 길을 건넌 적이 있는 소면 crossed 가 1이다
                    int crossed = (map[item.pos.r][item.pos.c] & (1<<dir)) != 0 ? 1 : item.crossed;

                    if(1<=nRow && nRow<=n && 1<=nCol && nCol<=n && !check[nRow][nCol][crossed]){
                        int target = position[nRow][nCol];
//                        가려는 위치에 소가 있고 길을 건넌 적이 없다면
                        if(target != 0 && crossed == 0) result[i][target] = true;

                        check[nRow][nCol][crossed] = true;
                        q.add(new Item(new Pair(nRow, nCol), crossed));
                    }
                }
            }
//            i번 소와 길 없이 만난 소를 제외한 소들 수 세기
            for(int j=i+1;j<=k;j++) if(!result[i][j]) ans++;
        }
        System.out.println(ans);
    }

//    상하좌우 방향 가져오기
    static int getDirection(int l, int r){
        if(l == 1) return 1;
        else if(l == -1) return 0;
        else if(r == 1) return 3;
        else return 2;
    }
}
