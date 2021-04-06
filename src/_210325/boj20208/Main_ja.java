package _210325.boj20208;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_ja {
	/**
	 * [ 진우의 민트초코우유 ]
	 * N*N
	 * 체력 M -> 이동할 수 있는 거리
	 * 상하좌우 이동 -> 체력 -1
	 * 민초 먹으면 H 만큼 증가
	 * 0이면 이동불가
	 * 
	 * 얼마나 많은 민초를 마시고 집으로 돌아올 수 있는지
	 * 
	 */
	static int N, M, H;
	static int result;
	static int home = 0;
	static int stoi(String str) {return Integer.parseInt(str);}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		M = stoi(st.nextToken());
		H = stoi(st.nextToken());
		
		ArrayList<Milk> milks = new ArrayList<>();
		milks.add(new Milk(home,null)); 
		int milkCnt = 1;
		int[] home = new int[2];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int num = stoi(st.nextToken());
				if(num == 1)
					home = new int[] {i,j};
				else if(num == 2) 
					milks.add(new Milk(milkCnt++,new int[] {i,j}));
			}
		}
		milks.get(0).point = home;

		result = 0;
		solve(milks, new boolean[milkCnt], M, milks.get(0));
		System.out.println(result);
	} 
	static void solve(ArrayList<Milk> milks, boolean[] visited, int hp, Milk milk) {
		for(Milk next : milks) {
			if(milk.name == next.name || visited[next.name]) continue;
			int[] p1 = milk.point;
			int[] p2 = next.point;
			int dist = Math.abs(p1[0]-p2[0]) + Math.abs(p1[1]-p2[1]);
			if(hp - dist < 0) continue;	
			
			if(next.name == home) {
				int cnt = 0;
				for(boolean v : visited)
					if(v) cnt++;
				result = Math.max(result, cnt);
				continue;
			}
			
			visited[next.name] = true;
			solve(milks, visited, hp-dist+H, next);
			visited[next.name] = false;
		}
	}
	static class Milk {
		int name;
		int[] point;
		
		public Milk(int name, int[] point) {
			this.name = name;
			this.point = point;
		}
	}
	


}
