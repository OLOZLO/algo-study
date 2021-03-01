package _210302.boj16954;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main_ja {
	/**
	 * [ 움직이는 미로 탈출 ]
	 * - 8*8 체스판 탈출
	 * - 빈칸 아니면 벽
	 * - 캐릭터 
	 * 	  가장 왼쪽 아랫칸  -> 가장 오른쪽 윗칸 이동
	 *   이동방향 : 상하좌우,대각선 이동 혹은 가만히
	 *  
	 * - 벽
	 * 	 이동방향 : 아래
	 * 	 -> 바닥에 닿으면 사라짐
	 * 	 -> 캐릭터에 닿으면 더이상 캐릭터 이동 x
	 * 
	 * - 클리어 가능하면 1, 아니면 0 return
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N  = 8;
		char[][] map = new char[N][N];
		
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < N; j++) {
				map[i][j] = str.charAt(j);
			}
		}
		int move = 0;
		int[][] dx = {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1},{0,0}};
		
		int[] end = {0,N-1};
		
		Queue<int[]> qu = new LinkedList<>();
		qu.add(new int[] {N-1,0});
		
		while(!qu.isEmpty()) {
			int size = qu.size();
			boolean[][] visited = new boolean[N][N]; // 이거 안해도 통과는 하드라 ㅎㅎ;;
			
			for (int s = 0; s < size; s++) {
				int[] man = qu.poll();
				if(man[0]==end[0] && man[1]==end[1]) {
					System.out.println(1);
					System.exit(0);
				}
				for (int d = 0; d < 9; d++) { // 상하좌우, 대각선, 그대로
					int i = man[0] + dx[d][0];
					int j = man[1] + dx[d][1];
					if(i<0||j<0||i>=N||j>=N||visited[i][j]) continue;
					if(i-move >= 0 && map[i-move][j]=='#') continue; // 캐릭터가 이동하려는 자리가 벽이 있는 자리면
					if(i-(move+1) >= 0 && map[i-(move+1)][j]=='#') continue; // 캐릭터 이동려는 자리가 벽이 다음에 이동할 자리면
					qu.add(new int[] {i,j});
					visited[i][j] = true;
				}
			}
			move++;
		}
		
		System.out.println(0);
	}
}
