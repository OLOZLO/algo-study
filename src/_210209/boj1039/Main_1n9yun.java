package _210209.boj1039;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_1n9yun {
    static class Item{
        StringBuilder n;
        int count;

        public Item(StringBuilder n, int count) {
            this.n = n;
            this.count = count;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StringBuilder n = new StringBuilder(sc.next());
        int k = sc.nextInt();

        Queue<Item> q = new LinkedList<>();
        q.add(new Item(n, 0));
        int[] check = new int[(int)Math.pow(10, n.length())];

        int answer = -1;
        while(!q.isEmpty()){
            Item item = q.poll();

            if(item.count == k) {
                answer = Math.max(answer, Integer.parseInt(item.n.toString()));
                continue;
            }

            for(int i=0;i<item.n.length()-1;i++){
                for(int j=i+1;j<item.n.length();j++){
                    if(i == 0 && item.n.charAt(j) == '0') continue;
                    StringBuilder swaped = swap(new StringBuilder(item.n), i, j);
                    int toInt = Integer.parseInt(swaped.toString());
                    if(check[toInt] == item.count + 1) continue;
                    check[toInt] = item.count + 1;

                    q.add(new Item(swaped, item.count + 1));
                }
            }
        }
        System.out.println(answer);
    }
    static StringBuilder swap(StringBuilder n, int from, int to){
        char temp = n.charAt(from);
        n.setCharAt(from, n.charAt(to));
        n.setCharAt(to, temp);
        return n;
    }
}