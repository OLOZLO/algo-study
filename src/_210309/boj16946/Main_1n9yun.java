package _210309.boj16946;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1n9yun {
    static class Item{
        int left, right;

        public Item(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }
    static int[][] delta = {{-1,0},{1,0},{0,-1},{0,1}};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][m];
        for(int i=0;i<n;i++){
            String line =  br.readLine();
            for(int j=0;j<m;j++){
                map[i][j] = line.charAt(j) - '0';
            }
        }

//        HashMap<Integer, Integer> clusters = new HashMap<>();
        int[] clusters = new int[n*m+2];
//        빈칸들 인덱싱, 크기 구하기
        int index = 2;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(map[i][j] != 0) continue;

                Queue<Item> q = new LinkedList<>();
                q.add(new Item(i, j));
                map[i][j] = index;

                int size = 0;
                while(!q.isEmpty()){
                    Item item = q.poll();
                    size++;

                    for(int[] dir : delta){
                        int nRow = item.left + dir[0];
                        int nCol = item.right + dir[1];

                        if(0<=nRow && nRow<n && 0<=nCol && nCol<m && map[nRow][nCol] == 0){
                            map[nRow][nCol] = index;
                            q.add(new Item(nRow, nCol));
                        }
                    }
                }
                clusters[index++] = size;
            }
        }

        int[][] answer = new int[n][m];
//        벽 위치에서 사방에 어떤 클러스터가 있는지 확인 후 크기 더하기
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(map[i][j] != 1) continue;

                HashSet<Integer> connectedClusters = new HashSet<>();
                for(int[] dir : delta){
                    int nRow = i + dir[0];
                    int nCol = j + dir[1];

                    if(0<=nRow && nRow<n && 0<=nCol && nCol<m && map[nRow][nCol] != 1){
                        connectedClusters.add(map[nRow][nCol]);
                    }
                }
                int size = 1;
                for(Integer clusterIdx : connectedClusters) size += clusters[clusterIdx];

                answer[i][j] = size % 10;
            }
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++) bw.write(String.valueOf(answer[i][j]));
            bw.write('\n');
        }
        bw.close();
    }
}
