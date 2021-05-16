package _210511.boj20166;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * [골드5] 문자열 지옥에 빠진 호석
 * 1. 결과 : 시간초과 (80%) -> 맞았습니다.
 * 2. 시간 복잡도 : O(N*M*8^(문자열길이)) => 최악의 경우(10*10*8^5) => 3_276_800
 *	- 이유 : 풀이와 동일
 *
 * 3. 풀이
 * 	(1) 전체 맵을 순회
 * 		* BFS 탐색
 * 			-> 8방향으로 이동하면서 만들 수 있는 문자열을 hashMap에 저장 -> 등장 횟수 포함
 * 	(2) 맵을 순회하면서 생성된 문자열 리스트에서 신이 좋아하는 문자열을 찾은 후, 등장 횟수 return 
 * 
 * 4. 시간초과 풀이
 *	- 시간 복잡도 : O(K*(N*M*8^(문자열길이))) => 최악의 경우 1_000*(10*10*8^5) => 30억
 * 	(1) 신이 좋아하는 문자열(K)의 수만큼 순회
 * 	(2) 전체 맵을 순회 
 * 		* BFS 탐색
 * 			-> 문자열의 idx와 이동한 해당 위치의 알파벳과 일치하면 push
 * 			-> 문자열의 길이만큼 탐색 완료했으면, qu의 size만큼 cnt(신이 좋아하는 문자열과 일치하는 경우의 수)에 추가
 * 
 * 5. 후기
 * 	- 문제가 넘 복잡해보여서, 일단 요구하는대로 구현을 해야겠다고 생각했다. 시간복잡도 생각할 여유가 없었다..ㅠㅡㅠ
 *  - 생성 가능한 문자열을 모두 저장하면 터질거라는 생각에 스쳐 지나갔던 아이디어였다. 범위를 생각했어야 했는데!
 *  - 송썜 고마워영 ^-^
 */
public class Main_ja {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int N = input[0];
		int M = input[1];
		int K = input[2];

		String[][] map = new String[N][M];
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().split("");
		}

		int[][] dt = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };

		ArrayList<String> likeStr = new ArrayList<>(); // 신이 좋아하는 문자열
		while (K-- > 0) {
			likeStr.add(br.readLine());
		}

		HashMap<String, Integer> strList = new HashMap<>(); // (key, value) => 만들 수 있는 문자열, 등장 횟수

		// 맵 전체 순회
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				int len = 1; // 문자열 길이
				
				Queue<Node> qu = new LinkedList<>();
				qu.add(new Node(new int[] { i, j }, map[i][j])); // point, str
				strList.put(map[i][j], strList.getOrDefault(map[i][j], 0) + 1); // 이미 존재하는 문자열이면 +1, 아니면 1

				while (!qu.isEmpty()) { // bfs 시작
					int size = qu.size();
					if (len == 5) // 최대 길이 5
						break;

					for (int s = 0; s < size; s++) { // qu의 사이즈만큼 순회
						Node cur = qu.poll();
						for (int d = 0; d < 8; d++) { // 상하좌우,대각선
							int ci = cur.point[0] + dt[d][0];
							int cj = cur.point[1] + dt[d][1];
							ci = ci == -1 ? N - 1 : (ci == N ? 0 : ci); // 환형 구현
							cj = cj == -1 ? M - 1 : (cj == M ? 0 : cj);

							String str = cur.str + map[ci][cj]; // 새로운 문자열
							qu.add(new Node(new int[] { ci, cj }, str));
							strList.put(str, strList.getOrDefault(str, 0) + 1); 
						}
					}
					len++;
				}

			}
		}
		
		String result = "";
		for (int i = 0; i < likeStr.size(); i++) {
			result += strList.getOrDefault(likeStr.get(i), 0) + "\n";
		}
		System.out.println(result);
	}

	static class Node {
		int[] point;
		String str;

		public Node(int[] point, String str) {
			this.point = point;
			this.str = str;
		}

	}
}