package _210518.boj11967;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * [골드3] 불켜기
 * 1. 결과 : 메모리 초과
 * 2. 시간 복잡도 : O(N^2)...?
 *  - 이유 :
 * 		- N(2~100), M(1~20,000)
 *  	- 모르겠습니다...ㅠㅠ
 *  
 * 3. 풀이
 * 	(1) 해당 위치에서 불을 켤 수 있는 방 탐색
 * 		(1-1) 불 켜기
 * 		(1-2) 불을 켠 방 주위(상하좌우)에 이미 방문 했던(visited) 방이 존재하면 qu에 추가 -> 주위에 방문했던 방이 존재하다는 것은, 추후에 방문이 가능하다는 것을 의미
 * 		
 * 	(2) 해당 위치에서 상하좌우 탐색
 * 		(2-1) 불을 켠 방이 존재하면 qu에 추가
 * 
 * 4. 메모리초과 풀이
 * 	(1) 더 이상 켜진 스위치가 없을 때 까지 무한 반복
 *		(1-1) (0,0)부터 출발
 *		(1-2) bfs 탐색
 * 			- 해당 위치와 연결된 방 스위치 on
 * 			- 해당 위치에서 상하좌우 이동하기 (단, 스위치가 켜진 방만 이동 가능)
 * 
 * 5. 후기
 *	- 쉬울 줄 알고 덤볐다가, 문제에서 놓친 부분이 있다는 걸 깨닫고 멘붕이 왔다.. => (1,3)에서 (2,1)방의 스위치를 켰을 때, (2,1)방과 다른 방이 연결되어 이동할 수 있는 경우.
 *	- 그러면, 스위치를 켤 때마다 시작지점에서 출발하쟈! 하다가 메모리 초과가 났다. (눈물) 
 *
 *	(+) 먼가 예외가 있을 것 같은데... 하면서 지우다 쓰다 백번한 듯. 맞아서 당황했다...
 * 
 */
public class Main_ja {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int N = input[0];
		int M = input[1];

		ArrayList<int[]>[][] switchPoint = new ArrayList[N][N]; 
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				switchPoint[i][j] = new ArrayList<int[]>();
			}
		}

		for (int i = 0; i < M; i++) {
			input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			int[] cur = new int[] { input[0] - 1, input[1] - 1 };
			int[] room = new int[] { input[2] - 1, input[3] - 1 };

			switchPoint[cur[0]][cur[1]].add(room); // 해당 위치에서 연결된 방 저장
		}

		int[][] dt = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		
		boolean[][] turnOn = new boolean[N][N]; // 불 켜짐 여부
		boolean[][] visited = new boolean[N][N]; // 해당 방 방문 여부
		
		Queue<int[]> qu = new LinkedList<>();
		qu.add(new int[] { 0, 0 });
		turnOn[0][0] = true;
		
		int result = 1;
		while (!qu.isEmpty()) {
			int[] cur = qu.poll();
			visited[cur[0]][cur[1]] = true;
			
			for (int[] room : switchPoint[cur[0]][cur[1]]) { // 해당 위치와 연결된 방 스위치 전부 on
				if(turnOn[room[0]][room[1]] || visited[room[0]][room[1]]) continue; 

				for (int d = 0; d < 4; d++) { // 주변에 방문한 적 있는 방이 있으면 qu에 추가
					int i = room[0] + dt[d][0];
					int j = room[1] + dt[d][1];
					if (i < 0 || j < 0 || i >= N || j >= N || !visited[i][j]) continue;
					qu.add(new int[] { room[0], room[1] });
					break;
				}
				turnOn[room[0]][room[1]] = true;
				result++;
			}
			
			for (int d = 0; d < 4; d++) { // 불 켜진 곳으로 이동
				int i = cur[0] + dt[d][0];
				int j = cur[1] + dt[d][1];
				if (i < 0 || j < 0 || i >= N || j >= N || visited[i][j] || !turnOn[i][j]) continue;
				qu.add(new int[] { i, j });
			}
		}
		System.out.println(result);
	}

}
