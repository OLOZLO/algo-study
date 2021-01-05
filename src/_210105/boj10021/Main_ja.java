package _210105.boj10021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main_ja {

	/**
	 * 
	 * N개의 들판 (1~2000) 수도관 만드는 비용 : (xi-xj) ^ 2 + (yi-yj) ^ 2 C 비용을 안 넘으면 설치 못함
	 * (1~1,000,000)
	 * 
	 * 모든 필드를 수도관으로 연결하기 위한 최소 금액 계산 구축 할 수없으면 -1 리턴
	 * 
	 * 최소비용 - 무향가중치 - 크루스칼 - 프림 
	 * 
	 * 간선 = 정점 (크루스칼, pq를 사용한 프림) -> 시간복잡도 : VlogV , ElogV
	 * > 잘못했다...입력값만 잘못보고 간선 = 정점 이 성립될 줄 알았지만, 조건에 따라 완전 그래프가 나올 수 있음.
	 * > 최악의 경우는 pq를 쓰면 메모리 초과 발생.
	 * 
	 * > 일차원 배열을 사용하는 프림을 써보자.
	 * 
	 * 2000 * 2000 * 4 = 16,000,000
	 * 최대 비용 : 2000*2000 + 2000*2000 = 4,000,000 + 4,000,000
	 * int[] 써도 되겠다.
	 */
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int C = in.nextInt();
		
		
		ArrayList<int[]> field = new ArrayList<>();

		for (int i = 0; i < N; i++) 
			field.add(new int[] {in.nextInt(), in.nextInt()});
		
		int[][] data = new int[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				int cost = setCost(field,i,j);
				if(cost<C) continue;
				data[i][j] = cost;
				data[j][i] = cost;
			}
		}
		boolean[] visited = new boolean[N];
		int[] cost = new int[N];
		// 시작정점은 0
		Arrays.fill(visited, false);
		Arrays.fill(cost, Integer.MAX_VALUE);
		
		cost[0] = 0;
		for(int i=0; i<N; i++) {
			int min = Integer.MAX_VALUE;
			int index = -1;
			
			for(int j=0; j<N; j++) {
				if(!visited[j] && cost[j] < min) {
					min = cost[j];
					index = j;
				}
			}
			if(index == -1) break;
			visited[index] = true;
			for(int j=0; j<N; j++) {
				if(!visited[j] && data[index][j] !=0 && cost[j] > data[index][j]) {
					cost[j] = data[index][j];
				}
			}
		}

		int result = 0;
		for (int i = 0; i < N; i++) {
			if(!visited[i]) {
				System.out.println(-1);
				return;
			}	
		}
		
		for (int c : cost) {
			result += c;
		}
		System.out.println(result);

	}

	public static int setCost(ArrayList<int[]> field, int a, int b) {
		int[] nodeA = field.get(a);
		int[] nodeB = field.get(b);
		return (nodeA[0] - nodeB[0]) * (nodeA[0] - nodeB[0]) + (nodeA[1] - nodeB[1]) * (nodeA[1] - nodeB[1]);
	}
	
}
