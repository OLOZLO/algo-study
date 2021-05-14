package _210511.boj6236;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * [실버3] 용돈관리
 * 1. 결과 : 시간초과 (9%) -> 맞았습니다.
 * 2. 시간복잡도 : O(logN) , 이분 탐색
 * 	- 이유
 * 		-> N일을 두 가지 경우(남은 돈 사용 혹은 인출)로 순회 => 2^(N-1)
 * 		-> 입력의 max 금액 ~ 최대 금액까지 매칭 => (1 ~ 10_000*N)
 * 		즉, 2^(N-1)*10_000*N => 2^(N-1) => 최악의 경우, 2^(100_000-1)
 * 		이에, 시초날 것이라 생각하고 이분 탐색으로 접근. log(10_000*100_000) = log10^9
 * 
 * 3. 풀이
 * 	(1) 예상하는 금액 K를 기준으로 이분탐색 진행
 * 		IF. 해당 금액으로, M번의 인출로 N일 동안 사용할 수 있는지 판단
 * 			- N일을 순회하면서 몇 번이나 인출해야 하는지 count
 * 				-> 부족하면 인출하고, 아니면 남은 돈을 사용
 * 				-> M보다 count가 적거나 같은 경우 해당 금액으로 사용 가능!
 *  (2) 최소 금액 K를 return
 * 
 * 4. 후기
 *	- 처음에 시초 난 이유 (맨 아래 주석으로 남겨뒀습니다!)
 *		-> (남은 돈을 사용하거나 / 새로 인출해서 사용하는) 두 가지 경우를 가지고 재귀를 돌림
 *		-> 무조건 M과 count가 같은 경우를 찾아야 한다고 생각했다.
 *		-> M과 안 맞으면 계속 탐색을 하다보니, 최악의 경우 2^N만큼 돌아서 시초가 났나봄! 
 *
 */
public class Main_ja {
	static int[] usedInDay;
	static int N, M;
	static boolean check;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input =br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		usedInDay = new int[N];
		
		int max = 0;
		for (int i = 0; i < N; i++) {
			usedInDay[i] = Integer.parseInt(br.readLine()); // i번쨰 날에 이용할 금액
			max = Math.max(max, usedInDay[i]); // 최대 금액
		}
		int s = max;
		int e = 10_000 * 100_000; // 최악의 경우
		int result = 0;
		
		// 예상 금액을 기준으로 이분 탐색
		while(s<=e) {
			int mid = (s+e)/2;
			if(solve(mid, 0)) { // 해당 금액으로 가능하면 더 작은 금액을 찾아보자
				e = mid - 1;
				result = mid;
			} else { // 불가능하면 더 큰 금액을 찾아보자
				s = mid + 1;
			}
		}
		System.out.println(result);
	}
	
	public static boolean solve(int price, int res) {
		int cnt = 0;
		for (int i = 0; i < N; i++) { // N일을 전부 순회해서
			if(res-usedInDay[i] < 0) { // 남은 돈이 부족하면 인출
				res =  price - usedInDay[i];
				cnt++;
			}else // 아니면 그대로 사용
				res -= usedInDay[i];
		}
		return cnt <= M; 
	}

	// 시초났던 solve
	public static void solve_fail(int price, int res, int cnt, int idx) {
		if(check || res < 0 || (cnt == M && idx < N) ) return;
		
		if(idx == N) {
			if(cnt <= M) check = true;
			return;
		}
		
		solve_fail(price, res-usedInDay[idx], cnt, idx+1); // 남은 걸 쓰자
		solve_fail(price, price-usedInDay[idx], cnt+1, idx+1); // 다시 꺼내서 쓰거나
	}
}
