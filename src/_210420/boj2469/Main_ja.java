package _210420.boj2469;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_ja {
	/**
	 * [실버1] 사다리타기
	 * 1. 결과 : 틀렸습니다. (58%)
	 * 2. 시간복잡도 : K*K*3? 
	 * 
	 * 3. 접근 방식
	 * 	(1) map을 새롭게 가공!
	 * 		-> map의 열을 2배로 늘려서 사다리모양으로 만들어 버림!
	 * 
	 * 	(2) 도착지점을 기준으로  감추어진("????") 라인까지 올라간다.
	 * 		-> 해당 도착 위치를 별도의 배열로 저장
	 * 
	 * 	(3) 시작지점에서 알파벳을 하나씩 출발해서 감추어진("????") 라인까지 이동한다.
	 * 		-> 아래, 왼쪽, 오른쪽 이동. 이 세가지 경우를 가지고 해당 알파벳 도착지점까지 갈 수 있는지 확인 (1번에서 저장한 도착위치 배열과 비교)
	 * 		-> 못하면 "x" 반복 출력
	 * 
	 * 	- 예외 처리한 부분
	 * 	(1) 가로막대('-')를 추가 했을 때, 해당 위치의 양 옆에 '-'가 있는지 확인 (두 개의 가로 막대가 직접 연결될 수 없다고 해서)
	 *  (2) 1번 단계에서, 두 개의 알파벳의 도착 위치가 동일한 경우 -> 더 비교할 것 없이 'x'를 반환
	 *  
	 *  - 후기
	 *  - 58%에서 틀린 이유가, 예외처리(1) 때문이라고 생각했음. 그래서 조건을 추가하다보니 코드가 매-우 더러워짐.
	 *  
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int K = Integer.parseInt(br.readLine()) - 1;
		int R = Integer.parseInt(br.readLine());
		int C = K * 2 + 1;

		// 도착지점
		int[] end = br.readLine().chars().map(c -> c - 'A').toArray();
		char[][] map = new char[R][C];
		for (int i = 0; i < R; i++) {
			Arrays.fill(map[i], '*');
		}

		// 감추어진 사다리 위치 인덱스
		int connect = 0;
		
		// map을 두배로 늘려서 입력값 사이사이에 새로운 열을 집어넣은 형태.
		// 사다리 모양으로 만들어줌.
		for (int i = 0; i < R; i++) {
			String str = br.readLine() + "*";
			int idx = 0;
			for (int j = 0; j < C - 1; j += 2) {
				map[i][j + 1] = str.charAt(idx++);
				if (map[i][j + 1] == '?')
					connect = i;
			}
		}
		Arrays.fill(map[connect], '?');

		int[] bottom = new int[C];
		Arrays.fill(bottom, -1);
		
		// 도착지점에서 감추어진 지점까지 올라가기
		int idx = 0; // 알파벳 idx
		for (int c = 0; c < C; c += 2) {
			int i = R - 1;
			int j = c;
			while (i > connect) {
				if (j - 1 >= 0 && map[i][j - 1] == '-')
					j -= 2;
				else if (j + 1 < C && map[i][j + 1] == '-')
					j += 2;
				i--;
			}
			if (bottom[j] != -1) { // 도착지점이 같은 경우는, 더 올라가도 갈라질 수 없으므로 x 출력
				System.out.println("x".repeat(K));
				return;
			}
			bottom[j] = end[idx++];
		}

		// 아래는 헷갈려서 풀어쓴 코드. 중복 코드가 많습니당
		// 1. 출발지점에서 감추어진 지점까지 내려가기
		// 2. 감추어진 지점에서 아래, 왼쪽, 오른쪽 으로 이동하면서 해당 알파벳의 도착 지점과 만날 수 있는지 판단
		idx = 0;
		for (int c = 0; c < C; c += 2) {
			// 1. 출발지점에서 감추어진 지점까지 내려가기
			int i = 0;
			int j = c;
			while (i < connect) {
				if (j - 1 >= 0 && map[i][j - 1] == '-')
					j -= 2;
				else if (j + 1 < C && map[i][j + 1] == '-')
					j += 2;
				i++;
			}

			// 아래로 내려가면 도착할 수 있는 경우
			// [ 이동 못하는 경우 ]
			// 왼쪽, 오른쪽에 '-'가 없을 경우만 이동 가능
			if (bottom[j] == idx) {
				if ((j - 1 >= 0 && map[i][j - 1] == '-') || (j + 1 < C && map[i][j + 1] == '-')) {
					System.out.println("x".repeat(K));
					return;
				}
			}
			// 왼쪽으로 가면 도착할 수 있는 경우
			// [ 가로 막대를 설치 못하는 경우 ]
			// - 가로('-') 막대를 기준으로 왼쪽, 오른쪽에 가로 막대가 있는 경우
			// - 해당 위치에 가로 막대가 없다고 저장한 경우
			if (j - 2 >= 0 && bottom[j - 2] == idx) {
				if ( (j-3>=0 && map[i][j - 3] == '-') || map[i][j - 1] == '*' || (j + 1 < C && map[i][j + 1] == '-')) {
					System.out.println("x".repeat(K));
					return;
				}
				map[i][j - 2] = '*';
				map[i][j - 1] = '-';
			}

			// 오른쪽으로 가면 도착할 수 있는 경우
			// 왼쪽과 동일하게 조건 확인
			if (j + 2 < C && bottom[j + 2] == idx) {
				if ( (j+3<C &&map[i][j + 3] == '-') || map[i][j + 1] == '*' || (j - 1 >= 0 && map[i][j - 1] == '-')) {
					System.out.println("x".repeat(K));
					return;
				}
				map[i][j + 2] = '*';
				map[i][j + 1] = '-';
			}
			idx++;
		}

		// 가로 막대가 위치할 수 있는 열만 출력
		String result = "";
		for (int i = 1; i < C; i += 2) {
			result += map[connect][i] == '?' ? '*' : map[connect][i];
		}

		System.out.println(result);
	}

}
