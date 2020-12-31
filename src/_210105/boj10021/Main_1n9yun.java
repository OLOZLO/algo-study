package _210105.boj10021;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main_1n9yun {
    static class Item{
        int from, to, cost;

        public Item(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }
    static class Pair{
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static int[] fieldSet;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int c = sc.nextInt();

        fieldSet = new int[n];
        for(int i=0;i<fieldSet.length;i++) fieldSet[i] = i;
        PriorityQueue<Item> pq = new PriorityQueue<>((o1, o2) -> {
            if(o1.cost > o2.cost) return 1;
            else return -1;
        });

        List<Pair> input = new ArrayList<>();
        for(int i=0;i<n;i++){
            input.add(new Pair(sc.nextInt(), sc.nextInt()));
        }
        for(int i=0;i<input.size()-1;i++){
            for(int j=i+1;j<input.size();j++){
                Pair p1 = input.get(i);
                Pair p2 = input.get(j);

                int cost = (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);

                if(cost >= c) pq.add(new Item(i, j, cost));
            }
        }

        int count = 1;
        int ans = 0;
        while(!pq.isEmpty()){
            Item item = pq.poll();

            if(union(item.from, item.to)) {
                count++;
                ans += item.cost;
                if(count == n) break;
            }
        }
        System.out.println(count == n ? ans : -1);
    }

    static int find(int idx){
        if(fieldSet[idx] == idx) return idx;

        return fieldSet[idx] = find(fieldSet[idx]);
    }

    static boolean union(int idx1, int idx2){
        int p1 = find(idx1);
        int p2 = find(idx2);

        if(p1 == p2) return false;

        fieldSet[p2] = p1;
        return true;
    }
}
