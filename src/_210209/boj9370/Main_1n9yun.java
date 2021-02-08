package _210209.boj9370;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_1n9yun {
    static class Item{
        int to, cost;
        boolean passed;

        public Item(int to, int cost, boolean passed) {
            this.to = to;
            this.cost = cost;
            this.passed = passed;
        }
    }
    static int stoi(String s) {return Integer.parseInt(s);}
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int TC = stoi(br.readLine());
        for(int tc=1;tc<=TC;tc++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = stoi(st.nextToken());
            int m = stoi(st.nextToken());
            int t = stoi(st.nextToken());

            ArrayList<Item>[] adjList = new ArrayList[n+1];
            for(int i=1;i<=n;i++) adjList[i] = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            int s = stoi(st.nextToken());
            int g = stoi(st.nextToken());
            int h = stoi(st.nextToken());

            for(int i=0;i<m;i++){
                st = new StringTokenizer(br.readLine());
                int from = stoi(st.nextToken());
                int to = stoi(st.nextToken());
                int cost = stoi(st.nextToken());
                boolean passed = (g == from && h == to) || (g == to && h == from);

                adjList[from].add(new Item(to, cost, passed));
                adjList[to].add(new Item(from, cost, passed));
            }

            HashSet<Integer> candidates = new HashSet<>();
            for(int i=0;i<t;i++) candidates.add(stoi(br.readLine()));
            HashSet<Integer> answer = new HashSet<>();

            int[] check = new int[n+1];
            Arrays.fill(check, Integer.MAX_VALUE);
            check[s] = 0;

            PriorityQueue<Item> pq = new PriorityQueue<>((o1, o2) -> {
                if(o1.cost > o2.cost) return 1;
                else if(o1.cost == o2.cost) {
                    if(!o1.passed && o2.passed) return 1;
                }
                return -1;
            });
            pq.addAll(adjList[s]);

            while(!pq.isEmpty()){
                Item item = pq.poll();

                if(check[item.to] == Integer.MAX_VALUE){
                    check[item.to] = item.cost;
                    if (candidates.contains(item.to) && item.passed) answer.add(item.to);

                    for(Item next : adjList[item.to]){
                        if(check[next.to] != Integer.MAX_VALUE) continue;

                        pq.add(new Item(next.to, item.cost + next.cost, next.passed || item.passed));
                    }
                }
            }
            answer.stream().sorted().forEach(ans -> System.out.print(ans + " "));
            System.out.println();
        }
    }
}
