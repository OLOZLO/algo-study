package _210427.boj1072;

import java.util.Scanner;

public class Main_ja {
	/**
	 * [실버3] 게임
	 * 
	 * 1. 결과 : 틀렸습니다 (5%)
	 * 2. 시간 복잡도 : O(logN)
	 * 	  - 게임 횟수(X)를 기준으로 이분탐색
	 * 	  - 이분 탐색으로 접근한 이유는, 
	 * 		1_000_000_000(X)에서, 0(Y)번 이겼을 경우가 최악의 경우라고 생각했다.
	 * 		이때 순차적으로 계산하면 당연히 터질거라고 어림짐작해서 생각했다. 
	 * 		(이 경우 대략 1억 이상은 넘어야한다고 생각)
	 * 		계산도 틀렸고, 최악의 경우도 틀렸다.
	 * 		잘못된 접근인데, 이분탐색이라 생각한 것이다. ㅎㅎ.
	 * 
	 * 3. 풀이
	 * 	  - 게임 횟수(X)를 기준으로 예상 게임 횟수(mid)를 정한다.
	 * 	  IF. 예상 게임 횟수(mid)로 승률(Z)가 처음이랑 달라졌으면, 범위를 낮춰본다.
	 * 	  ELSE. 범위를 높여서 다시 확인해본다.
	 * 
	 * 4. 후기
	 * 	  - 자잘하게 신경 써야하는 부분이 많았다. (특히 범위 부분)
	 * 	  - 문제의 테케는 잘 나오는데 자꾸 틀렸습니다가 나와서 멘탈이 흔들렸다. ㅠㅡㅠ
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int X = in.nextInt();
		int Y = in.nextInt();
		int pre = (int)Math.floor((double)Y/X*100);
		
		long MAX = 2_000_000_000L; // 게임 횟수가 많아도 최소 10억 이상은 증가하지 않을 거라고 생각함
		long s = X;
		long e = MAX;
		
		long result = MAX;
		while(s<=e) {
			long mid = (s+e)/2;
			long win = Y + (mid - X);
			int percent = (int)Math.floor((double)win/mid*100);
			if(pre != percent) { // 승률이 바꼈으면, min값을 저장하고 끝지점을 변경
				result = Math.min(result, mid);
				e = mid - 1;
			}
			else s = mid + 1; // 승률이 안 바꼈으면, 게임 횟수를 늘려보자.
		 }
		System.out.println(result == MAX ? -1 : (result-X));
	}
}
