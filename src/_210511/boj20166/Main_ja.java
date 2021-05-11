package _210511.boj20166;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * [골드5] 문자열 지옥에 빠진 호석
 * 1. 결과 : 시간초과 (80%)
 * 2. 시간 복잡도 : O(K*(N*M*8^(문자열길이))) => 최악의 경우 1_000*(10*10*8^5) => 30억
 *	- 이유 : 풀이와 동일
 *
 * 3. 풀이
 * 	(1) 신이 좋아하는 문자열(K)의 수만큼 순회
 * 	(2) 전체 맵을 순회 
 * 		* BFS 탐색
 * 			-> 문자열의 idx와 이동한 해당 위치의 알파벳과 일치하면 push
 * 			-> 문자열의 길이만큼 탐색 완료했으면, qu의 size만큼 cnt(신이 좋아하는 문자열과 일치하는 경우의 수)에 추가
 * 
 * 4. 후기
 * 	- 문제가 넘 복잡해보여서, 일단 요구하는대로 구현을 해야겠다고 생각했다. 시간복잡도 생각할 여유가 없었다..ㅠㅡㅠ
 *  - 중복되는 문자열이 존재하니까, 해당 문자열을 기준으로 qu를 저장해서 돌려쓰면 될 것 같은데, 하다가 멘붕 터져버렸다...
 */
public class Main_ja {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int N = input[0];
		int M = input[1];
		int K = input[2];
		
		char[][] map = new char[N][M];
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}
		
		String result = "";
		int[][] dt = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};
		
		// 신이 좋아하는 문자열의 개수만큼 순회
		while(K-- > 0) {
			// 신이 좋아하는 문자열
			char[] str = br.readLine().toCharArray();
			
			// 신이 좋아하는 문자열과 동일한 경우의 수
			int cnt = 0;
			
			// 맵 전체 순회
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					int idx = 0; // 문자열 비교 idx
					if(map[i][j] != str[idx++]) continue; // 첫 글자부터 다르면 pass
					
					Queue<int[]> qu = new LinkedList<>();
					qu.add(new int[]{i,j});
					
					while(!qu.isEmpty()) { // bfs 시작
						int size = qu.size();
						if(idx == str.length) { // 신이 좋아하는 문자열과 전부 비교한 상태, qu에 남은 애들은 신이 좋아하는 문자열과 동일한 경우이다.
							cnt+=size;
							break;
						}
						for (int s = 0; s < size; s++) { // qu의 사이즈만큼 순회
							int[] cur = qu.poll();
							for (int d = 0; d < 8; d++) { // 상하좌우,대각선
								int ci = cur[0] + dt[d][0];
								int cj = cur[1] + dt[d][1];
								ci = ci == -1 ? N-1 : (ci == N ? 0 : ci); // 환형 구현
								cj = cj == -1 ? M-1 : (cj == M ? 0 : cj);
								if(map[ci][cj] != str[idx]) continue; // idx번째 문자와 동일한 경우만 qu에 저장
								qu.add(new int[]{ci, cj});
							}
						}
						idx++;
					}
				}
			}
			result += cnt + "\n";
		}
		System.out.println(result);
	}

}