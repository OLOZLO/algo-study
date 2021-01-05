package _210105.boj10021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main_Taekyung2 {

    static class Pair {
        int first, second;

        public Pair(int first, int second) {
            this.first = first;
        this.second = second;
    }
}
    static int V, C;
    static int[] x, y;
    static ArrayList<Pair>[] adj;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        C = sc.nextInt();
        adj = new ArrayList[V];
        x = new int[V];
        y = new int[V];
        for(int i = 0; i < V; i++)
            adj[i] = new ArrayList<>();
        for(int i = 0; i < V; i++) {
            x[i] = sc.nextInt();
            y[i] = sc.nextInt();
        }

        for(int i = 0; i < V - 1; i++) {
            for(int j = i + 1; j < V; j++) {
                int d = (int) (Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2));
                if(C <= d) {
                    adj[i].add(new Pair(j, d));
                    adj[j].add(new Pair(i, d));
                }
            }
        }
        System.out.println(prim());
    }

    static int prim() {
        boolean[] added = new boolean[V];
        int[] minWeight = new int[V];
        Arrays.fill(minWeight, Integer.MAX_VALUE);
        int ret = 0;
        minWeight[0] = 0;
        for(int iter = 0; iter < V; iter++) {
            int u = -1;
            for(int v = 0; v < V; v++)
                if(!added[v] && (u == -1 || minWeight[u] > minWeight[v]))
                    u = v;
            ret += minWeight[u];
            added[u] = true;

            for(int i = 0; i < adj[u].size(); i++) {
                int v = adj[u].get(i).first, weight = adj[u].get(i).second;
                if(!added[v] && minWeight[v] > weight) {
                    minWeight[v] = weight;
                }
            }
        }
        for(int n : minWeight) {
            if(n == Integer.MAX_VALUE)
                return -1;
        }
        return ret;
    }
}