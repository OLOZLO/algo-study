package _210504.boj12886;

import java.util.Scanner;

public class Main_ja {
	/**
	 * [골드5] 돌 그룹
	 * 1. 결과 : 틀렸습니다. (테케부터 틀림)
	 * 
	 * 2. 시간복잡도 : 모르겠습니다..
	 * 
	 * 3. 풀이
	 * 	(1) A,B,C의 세가지 교환 경우(AB,AC,BC)를 가지고 DFS 탐색
	 * 	(2) 모든 그룹의 돌의 수가 동일 할 경우 end
	 * 
	 * 4. 후기
	 * - 중복 교환에 대해 체크를 어떻게 해줘야할까... 고민을 많이 했지만, 시간 내에 생각하지 못했다.
	 * - 중복 교환 체크 안해줘서 스택 오버플로우로 테케부터 안 맞는다 ㅠ
	 */
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		dfs(new int[]{in.nextInt(), in.nextInt(), in.nextInt()});
		System.out.println(0);
	}
	// 세 가지 교환 경우를 따로 저장함 (AB,AC,BC)
	static int[][] cases = {{0,1},{0,2},{1,2}};
	
	public static void dfs(int[] stone) {
		
		// 모든 그룹의 돌의 수가 동일하면 break
		if(stone[0] == stone[1] && stone[0] == stone[2]) {
			System.out.println(1);
			System.exit(0);
		}
		
		// 세 가지 교환 경우를 전부 확인해 봄
		for (int i = 0; i < 3; i++) {
			// 바꿀 그룹의 돌맹이 수가 같은 경우 pass
			// 다르면 돌맹이 수가 작은 경우의 그룹을 X, 큰 경우의 그룹을 Y에 저장
			if(stone[cases[i][0]] == stone[cases[i][1]]) continue;
			int X = stone[cases[i][0]] < stone[cases[i][1]] ? cases[i][0] : cases[i][1];
			int Y = stone[cases[i][0]] > stone[cases[i][1]] ? cases[i][0] : cases[i][1];
			
			int sum = stone[X];
			stone[X] += sum;
			stone[Y] -= sum;
			dfs(stone); 
			stone[X] += sum;
			stone[Y] -= sum;
		}
	}
	
}
