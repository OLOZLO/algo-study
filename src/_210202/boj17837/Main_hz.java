package _210202.boj17837;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 80% 정도에서 실패했습니다가 나옵니다...ㅠ 로직은 맞는것 같은데 대체 어느 부분이 잘못된건지 못찾겠슴다ㅠㅡㅠ

public class Main_hz {
	static int[][] dir = {{0, 0}, {0, 1}, {0, -1}, {-1, 0}, {1, 0}};
	
	public static class Mal {
		int i, j, dir;
		
		Mal(int i, int j, int dir) {
			this.i = i;
			this.j = j;
			this.dir = dir;
		}
		
		void update(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public String toString() {
			return "Pos [i=" + i + ", j=" + j + ", dir=" + dir + "]";
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		
		int[][][] map = new int[N+1][N+1][4];	// [0]엔 지도의 색 저장, [1]~[3]에 말 쌓기
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				map[i][j][0] = sc.nextInt();
			}
		}
		
		List<Mal> mals = new ArrayList<>();
		mals.add(new Mal(0, 0, 0));
		for (int k = 1; k <= K; k++) {
			int hi = sc.nextInt();
			int hj = sc.nextInt();
			int hd = sc.nextInt();
			
			map[hi][hj][1] = k;
			mals.add(new Mal(hi, hj, hd));
		}
		
		int result = -1, turn = 1;
		boolean fin = false, retry = false;
		
		while(turn <= 1000) {
			
			for (int h = 1; h < mals.size(); h++) {	// 한 턴에 말들을 한번씩 움직일껍니다
				Mal now = mals.get(h);
				int oi = now.i, oj = now.j;	// now 조작하는 일이 있어서 현재 i, j값 저장해놨어여
				
				int np = 0, cnt = 0;	// np는 현재 칸에서 내가 어느 높이에 있는지, cnt는 날 포함해서 내 위에 몇개나 잇는지!
				for (int i = 1; i < 4; i++) {
					if (map[oi][oj][i] == h)
						np = i;
					if (np != 0 && map[oi][oj][i] != 0)
						cnt++;
				}
				
				int ni = oi + dir[now.dir][0];
				int nj = oj + dir[now.dir][1];
				
				if (ni > 0 && ni <= N && nj > 0 && nj <= N) {	// 다음 위치가 지도 내의 범위일 때
					
					int si = 0;	// si는 내가 이동할 칸에서 내가 놓일 수 있는 높이
					for (int i = 1; i < 4; i++) {
						if (map[ni][nj][i] == 0) {
							si = i;
							break;
						}
					}
					
					// 이동할 수 있는 높이가 존재하지 않거나(이미 3개 쌓여있음) 이동해서 쌓았을 때 4 이상이라면 땡땡
					if (si == 0 || si+cnt-1 >= 4) {
						fin = true;
						result = turn;
						break;
					}
					
					if (map[ni][nj][0] == 0) {	// 흰색일때 순서대로 옮김
						for (int c = 0; c < cnt; c++) {
							map[ni][nj][si+c] = map[oi][oj][np+c];	// 지도에 말 위치 변경해주고
							map[oi][oj][np+c] = 0;
							
							mals.get(map[ni][nj][si+c]).update(ni, nj);	// 말 리스트에도 말 위치 변경
						}
					} else if (map[ni][nj][0] == 1) {	// 빨간색일때 역순으로 옮김
						for (int c = 0; c < cnt; c++) {
							map[ni][nj][si+c] = map[oi][oj][np+cnt-1-c];	// 지도에 말 위치 변경
							map[oi][oj][np+cnt-1-c] = 0;
							
							mals.get(map[ni][nj][si+c]).update(ni, nj);	// 말 리스트에 말 위치 변경
						}
					} else if (!retry && map[ni][nj][0] == 2) {	// 재방문 아니고 파란색일 때 방향 바꿔주고 다시 시도 ㄱ
						if (now.dir % 2 == 0) now.dir -= 1;
						else now.dir += 1;
						
						retry = true;	// 재방문해라 (재방문시 파란색이거나 맵 범위 밖일때 아무것도 안하려고 여기랑 맵 범위 밖일때 !retry 조건 걸어줌)
						h--;	// 지금 말 다시 이동시켜야되니까 h--한 후 continue
						continue;
					}
				} else if (!retry) {	// 재방문 아니고 맵 범위 밖일때 방향 바꿔주고 다시 시도 ㄱ (파란색과 동일)
					if (now.dir % 2 == 0) now.dir -= 1;
					else now.dir += 1;
					
					retry = true;
					h--;
					continue;
				}
				
				retry = false;
				
//				for (int i = 1; i <= N; i++) {
//					for (int j = 1; j <= N; j++) {
//						for (int hh = 1; hh < 4; hh++) {
//							System.out.print(map[i][j][hh]+" ");
//						}
//						System.out.print(" | ");
//					}
//					System.out.println();
//				}
//				System.out.println();
			}
			
			if (fin)
				break;
			
			turn++;
		}
		
		System.out.println(result);
		
	}

}

