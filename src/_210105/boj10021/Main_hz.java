package _210105.boj10021;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main_hz {
	static int[] parents;
	
	static class Node {
		int i, j, cost;
		
		Node (int i, int j, int cost) {
			this.i = i;
			this.j = j;
			this.cost = cost;
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int C = sc.nextInt();
		
		Node[] filed = new Node[N];
		for (int i = 0; i < N; i++) {
			filed[i] = new Node(sc.nextInt(), sc.nextInt(), 0);
		}
		
		List<Node> list = new ArrayList<>();
		PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				return o1.cost-o2.cost;
			}
		});
		
		for (int i = 0; i < filed.length; i++) {
			for (int j = i+1; j < filed.length; j++) {
				int cost = (int)Math.pow(filed[i].i-filed[j].i, 2) + (int)Math.pow(filed[i].j-filed[j].j, 2);
				if (cost >= C)
					pq.add(new Node(i, j, cost));
			}
		}
		
		parents = new int[N+1];
		for (int i = 1; i <= N; i++)
			parents[i] = i;
		
		int result = 0;
		int cnt = 0;
		while(!pq.isEmpty()) {
			if (cnt > N)
				break;
			
			Node now = pq.poll();
			
			if (find(now.i) == find(now.j))
				continue;
			
			union(now.i, now.j);
			cnt++;
			result += now.cost;
		}
		
		if (cnt < N-1)
			System.out.println("-1");
		else
			System.out.println(result);
		
	}
	
	static int find(int x) {
		if (x == parents[x])
			return x;
		else {
			parents[x] = find(parents[x]);
			return parents[x];
		}
	}
	
	static void union(int x, int y) {
		int px = find(x);
		int py = find(y);
		
		if (px != py)
			parents[px] = py;
	}

}
