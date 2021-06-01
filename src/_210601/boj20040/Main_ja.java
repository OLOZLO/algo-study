package _210601.boj20040;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main_ja {
	/**
	 * [골드4] 사이클 게임
	 * 1. 결과 : 시간초과(1%) -> 맞았습니다.
	 * 2. 시간복잡도 : O(logN)
	 *	- 이유 : Union-Find의 시간복잡도 -> 사향트리가 될 경우, O(N)
	 * 
	 * 3. 풀이
	 * 	(1) 두 점에 대해 union-find
	 * 	(2) 두 점의 부모가 같으면 사이클이 형성됐음을 의미
	 * 
	 * 4. 시초났던 이유 (코드가 너무 더러워서 깃에는 안 올렸습니다..)
	 * 	- 해당 선을 추가할 때마다, DFS 탐색을 통해 사이클이 형성 되는지를 판단
	 * 	- 최악의 경우, M*O(N^2) => 1_000_000*500_000^2 ...으로 예상함
	 * 
	 * 3. 후기
	 * 	- 너무 어렵게 생각했다. 초코먹고 나니까 생각나드라. ^^.. 알고바보가 된 기분...
	 * 	- 제출한 코드보면 메모리랑 시간 둘다 폭발하려고 하는데, BufferdReader랑 StringTokenizer 사용하면 줄드라!
	 * 
	 */
	static int[] parents;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int M = in.nextInt();
		
		parents = IntStream.range(0, N).toArray();
		for (int i = 1; i <= M; i++) {
			int parentA = findParent(in.nextInt());
			int parentB = findParent(in.nextInt());
			
			if(parentA == parentB) {
				System.out.println(i);
				return;
			}else
				unionParent(parentA, parentB);
		}
		System.out.println(0);
	}
	
	public static int findParent(int child) {
		if(parents[child] == child)
			return child;
		return findParent(parents[child]);
	}
	
	public static void unionParent(int parentA, int parentB) {
		if(parentA > parentB)
			parents[parentA] = parentB;
		else 
			parents[parentB] = parentA;
	}
}