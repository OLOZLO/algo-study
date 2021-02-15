package _210216.boj1981;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ja_이분탐색 {
	/**
	 * [ 배열에서 이동 ]
	 * (1,1) -> (n,n) 상하좌우 이동
	 * 이동하기 위해 거쳐 간 수들 중, 최댓값과 최솟값의 차이가 가장 작아지는 경우를 구하는 프로그램
	 * 
	 * [ POINT ]
	 * n*n의 minValue과 maxValue값을 저장한 후, 
	 * 이분탐색으로 min ~ max 범위내에 bfs로 도착할 수 있는지 확인할 것! 
	 * 
	 * (중요!) 투포인터 알고리즘과는 min, max의 범위를 정하는 방식이 다름!
	 * 1. 이분탐색으로 미리 최솟값과 최댓값(max-min)을 지정
	 * 2. 여기서 max는, max >= min +(max-min)이라는 조건 성립한다는 점! 
	 *
	 * 이에 도착한다면, 현재 이분탐색값(max-min)으로 n-1까지 도착할 수 있다는 것을 의미!
	 * 
	 * (정리) 
	 * 1. 이분탐색으로 최솟값과 최댓값의 차이를 미리 지정(diff) => max-min
	 * 2. max-min(1번에서 지정한 diff)을 이용해서, max >= min + (max-min)이라는 조건을 만들어 낼 수 있고
	 * 3. bfs(min,max)로 탐색해, min~max 범위내에서  n-1까지 도착할 수 있는지 판단
	 * 	  => 탐색할 수 없다면, min를 1씩 증가시켜 max(min+diff)값과 같아질때까지 탐색
	 * 	  WHY?
	 * 	  2 4 5
	 * 	  1 3 4
	 * 	  5 5 4
	 * 	  max - min = 2 일 때,
	 * 	  2->4->3->4->4 (4-2=2)로 지나갈 수 있음.
	 * 	   
	 *    BUT.
	 * 	  CASE1. 2->1->3 (3-1=2)
	 * 	  CASE2. 2->4->3 (4-2=2)
	 * 	  CASE1이 먼저 (1,1)을 방문해버리면, visited처리가 되버려서 CASE2는 못지나가게 됨.
	 * 	  => CASE1. 2->1->3->4->4(4-1=3) 
	 * 	  이런 경우 때문에, min을 증가시키면서 bfs로 탐색가능할 때까지 확인해 보는 것! 
	 * 	
	 * @throws IOException 
	 * 
	 * */
	static int N;
	static int minValue, maxValue;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		int[][] map = new int[N][N];
		
		minValue = Integer.MAX_VALUE;
		maxValue = Integer.MIN_VALUE;
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				minValue = Math.min(minValue, map[i][j]);
				maxValue = Math.max(maxValue, map[i][j]);
			}
		}
		
		int answer = Integer.MAX_VALUE;
		// 기대하는 최댓값-최소값(max-min)을 이분탐색으로 미리 지정 (0 ~ maxValue-minValue)
		int start = 0;
		int end = maxValue-minValue;
		while(start <= end) {
			int mid = (start+end)/2;
			if(solve(map, mid)) {
				answer = Math.min(answer, mid);
				end = mid-1;
			}else {
				start = mid+1;
			}
		}
		
		System.out.println(answer);
	}
	static boolean solve(int[][] map, int diff) {
		int min = minValue;
		while(min+diff <= maxValue) {
			// min과 max범위 내에서 bfs로 탐색 가능한지 판단
			if(bfs(map, min, min+diff)) return true;
			min++; // 기대하는 diff로 bfs 탐색 가능여부를 완벽하게 알아보기위해 min++후 재탐색
		}
		return false;
	}
	
	static int[][] dx = {{0,1},{0,-1},{1,0},{-1,0}};
	static boolean bfs(int[][] map, int min, int max) {
		if(map[0][0] < min || map[0][0] > max) return false;
		
		boolean[][] visited = new boolean[N][N];
		Queue<int[]> qu = new LinkedList<>();
		qu.add(new int[] {0,0});
		visited[0][0] = true;
		
		while(!qu.isEmpty()) {
			int[] p = qu.poll();
			
			if(p[0]==N-1 && p[1]==N-1) return true;
			for (int d = 0; d < 4; d++) {
				int i = p[0] + dx[d][0];
				int j = p[1] + dx[d][1];
				
				if(i<0 || j<0 || i>=N || j>=N || visited[i][j]) continue;
				// max, min 범위 내에 포함 안되는 수면, pass
				if(map[i][j]<min||map[i][j]>max) continue;
				qu.add(new int[] {i,j});
				visited[i][j] = true;
			}
		}
		return false;
	}

}
