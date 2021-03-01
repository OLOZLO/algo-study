package _210302.boj16954;

import java.util.*;

public class Main_1n9yun {
    static class Item{
        int r, c;

        public Item(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    static int[][] delta = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Item> wall = new ArrayList<>();
        final int n = 8;
        char[][] map = new char[n][];

        for(int i=0;i<n;i++) {
            map[i] = sc.next().toCharArray();
            for(int j=0;j<n;j++){
                if(map[i][j] == '#') wall.add(new Item(i, j));
            }
        }
        
//        각 시간 별로 이동하지 않았던 칸만 이동하기 위해
        int[][] check = new int[n][n];
        for(int[] sub : check) Arrays.fill(sub, -2);
        Queue<Item> q = new LinkedList<>();
        q.add(new Item(n-1, 0));

//        가장 위의 벽의 바로 윗 줄에 도착하면 그냥 도착한 거나 다름이 없다
        int goal = -1;
        while(!q.isEmpty()){
            int size = q.size();
            while(size-- != 0) {
                Item item = q.poll();

                if (item.r <= goal) {
                    System.out.println(1);
                    return;
                }
                if (map[item.r][item.c] == '#') continue;

                q.add(item);
                for (int[] dir : delta) {
                    int nRow = item.r + dir[0];
                    int nCol = item.c + dir[1];

                    if (0 <= nRow && nRow < n && 0 <= nCol && nCol < n && map[nRow][nCol] == '.' && check[nRow][nCol] < goal) {
                        check[nRow][nCol] = goal;
                        q.add(new Item(nRow, nCol));
                    }
                }
            }
            
//            벽 내리기
            for(int i=wall.size()-1;i>=0;i--){
                int r = wall.get(i).r;
                int c = wall.get(i).c;
                if(r + 1 == n) {
                    map[r][c] = '.';
                    wall.remove(i);
                }
                else {
                    map[r][c] = '.';
                    map[r+1][c] = '#';
                    wall.get(i).r++;
                }
            }
            goal++;
        }
        System.out.println(0);
    }
}
