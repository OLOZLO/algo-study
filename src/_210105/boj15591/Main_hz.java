package _210105.boj15591;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main_hz {
	static int N;
	static List<Node>[] adj;
	
	public static class Node {
		int to, weight;
		
		Node (int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		int Q = sc.nextInt();
		
		adj = new ArrayList[N+1];
		for (int i = 1; i <= N; i++) 
			adj[i] = new ArrayList<>();
		
		for (int n = 0; n < N-1; n++) {
			int i = sc.nextInt();
			int j = sc.nextInt();
			int w = sc.nextInt();
			adj[i].add(new Node(j, w));
			adj[j].add(new Node(i, w));
		}
		
		for (int q = 0; q < Q; q++) {
			System.out.println(bfs(sc.nextInt(), sc.nextInt()));
			
		}
	}
	
	public static int bfs(int K, int V) {
		Queue<Node> q = new LinkedList<>();
		int[] check = new int[N+1];
		Arrays.fill(check, Integer.MAX_VALUE);
		
		for (int i = 0; i < adj[V].size(); i++) {
			q.add(adj[V].get(i));
			check[adj[V].get(i).to] = adj[V].get(i).weight;
		}
		
		while(!q.isEmpty()) {
			Node now = q.poll();
			
			for (int i = 0; i < adj[now.to].size(); i++) {
				Node conn = adj[now.to].get(i);
				
				if (conn.to != V && check[conn.to] == Integer.MAX_VALUE) {
					check[conn.to] = Math.min(now.weight, conn.weight);
					q.add(new Node(conn.to, Math.min(now.weight, conn.weight)));
				}
			}
		}
		
		int result = 0;
		for (int i = 1; i <= N; i++) {
			if (i != V && check[i] != Integer.MAX_VALUE && check[i] >= K) {
				result++;
			}
		}
		
		return result;
	}
}
