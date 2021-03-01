package _210302.boj1956;

import java.util.Scanner;

public class Main_1n9yun {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int v = sc.nextInt();
        int e = sc.nextInt();

        int[][] costs = new int[v+1][v+1];
        while(e-- != 0){
            int from = sc.nextInt();
            int to = sc.nextInt();
            int cost = sc.nextInt();

            costs[from][to] = cost;
        }

//        플로이드 워셜
//        사이클이라 함은 i -> j, j -> i 로 돌아오는 것.
//        따라서 모든 서로 다른 두 정점간의 최소 거리를 구해놓고 하나씩 따져보면 됨.
        for(int k=1;k<=v;k++){
            for(int i=1;i<=v;i++){
                if(k == i) continue;
                for(int j=1;j<=v;j++){
                    if(k == j || i == j) continue;
                    if(costs[i][k] != 0 && costs[k][j] != 0){
                        costs[i][j] = Math.min(costs[i][j], costs[i][k] + costs[k][j]);
                    }
                }
            }
        }

        int answer = Integer.MAX_VALUE;
        for(int i=1;i<=v;i++){
            for(int j=1;j<=v;j++){
                if(i == j || costs[i][j] == 0 || costs[j][i] == 0) continue;
                answer = Math.min(answer, costs[i][j] + costs[j][i]);
            }
        }
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}
