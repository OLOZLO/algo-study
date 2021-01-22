package _210119.boj14466;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_ja {
	/**
	 * [ 소가 길을 건너간 이유 6 ]
	 * 길을 건너야 만날 수 있는 소가 몇 쌍인지 출력
	 * - N*N : 목초지 (2~100)
	 * - K : 소의 수 (1~100, ~N^2)
	 * - R : 길의 수
	 * 
	 * [ 풀이 ① ]
	 * (1) 목초지 하나하나를 노드라고 생각 -> 인접행렬로 길이 있는지 없는지 판단
	 * (2) 소 위치를 저장한 행렬 생성
	 * (3) 소 한마리씩 queue에 넣어서 상하좌우 탐색 (BFS)
	 * 		IF. continue 하는 경우
	 * 			(1) 목초지 범위 넘어간 경우
	 * 			(2) 이미 방문한 목초지인 경우
	 * 			(3) 길로 연결된 목초지인 경우
	 * 		IF. 다른 소가 있는 목초지를 방문했다
	 * 			=> 다른 소를 만났는지에 대한 여부를 판단하기 위한 배열에 저장
	 * 		qu에 추가 -> 반복
	 * (4) 각각의 소들이 어떤 소를 만났는지에 대한 배열 탐색 -> 몇 쌍인지 count
	 * 
	 * [ 풀이 ② ]
	 * (1) 풀이 ①의 (1)은 동일
	 * (2) 길을 벽으로 생각하고, 연결된 목초지끼리 그룹화
	 * 		IF. continue 하는 경우
	 * 			(1) 목초지 범위 넘어간 경우
	 * 			(2) 이미 방문한 목초지인 경우
	 * 			(3) 길로 연결된 목초지인 경우
	 * (3) 해당 목초지 그룹에 속한 소가 몇마리 있는지 체크해서 배열에 저장
	 * (4) 두 그룹씩 묶을 수 있는 모든 경우에서(중복없이), 두 그룹을 곱한 값들을 한 변수에 저장 -> 몇 쌍인지 count
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		int K = in.nextInt();
		int R = in.nextInt();
		
		// (1) 인접행렬로 두개의 목초지가 길로 연결됐는지에 대한 여부 저장
		boolean[][] path = new boolean[N*N+N+1][N*N+N+1]; 
		
		for (int i = 0; i < R; i++) {
			int node1 = in.nextInt()*N+in.nextInt();
			int node2 = in.nextInt()*N+in.nextInt();
			path[node1][node2] = true;
			path[node2][node1] = true;
		}
		
		//////////////////// [ 풀이 ① ] ////////////////////////
		// (2) 소 위치를 저장한 행렬 생성
		// 		=> 해당 소와 만났는지 판단하기 위함
		int[][] cows = new int[N+1][N+1]; 
		Queue<int[]> quCows = new LinkedList<>();
		
		for (int i = 1; i <= K; i++) {
			int cowi = in.nextInt();
			int cowj = in.nextInt();
			cows[cowi][cowj] = i;
			quCows.add(new int[] {cowi,cowj});
		}
		int[] di = {0,0,1,-1};
		int[] dj = {1,-1,0,0};
		
		// (3) 소 한마리씩 다른 소 만나러 출발 => 상하좌우 탐색
		boolean[][] check = new boolean[K+1][K+1]; // 만난 소 체크
		for (int cowName = 1; cowName <= K; cowName++) {
			
			boolean[][] visited = new boolean[N+1][N+1]; // 방문 목초지 체크
			Queue<int[]> qu = new LinkedList<>();
			qu.add(quCows.poll());
			int meetCnt = 1; // 해당 소가 만난 소의 수, 자신 포함
			
			while(!qu.isEmpty()) {
				int[] cow = qu.poll();
				visited[cow[0]][cow[1]] = true;
				if(meetCnt==K) break; // 전부 만났으면 탐색 중지
				
				for (int d = 0; d < 4; d++) {
					int i = di[d] + cow[0];
					int j = dj[d] + cow[1];
					if(i<1 || j<1 || i>N || j>N || visited[i][j]) continue;
					// 길이 존재하는 경우
					int pi = cow[0]*N+cow[1];
					int pj = i*N+j;
					if(path[pi][pj]) continue;
					
					// 다른 소를 만난 경우
					if(cows[i][j] != 0) {
						check[cowName][cows[i][j]] = true;
						meetCnt++;
					}
					qu.add(new int[] {i,j});
					visited[i][j] = true;
				}
			}
		}
		
		// 길을 건너지 못하면 만나지 못하는 소들의 쌍 계산
		// true면 만난 사이, false면 못 만난 사이
		int result = 0;
		for (int i = 1; i < K; i++) {
			for (int j = i+1; j < K+1; j++) {
				if(!check[i][j]) result++;
			}
		}
		System.out.println(result);
		
		//////////////////// [ 풀이 ① ] END ////////////////////////
		
		//////////////////// [ 풀이 ② ] ////////////////////////
//		/*
		// (2) 길을 벽으로 생각하고, 연결된 목초지끼리 그룹화
		int[][] group = new int[N+1][N+1];
		boolean[][] visited = new boolean[N+1][N+1];
		
		Queue<int[]> qu = new LinkedList<>();
		
		int name = 1;
		for (int i = 1; i < N+1; i++) {
			for (int j = 1; j < N+1; j++) {
				if(visited[i][j]) continue;
				qu.add(new int[] {i,j});
				visited[i][j] = true;
				group[i][j] = name;
				
				while(!qu.isEmpty()) {
					int[] cow = qu.poll();
					for (int d = 0; d < 4; d++) {
						int ii = cow[0] + di[d];
						int jj = cow[1] + dj[d];
						if(ii<1|| jj<1 || ii>N || jj>N || visited[ii][jj]) continue;
						// 길로 연결된 목초지면 pass
						int pi = cow[0]*N+cow[1];
						int pj = ii*N+jj;
						if(path[pi][pj]) continue;
						
						qu.add(new int[] {ii,jj});
						visited[ii][jj] = true;
						group[ii][jj] = name;
					}
				}
				name++;
			}
		}
		
		// (3) 해당 목초지 그룹에 속한 소가 몇마리 있는지 체크해서 배열에 저장
		int[] groupCow = new int[name];
		for (int i = 0; i < K; i++) {
			int[] cow = new int[]{in.nextInt(),in.nextInt()};
			groupCow[group[cow[0]][cow[1]]]++;
		}
		
		// (4) 길을 건너야 만날 수 있는 소들이 몇 쌍인지 계산
		int sum = 0;
		for (int i = 1, len = groupCow.length; i < len; i++) {
			for (int j = i+1; j < len; j++) {
				sum += groupCow[i] * groupCow[j];
			}
		}
		System.out.println(sum);
		
//		*/
		/////////////////// [ 풀이 ② ] END ////////////////////////
	}

}
