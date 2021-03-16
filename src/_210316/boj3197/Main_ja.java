package _210316.boj3197;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class Main_ja {
	/**
	 * [ 백조의 호수 ]
	 * 하루가 지날 때 마다 물에 닿은 얼음은 사라짐
	 * 두 마리의 백조가 며칠이 지나야 만날 수 있는지 return
	 */
	static int stoi(String str) { return Integer.parseInt(str); }
	static int R,C;
	static int[][] dt = {{0,1},{0,-1},{1,0},{-1,0}};
	static final int   WATER = 0, SWAN = -1, ICE = -2, CHECK_ICE = -3;
	static int groupName = 1;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		R = stoi(st.nextToken());
		C = stoi(st.nextToken());
		
		int[][] map = new int[R][C];
		for (int i = 0; i < R; i++) {
			String str = br.readLine();
			for (int j = 0; j < C; j++) {
				char c = str.charAt(j);
				map[i][j] = c=='X'? ICE : c=='.'? WATER : SWAN; // 숫자로 변환에서 저장
			}
		}
		
		Queue<Node> qu = new LinkedList<>(); // '.'(WATER)와 인접한 'X'(ICE)를 집어넣어 넣을거임
		ArrayList<Integer> swan = groupingMap(map, qu); // 인접한 '.'(WATER)끼리 그룹화 , return : 백조들이 속한 그룹
		
		int[] parent = new int[groupName]; // union-find
		for (int i = 0; i < groupName; i++) 
			parent[i] = i; // 부모는 자기자신으로 초기화
		
		int day = 0;
		boolean[][] visited = new boolean[R][C];
		while(!qu.isEmpty()) {
			if(findParent(parent, swan.get(0)) == findParent(parent, swan.get(1))) { // 두 마리의 백조들의 그룹이 동일하면 end
				System.out.println(day);
				System.exit(0);
			}
			int size = qu.size(); // 하루 단위로 순회
			day++;
			for (int s = 0; s < size; s++) {
				Node cur = qu.poll();
				int curi = cur.point[0];
				int curj = cur.point[1];
				
				map[curi][curj] = cur.group;
				for (int d = 0; d < 4; d++) {
					int i = cur.point[0] + dt[d][0];
					int j = cur.point[1] + dt[d][1];
					if (i < 0 || j < 0 || i >= R || j >= C || map[i][j] == CHECK_ICE) continue; // CHECK_ICE는 방문한 ICE임.
					if(map[i][j] == ICE) {
						map[i][j] = CHECK_ICE;
						qu.add(new Node(new int[] {i,j}, cur.group));
						continue;
					}
					if(cur.group != map[i][j]) { // 다른 영역이랑 만나면, 합치러 갈거임
						unionParent(parent, map[i][j], cur.group);
					}
				}
			}
		}
	}
	
	static class Node{
		int[] point;
		int group;
		
		public Node(int[] point, int group) {
			this.point = point;
			this.group = group;
		}
	}

	// . : 0, X : -1, L : -2
	static ArrayList<Integer> groupingMap(int[][] map, Queue<Node> borderline) {
		ArrayList<Integer> swan = new ArrayList<>();
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if(map[i][j] > 0 || map[i][j] <= ICE) continue;
				
				// bfs 순회
				Queue<int[]> qu = new LinkedList<>();
				qu.add(new int[] {i,j});
				if(map[i][j] == SWAN) 
					swan.add(groupName);
				map[i][j] = groupName;
				
				while(!qu.isEmpty()) {
					int[] point = qu.poll();
					
					for (int d = 0; d < 4; d++) {
						int ii = point[0] + dt[d][0];
						int jj = point[1] + dt[d][1];
						
						if (ii < 0 || jj < 0 || ii >= R || jj >= C || map[ii][jj] > 0 || map[ii][jj] == CHECK_ICE) continue;
						
						if (map[ii][jj] == ICE) { 
							borderline.add(new Node(new int[]{ii,jj}, groupName)); // 물이랑 닿아있는 얼음 저장
							map[ii][jj] = CHECK_ICE; // 방문처리
							continue;
						}
						if(map[ii][jj] == SWAN) // 백조 저장
							swan.add(groupName);
						map[ii][jj] = groupName;
						qu.add(new int[] {ii,jj});
					}
				}
				groupName++;
			}
		}
		return swan;
	}
	static int findParent(int[] parent, int x) { // 최상위 부모 찾기
		if(parent[x] == x) return x;
		return parent[x] = findParent(parent, parent[x]);
	}
	static void unionParent(int[] parent, int a, int b) { // 두 요소 부모 합치기
		if(a==b) return;
		a = findParent(parent, a);
		b = findParent(parent, b);
		if(a<b) parent[b] = a;
		else parent[a] = b;
	}
}
