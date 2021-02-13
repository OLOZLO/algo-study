package _210211.boj2931;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ja {
	/**
	 * [ 가스관 ]
	 * 지워진 파이프 찾기
	 * 
	 * [ 풀이 ] 
	 * 1. Z,W을 시작으로 bfs를 돌릴거야 (상하좌우 탐색) -> Z,W 둘 중 하나로 시작하면 안댐! 시작점 근처 파이프가 지워질 수 있으니까.
	 * 	  => 이떄, 내가 위치한 파이프를 기준으로 연결된 곳만 방문할거임
	 * 2. 파이프 기준으로 방문하니까, 파이프 없는 곳('.')이 곧 지워진 파이프임!
	 * 3. 지워진 파이프 찾았으니까, 그 곳 근처에있는 파이프로 지워진 파이프의 모양을 유추해볼거임
	 * 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int R = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		
		int[][] map = new int[R][C];
		int[][] dx = {{-1,0},{1,0},{0,-1},{0,1}}; // 상하좌우
		int[] reverseDx = {1,0,3,2}; // 상하좌우 반대편
		
		// 파이프 명칭
		char[] block = {'0', '1', '2', '3', '4', '|', '-', '+'};
		
		// 파이프 모양 
		// 0~7까지, 0 '1' '2' '3' '4' '|' '-' '+' 순임, 0은 노연결!
		// 그 안에 데이터는 상하좌우 순 (boolean)
		boolean[][] pipe = {{false,false,false,false} 
		,{false,true,false,true}
		,{true,false,false,true}
		,{true,false,true,false}
		,{false,true,true,false}
		,{true,true,false,false}
		,{false,false,true,true}
		,{true,true,true,true}};  
		
		Queue<int[]> qu = new LinkedList<>();
		boolean[][] visited = new boolean[R][C];
		
		// 데이터 저장
		for (int i = 0; i < R; i++) {
			String str = br.readLine();
			for (int j = 0; j < C; j++) {
				char c = str.charAt(j);
				if(c=='.')map[i][j]=0;
				else if(c=='|') map[i][j]=5; // 접근 편하게 숫자로 집어넣음
				else if(c=='-') map[i][j]=6;
				else if(c=='+') map[i][j]=7;
				else if(c=='Z'||c=='M') {
					map[i][j] = -1;
					qu.add(new int[] {i,j});
					visited[i][j] = true;
				}
				else map[i][j]=c-'0';
			}
		}
		
		// 먼저 Z,W에 연결된 파이프를 qu에 저장할거임!
		for (int s = 0; s < 2; s++) {
			int[] start = qu.poll();
			for (int d = 0; d < 4; d++) {
				int i = start[0] + dx[d][0];
				int j = start[1] + dx[d][1];
				if(i<0||j<0||i>=R||j>=C||map[i][j]<=0) continue;
				qu.add(new int[]{i,j});
				visited[i][j] = true;
			}
		}
		
		
		while(!qu.isEmpty()) {
			int[] point = qu.poll();
			for (int d = 0; d < 4; d++) {
				if(!pipe[map[point[0]][point[1]]][d]) continue; // 혅재 위치에서 갈수없는 방향이면 pass
				
				int i = point[0] + dx[d][0];
				int j = point[1] + dx[d][1];
				if(i<0||j<0||i>=R||j>=C||visited[i][j]) continue;
				if(map[i][j]==0) { // 지워진 파이프 여기있넹!
					// 지워진 파이프의 모양을 유추해볼거야.
					// 지워진 파이프의 상하좌우 파이프 모양을 활용
					boolean[] result = new boolean[4];
					for (int dd = 0; dd < 4; dd++) {
						int ii = i + dx[dd][0];
						int jj = j + dx[dd][1];
						if(ii<0||jj<0||ii>=R||jj>=C||map[ii][jj]<=0) continue;
						// [EX] 지워진 파이프의 왼쪽 방향 막힘여부는, 그 방향 파이프의 오른쪽 방향 막힘 여부와 동일
						result[dd] = pipe[map[ii][jj]][reverseDx[dd]];
					}
					// 지워진 파이프의 상하좌우 막힘 여부 아니까, 그걸로 이제 어떤 모양인지 찾을거임
					for (int idx = 0; idx < 8; idx++) {
						if(Arrays.equals(result, pipe[idx])){
							System.out.println((i+1) + " " + (j+1) + " " + block[idx]);
							return;
						}
					}
					return;
				}
				qu.add(new int[]{i,j});
				visited[i][j] = true;
			}
		}
	}
}
