package _210105.boj15591;

import java.util.*;

public class Main_1n9yun {
    static class Pair{
        int to, cost;

        public Pair(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int q = sc.nextInt();
        List<Pair>[] adjList = new ArrayList[n+1];
        for(int i=1;i<=n;i++) adjList[i] = new ArrayList<>();

        for(int i=0;i<n-1;i++){
            int from = sc.nextInt();
            int to = sc.nextInt();
            int cost = sc.nextInt();

            adjList[from].add(new Pair(to, cost));
            adjList[to].add(new Pair(from, cost));
        }

        for(int i=0;i<q;i++){
            int k = sc.nextInt();
            int v = sc.nextInt();

            Queue<Pair> queue = new LinkedList<>();
            queue.add(new Pair(v, k));
            boolean[] check = new boolean[n+1];
            check[v] = true;
            int ans = 0;

            while(!queue.isEmpty()){
                Pair p = queue.poll();

                for(Pair next : adjList[p.to]){
                    if(!check[next.to] && next.cost >= p.cost){
                        check[next.to] = true;
                        ans++;
                        queue.add(new Pair(next.to, p.cost));
                    }
                }
            }
            System.out.println(ans);
        }
    }
}
