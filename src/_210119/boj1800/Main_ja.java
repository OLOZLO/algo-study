package _210119.boj1800;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main_ja {
	/**
	 * - 시간초과 난 코드 - 
	 * 
	 * [ 인터넷 설치 - 1번과 N번 컴퓨터를 연결하는데 드는 최소 비용]
	 * 
	 * [ 조건 ]
	 *  N(1~1,000) : 컴퓨터
	 *  P(1~10,000) : P개의 쌍만이 서로 이어질 수 있음
	 *  K(0~N) : 공짜로 제공하는 케이블 수
	 *  케이블 가격 (1~1,000,000)
	 *  - 1번과 N번 컴퓨터와 인터넷 연결
	 *  - 나머지 컴퓨터의 인터넷 연결은 선택사항
	 *  
	 *  - K개의 인터넷 선은 공짜
	 *  - 남은 인터넷 선 중 가장 비싼 것만 내면 됨
	 *  
	 *  [ Result ] 
	 *  IF. 1번과 N번 컴퓨터를 연결하는 것이 불가능하면 -1 출력
	 *  ELSE. 사야하는 인터넷 선의 최소 비용
	 *  
	 *  [ 풀이 ] 
	 *  - DFS로 나올 수 있는 경로 탐색 후, 비용 계산
	 */
	static int N,P,K;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		N = in.nextInt();
		P = in.nextInt();
		K = in.nextInt();
		int[][] cable = new int[N+1][N+1];
		
		for (int i = 0; i < P; i++) {
			int c1 = in.nextInt();
			int c2 = in.nextInt();
			int price = in.nextInt();
			cable[c1][c2] = price;
			cable[c2][c1] = price;
		}
		
		boolean[] visited = new boolean[N+1];
		ArrayList<Cable> selected = new ArrayList<>();
		visited[1] = true;
		selected.add(new Cable(1,0));
		result = Integer.MAX_VALUE;
		dfs(cable,selected,visited,1);
		if(result==Integer.MAX_VALUE) {
			System.out.println(-1);
			return;
		}
		System.out.println(result);

	}
	static int result = Integer.MAX_VALUE;
	public static void dfs(int[][] cable, ArrayList<Cable> selected, boolean[] visited, int maxResult) {
		if(maxResult>=K+2) return;
		int lastCable = selected.size()!=0?selected.get(selected.size()-1).computer:0;

		if(lastCable == N) {
			if(selected.size()<=K)
				result = 0;
			else {
				ArrayList<Cable> arr = (ArrayList<Cable>) selected.clone();
				Collections.sort(arr);
				result = Math.min(arr.get(K).price,result);
			}
			return;
		}
		for (int i = 1; i <= N; i++) {
			if(visited[i]) continue;
			if(cable[lastCable][i] == 0) continue;
			selected.add(new Cable(i,cable[lastCable][i]));
			visited[i] = true;
			dfs(cable,selected,visited, cable[lastCable][i]>result?maxResult+1:maxResult);
			selected.remove(selected.size()-1);
			visited[i] = false;
		}
	}
	static class Cable implements Comparable<Cable>{
		int computer;
		int price;
		
		public Cable(int computer, int price) {
			super();
			this.computer = computer;
			this.price = price;
		}

		@Override
		public int compareTo(Cable o) {
			if(this.price>o.price)
				return -1;
			return 1;
		}
		
		@Override
			public String toString() {
				return price + "";
			}
	}
}
