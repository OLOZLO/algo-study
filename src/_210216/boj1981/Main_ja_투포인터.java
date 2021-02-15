package _210216.boj1981;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ja_투포인터 {
	/**
	 * [ 배열에서 이동 ]
	 * (1,1) -> (n,n) 상하좌우 이동
	 * 이동하기 위해 거쳐 간 수들 중, 최댓값과 최솟값의 차이가 가장 작아지는 경우를 구하는 프로그램
	 * 
	 * [ POINT ]
	 * 투 포인터 알고리즘을 사용해서 최댓값과 최솟값의 범위를 미리 지정해서 BFS를 돌려볼거임! 
	 * 가능하면, 최댓값과 최솟값의 차이를 비교 및 저장!
	 * 
	 * [ 투 포인터 알고리즘 ]
	 * - 2개의 포인터를 이용해 원하는 값을 얻는 방식
	 * - 연속적인 값들을 이용해 푸는 문제에 적합 (ex : 구간의 합)
	 * - 시간복잡도 O(n)
	 * 
	 * (1) 투 포인터가 탐색할 배열 생성(numbers)
	 * 	(1)-1. n*n배열에서 나올 수 있는 수들을 ArrayList(numbers)에 저장
	 * 	(1)-2. 오름차순으로 sort
	 * 
	 * (2) 2개의 포인터(min, max)를 0으로 초기화 
	 * 
	 * (3) while(min과 max가 numbers 배열 끝까지 이동할 때까지)
	 * 		IF(numbers[min], numbers[max] 범위내에서 BFS를 돌릴 수 있는지 여부 return) // 편의상 numbers[인덱스]로 표현
	 * 			answer = Math.min(answer,numbers[max]-numbers[min])
	 * 			min++;
	 *  	ELSE
	 *  		max++;
	 *  
	 * @throws IOException 
	 *  	
	 */
	static int N;
	public static void main(String[] args) throws  IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		int[][] map = new int[N][N];
		ArrayList<Integer> numbers = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				// (1)-1. N*N에서 나올 수 있는 수들을 배열에 저장
				if(!numbers.contains(map[i][j]))
					numbers.add(map[i][j]);
			}
		}
		// (1)-2. 오름차순으로 정렬
		Collections.sort(numbers);
		
		// (2) 투 포인터 준비
		int min=0, max=0;
		int answer = Integer.MAX_VALUE;
		
		// (3) 투 포인터가 배열 끝까지 이동할 때 까지 반복
		int len = numbers.size();
		while(min<len && max<len) {
			int minValue = numbers.get(min);
			int maxValue = numbers.get(max);
			// (3)-1. 투 포인터 범위내에서 bfs를 돌릴 수 있는지 여부 판단
			if(bfs(map, minValue, maxValue)) {
				answer = Math.min(answer, maxValue - minValue);
				min++;
			}
			else {
				max++;
			}
		}
		System.out.println(answer);
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
