package _210209.boj9370;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_ja {
	/**
	 * [ 미확인 도착지 ]                      
	 * 1. 다익스트라로 최단 거리 구하기
	 * 	=> IF. dist 동일할 때, G-H 도로 지나는지 check
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int TC = Integer.parseInt(br.readLine());
		for (int tc = 0; tc < TC; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int T = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			int S = Integer.parseInt(st.nextToken());
			int G = Integer.parseInt(st.nextToken());
			int H = Integer.parseInt(st.nextToken());
			
			ArrayList<Node>[] adj = new ArrayList[N+1];
			
			for (int i = 0; i < N+1; i++) {
				adj[i] = new ArrayList<>();
			}
			
			// 인접리스트
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int nodeA = Integer.parseInt(st.nextToken());
				int nodeB = Integer.parseInt(st.nextToken());
				int d =Integer.parseInt(st.nextToken());
				adj[nodeA].add(new Node(nodeB, d));
				adj[nodeB].add(new Node(nodeA, d));
			}
			
			// (dist, check) => check는 G-H 도로 지났는지 판별
			Node[] dist = new Node[N+1];
			
			for (int i = 0; i < N+1; i++) {
				dist[i] = new Node(Integer.MAX_VALUE, false);
			}
			dist[S].v = 0;
			
			boolean[] check = new boolean[N+1];
			
			PriorityQueue<Node> pq = new PriorityQueue<>();
			pq.add(new Node(S,0));
			
			while(!pq.isEmpty()) {
				Node node = pq.poll();
				
				for(Node next : adj[node.v]) {
					if(check[next.v]) continue;
					if(dist[next.v].v < dist[node.v].v + next.dist) continue;
					
					// dist가 같을 때도 최단거리 갱신하려고 하는데, 갱신 전 dist가 G-H 도로를 지나온 경로(check==true)면 pass
					if(dist[next.v].check && dist[next.v].v == dist[node.v].v + next.dist) continue;
					
					dist[next.v].v = dist[node.v].v + next.dist;
					dist[next.v].check = dist[node.v].check;
					pq.add(new Node(next.v,dist[next.v].v));
					
					// G-H 도로지날 때
					if((next.v == G && node.v == H) || (next.v == H && node.v == G)) {
						dist[next.v].check = true;
					}
					
				}
				check[node.v] = true;
			}
			ArrayList<Integer> result = new ArrayList<>();
			// 도착지의 최단거리에서 G-H 지났는지 return
			for (int i = 0; i < T; i++) {
				int endNode = Integer.parseInt(br.readLine());
				if(dist[endNode].check) result.add(endNode);
			}
			if(result.size()==0) continue;
			Collections.sort(result);
			for(int node : result) {
				System.out.print(node + " ");
			}
			System.out.println();
			
		}
	}
	static class Node implements Comparable<Node>{
		int v;
		int dist;
		boolean check;
		
		public Node(int v, boolean check) {
			this.v = v;
			this.check = check;
		}
		
		public Node(int v, int dist) {
			this.v = v;
			this.dist = dist;
		}
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.dist, o.dist);
		}
		
		@Override
		public String toString() {
			return this.v +"->"+ this.check;
		}
	}
	
}
