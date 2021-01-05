package _210105.boj10021;

import java.util.Arrays;
import java.util.Scanner;

public class Main_Taekyung2 {

    static int V, C;
    static int[] x, y;
    static int[][] adj;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        C = sc.nextInt();
        adj = new int[V][V];
        x = new int[V];
        y = new int[V];
        for(int i = 0; i < V; i++) {
            x[i] = sc.nextInt();
            y[i] = sc.nextInt();
        }

        for(int i = 0; i < V - 1; i++) {
            for(int j = i + 1; j < V; j++) {
                int d = (int) (Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2));
                if(C <= d) {
                    adj[i][j] = d;
                    adj[j][i] = d;
                }
            }
        }
        System.out.println(prim());
    }

    static int prim() {
        boolean[] added = new boolean[V];
        int[] min = new int[V];
        Arrays.fill(min, Integer.MAX_VALUE);
        int ret = 0;
        min[0] = 0;
        for(int iter = 0; iter < V; iter++) {
            int u = -1;
            for(int v = 0; v < V; v++)
                if(!added[v] && (u == -1 || min[u] > min[v]))
                    u = v;
            ret += min[u];
            added[u] = true;

            for(int i = 0; i < V; i++) {
                if(adj[u][i] == 0) continue;
                int w = adj[u][i];
                if(!added[i] && min[i] > w) {
                    min[i] = w;
                }
            }
        }
        for(int n : min) {
            if(n == Integer.MAX_VALUE)
                return -1;
        }
        return ret;
    }
}