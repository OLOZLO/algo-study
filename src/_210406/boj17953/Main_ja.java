package _210406.boj17953;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * [골드5] 디저트
 * 1. 결과 : 성공
 * 2. 시간 복잡도 : O(NM^2), 최악의 경우 : 100,000*10^2
 * 	- 이유	
 *	(1) 범위 : N(1~100,000), M(1~10)
 *	(2) 접근
 *		- 전날, x번 디저트를 먹을 때, 다음날 먹을 수 있는 디저트(1-M)를 모두 매칭
 *		- dp를 써서, 해당 디저트를 먹을 때, 가장 만족감이 큰 값을 저장
 *		
 * 3. 후기
 * - 처음에 중복순열로, Queue에 만족감을 누적해서 dp에 저장한 값보다 작으면 pass하는 식으로 구현했다.
 *   나중에 코드 정리하고 보니까, 조건이 결국 다음 날에서 가장 큰 값으로 그 다음 날을 탐색하는 것이라,
 *   굳이 Queue를 사용하지 않아도 된다는 걸 깨달았다. (현재 코드는 수정한 상태)
 *   오히려 Queue를 쓰면 완벽하게 필터 안됨 (조건 잘 안걸어주면 시초)
 *  
 * - (시간초과) dp없는 중복순열의 시간 복잡도 : O(M^N)
 *  
 * - 문제푸는 시간보다 시간 복잡도 계산하는 시간이 더 걸렸다...TT
 */
public class Main_ja {
	public static int[] inputToIntArr(String str, String separator) {
		return Arrays.stream(str.split(separator)).mapToInt(Integer::parseInt).toArray();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] input = inputToIntArr(br.readLine(), " ");
		int N = input[0]; // 날짜 수
		int M = input[1]; // 디저트 종류 수
		int[][] desert = new int[M][N];
		for (int i = 0; i < M; i++) {
			desert[i] = inputToIntArr(br.readLine(), " ");
		}
		int[][] dp = new int[M][N]; // n기간에 m디저트를 먹을 때의 만족도 최대 값 저장
		for (int i = 0; i < M; i++) { 
			dp[i][0] = desert[i][0];
		}
		int day = 0;
		int result = 0;
		while (++day != N) { // N 기간동안
			for (int cur = 0; cur < M; cur++) { // 전날 먹은 음식
				for (int next = 0; next < M; next++) { // 담날 먹은 음식
					int sum = (cur == next ? (desert[next][day] / 2) : desert[next][day]) + dp[cur][day - 1];
					dp[next][day] = Math.max(sum, dp[next][day]);
				}
			}
		}
		for (int[] d : dp) {
			System.out.println(Arrays.toString(d));
			result = Math.max(result, d[N - 1]);
		}
		System.out.println(result);
	}
}
