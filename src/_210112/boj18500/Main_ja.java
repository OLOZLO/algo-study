package _210112.boj18500;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_ja {
	/**
	 * 모든 막대를 던지고 난 이후에 미네랄 모양을 구하는 프로그램 
	 * 막대로 미네랄 부수기
	 *
	 * 막대를 던진 횟수 N (1~100) 
	 * 막대 던지는 순서 
	 * 1. 왼 -> 오 
	 * 2. 오 -> 왼 
	 * 3. 1,2번 반복
	 *
	 * ACTION. 막대로 맞은 미네랄은 파괴 
	 * IF. 공중에 떠있는 클러스터 발생 { 
	 * 다른 클러스터나, 땅을 만날때까지 계속 떨어짐 
	 * - 떨어지는 동안 클러스터 모양은 유지 
	 * - 두 개 또는 그 이상의 클러스터가 동시에 떨어지는 경우는 없음 
	 * }
	 * 
	 * [조건] 
	 * 동굴 R*C (R,C: 1~100) 
	 * '.' 빈칸, 
	 * 'x' 미네랄
	 * 
	 * 높이 1 : 행렬바닥 
	 * R : 맨 위
	 * 
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int R = in.nextInt();
		int C = in.nextInt();

		char[][] data = new char[R][C]; 

		for (int i = 0; i < R; i++) {
			String str = in.next();
			for (int j = 0; j < C; j++) {
				data[i][j] = str.charAt(j);
			}
		}

		
		// 막대 던진 횟수
		int N = in.nextInt();

		// 막대, 던질차례
		int stick = 1; // 1:왼, -1:오

		for (int n = 0; n < N; n++) {
			// 막대 던질 높이 (바닥:R, 맨위:0)
			int attack = R - in.nextInt();
			
			// 던질사람 (왼, 오)
			int man = stick == 1 ? 0 : (C - 1);
			
			// 1. 막대 던짐 -> 맞은 미네랄을 찾아서 start 배열에 저장
			int[] start = new int[2];
			for (int i = 0; i < C; i++) {
				int j = man + i*stick;
				if(data[attack][j] == 'x') {
					data[attack][j] = '.';
					start = new int[] {attack, j};
					break;
				}
			}
			
			int[] di = { 1, -1, 0, 0 };
			int[] dj = { 0, 0, 1, -1 };
			
			// 2. 막대를 던진 후, 맞은 미네랄을 기준으로 클러스터 탐색
			// 공중에 있는 클러스터가 있는지 판단.
			
			// 공중에 있는, 움직여야하는 클러스터 ArrayList에 저장
			ArrayList<int[]> move = new ArrayList<>();
			
			// 클러스터 방문여부 확인
			// 부서진 미네랄 기준으로 최대 4가지의 클러스터가 나올 수 있음
			// 1-4 번호를 지정하여 클러스터 구분(반복 체크 방지 및 자신이 속한 클러스터 그룹을 체크하기 위함)
			int[][] visited = new int[R][C];
			
			// 클러스터를 그룹별로 체크하기 위해서 부서진 미네랄 기준으로 한 방향씩 BFS로 돌림
			for (int s = 4; s >= 1; s--) {
				Queue<int[]> qu = new LinkedList<>();
				
				int si= start[0] + di[s-1];
				int sj= start[1] + dj[s-1];
				// 행렬 범위를 넘어가거나, 바닥에 있거나, 빈칸이거나, 이미 방문한 곳이면 continue;
				if (si < 0 || sj < 0 || si >= R-1 || sj >= C || data[si][sj] == '.' || visited[si][sj]!=0) continue;
				
				qu.add(new int[] {si,sj});
				move.add(new int[] {si,sj});
				visited[si][sj] = s;
				
				while (!qu.isEmpty()) {
					int[] p = qu.poll();
					for (int d = 0; d < 4; d++) {
						int ii = p[0] + di[d];
						int jj = p[1] + dj[d];
						if (ii < 0 || jj < 0 || ii >= R || jj >= C || data[ii][jj] == '.') continue;
						// 클러스터 탐색 중, 바닥 혹은 이미 탐색한 클러스터(바닥에 붙어있는)를 만났다? => 공중에 떠있는 클러스터 존재 X break
						if(ii == (R-1) || visited[ii][jj] > s) {
							qu.clear();
							move.clear();
							break;
						}
						// 방문한적 없는 미네랄인 경우
						if(visited[ii][jj] == 0) {
							qu.add(new int[] {ii,jj});
							move.add(new int[] {ii,jj});
							visited[ii][jj] = s;
						}
					}
				}
//				for (int ii = 0; ii < R; ii++) {
//					for (int jj = 0; jj < C; jj++) {
//						System.out.print(data[ii][jj]);
//					}
//					System.out.println();
//				}
//				System.out.println("----------------------");
				
				// 열방향 탐색
				// 최소 움직일 수 있는 값 구하기 (다른 클러스터, 바닥 만나면 stop)
				int min = Integer.MAX_VALUE;
				if(move.size() != 0) {
					for (int i = 0; i < move.size(); i++) {
						int[] m = move.get(i);
						int mi = m[0]+1, mj = m[1];
						int cnt = 0;
						while(true) {
							if(mi==R || (data[mi][mj]=='x' && visited[mi][mj] !=s)) break;
							cnt++;
							mi++;
							if(min <= cnt) break;
						} 
						min = Math.min(min, cnt);
						// 만약, 최소가 1이다. break
						if(min == 1) break;
					}
					// min값 만큼 옮기기
					for(int[] p : move) {
						data[p[0]][p[1]] = '.';
						data[p[0]+min][p[1]] = 'x';
					}

					break;
				}
			}
			// 막대 던질 차례 바꾸기
			stick *= -1;
		}
		for (int ii = 0; ii < R; ii++) {
			for (int jj = 0; jj < C; jj++) {
				System.out.print(data[ii][jj]);
			}
			System.out.println();
		}
	}
}
