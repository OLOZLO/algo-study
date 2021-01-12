package _210112.boj18500;

import java.util.*;

public class Main_1n9yun {
    static class Pair{
        int r, c;

        public Pair(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    static class Cluster{
        List<Integer>[] list;
        int min;

        public Cluster(List<Integer>[] list, int min) {
            this.list = list;
            this.min = min;
        }
    }
    static char[][] map;
    static int[][] delta = {{-1,0},{1,0},{0,-1},{0,1}};
    static int r, c;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        r = sc.nextInt();
        c = sc.nextInt();
        map = new char[r+1][c+1];
        for(int i=1;i<=r;i++){
            char[] input = sc.next().toCharArray();
            for(int j=1;j<=c;j++){
                map[i][j] = input[j-1];
            }
        }

        int n = sc.nextInt();
        int shootDir = 3;
        int nextDir = 2;
        for(int i=0;i<n;i++){
            int shootRow = (r+1) - sc.nextInt();
            for(int shootCol=shootDir == 2 ? c : 1;1<=shootCol && shootCol<=c;shootCol+=delta[shootDir][1]){
                if(map[shootRow][shootCol] == '.') continue;

                map[shootRow][shootCol] = '.';

                for(int dir=0;dir<delta.length;dir++){
                    if(dir == nextDir) continue;

                    int nRow = shootRow + delta[dir][0];
                    int nCol = shootCol + delta[dir][1];
                    if(!(1<=nRow && nRow<=r && 1<=nCol && nCol<=c) || map[nRow][nCol] == '.') continue;

                    boolean[][] check = new boolean[r+1][c+1];
                    check[shootRow][shootCol] = true;
                    Cluster cluster = getCluster(shootRow + delta[dir][0], shootCol + delta[dir][1], check);

                    if(cluster == null) continue;

                    List<Integer>[] list = cluster.list;
                    for(int row=r;row>=1;row--){
                        for(int col : list[row]){
                            map[row][col] = '.';
                            map[row + cluster.min][col] = 'x';
                        }
                    }
                    break;
                }
                break;
            }
            int temp = shootDir;
            shootDir = nextDir;
            nextDir = temp;
        }

        for(int i=1;i<=r;i++){
            for(int j=1;j<=c;j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    static Cluster getCluster(int row, int col, boolean[][] check){
        List<Integer>[] list = new ArrayList[r+1];
        for(int i=0;i<list.length;i++) list[i] = new ArrayList<>();

        boolean fall = true;

//        (row, col)과 이어진 클러스터에서 미네랄들 좌표 리턴
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(row, col));
        check[row][col] = true;

        while(!q.isEmpty()){
            Pair p = q.poll();
            list[p.r].add(p.c);

            for(int[] dir : delta){
                int nRow = p.r + dir[0];
                int nCol = p.c + dir[1];
                if(1<=nRow && nRow<=r && 1<=nCol && nCol<=c && map[nRow][nCol] == 'x' && !check[nRow][nCol]){
                    check[nRow][nCol] = true;
                    q.add(new Pair(nRow, nCol));
                    if(nRow == r) fall = false;
                }
            }
        }
        if(!fall) return null;

        int fallCount = 101;
        for(int i=1;i<=r;i++){
            for(int j=0;j<list[i].size();j++){
                int C = list[i].get(j);
                int count = 0;
                for(int h=i+1;h<=r;h++){
                    if(map[h][C] == '.') count++;
                    else if(map[h][C] == 'x'){
                        if(check[h][C]) count = 101;
                        break;
                    }
                }
                fallCount = Math.min(fallCount, count);
            }
        }
        return new Cluster(list, fallCount);
    }
}