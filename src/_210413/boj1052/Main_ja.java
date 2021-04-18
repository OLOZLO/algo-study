package _210413.boj1052;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * [실버1] 물병
 * 1. 결과 : 성공
 * 2. 시간 복잡도 : O(log2(n))
 * 	- 이유
 *    N을 2진수로 변환하는 과정에서 1의 개수를 가지고 사야하는 물병의 수를 구했다.
 *    -> 이진수 변환 시간 복잡도 : O(log2(n))
 *    
 *  - 범위 
 *    - N : ~ 10^7
 *    - K : ~ 1,000
 *    
 * 	- 접근 방식
 * 		1) 기존에 있는 물병을 합칠 수 있을 때까지 다 합친다.(N/2 반복, 마지막엔 1개로 합쳐짐)
 *		   POINT. 이때, 중간에 합쳐지지 못한 물병이 존재할 경우 따로 배열에 저장한다. (N%2==1인 경우)
 * 				  -> 배열에 저장할 데이터(x)는, 현재 물병이 N에서 몇 번 나눈 상태인지를 저장한다.
 * 					 (2의 x승 == 필요한 물병 개수)
 * 		   IF. 합쳐지는 과정에서 남은 물병이 K보다 작으면 0을 반환한다. (물병 안사도 됨!)
 * 
 * 		2) 최종적으로 남은 물병은 K보다 많이 남은 경우다.
 * 			-> 젤 마지막까지 살아남은 물병부터 K개를 선택한다.
 * 			-> 나머지 물병은 새로 구매한 물병을 가지고 K번째에 해당하는 물병과 합친다. 완벽한 K개로 만들어주기!
 * 		
 * 3. 후기
 *	- 함정인지도 모르고, 불가능한 경우가 무엇인지 계속 찾았다. 함정이 너무 나빴다 :( 
 * 	- Integer.bitCount()라는 좋은 함수를 두고 직접 구현했다. 코드만 보면 좀 복잡하지만, 재밌다 :>
 *	
 */
public class Main_ja {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int K = in.nextInt();
		if (N == 1) {
			System.out.println(0);
			return;
		}
		
		ArrayList<Integer> res = new ArrayList<>();
		int cnt = 0; // cnt : N을 몇 번 나눴는가
		while (N > 1) {
			if (N % 2 == 1)
				res.add(cnt);
			N /= 2;
			cnt++;

			if (N + res.size() <= K) {
				System.out.println(0);
				return;
			}
		}
		res.add(cnt); // 마지막으로 살아남은 물병도 추가
		
		// K번 째 물병을 합치기 위해 필요한 새로운 물병 수 (남은 물병 고려 x)
		int result = 1 << res.get(res.size() - K); 
		
		// K개 선택하고 남은 물병들, 필요한 물병 수에서 남은 물병 수를 빼주기!
		int next = res.size() - K; 
		while(--next >= 0)
			result -= 1 << res.get(next); 
		
		System.out.println(result);
	}
}
