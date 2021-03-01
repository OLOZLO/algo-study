package _210302.boj1956;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt(), E = sc.nextInt(), MAX = 987654321;
        int[][] adj = new int[V + 1][V + 1];
        for (int[] a : adj)
            Arrays.fill(a, MAX);

        while (E-- > 0)
            adj[sc.nextInt()][sc.nextInt()] = sc.nextInt();

        for (int k = 1; k <= V; k++) { // i에서 출발한다음 i로 돌아와야 하니까 이거 거르지말고 어디찍고왔을 때 가장 빨리오는지 보려고 continue 안먹임
            for (int i = 1; i <= V; i++)
                for (int j = 1; j <= V; j++)
                    if (adj[i][j] > adj[i][k] + adj[k][j])
                        adj[i][j] = adj[i][k] + adj[k][j];
        }

        int ans = IntStream.rangeClosed(1, V).map(i -> adj[i][i]).min().getAsInt(); // i -> i 경로로 오는 것 중 가장 빠른거 반환
        System.out.println(ans >= MAX ? -1 : ans); //  만약 min이 MAX 이상이면 어디 찍고 오는 경우 없는 것 -1 반환. 아니면 min값 반환
    }
}
