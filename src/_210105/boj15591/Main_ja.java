package _210105.boj15591;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// mootube
public class Main_ja {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N=in.nextInt(); // 정점의 갯수
		int Q=in.nextInt(); // 질문 수
		
		ArrayList<ArrayList<int[]>> mootube = new ArrayList<>();
		
		for(int i=0; i<N+1; i++)
			mootube.add(new ArrayList<>());
		
		for(int i=0; i<N-1; i++) {
			int p=in.nextInt();
			int q=in.nextInt();
			int r=in.nextInt();

			mootube.get(p).add(new int[]{q,r});
			mootube.get(q).add(new int[]{p,r});
		}
		
		for(int i=0; i<Q; i++) {
			int K = in.nextInt();
			int start = in.nextInt();
			
			boolean[] visited = new boolean[N+1];
			
			Queue<Integer> qu = new LinkedList<>();
			qu.add(start);
			visited[start] = true;
			int result = 0;
			while(!qu.isEmpty()) {
				int size = qu.size();
				for(int s = 0; s<size; s++) {
					int vs = qu.poll();
					for(int j=0; j<mootube.get(vs).size(); j++) {
						int ve = mootube.get(vs).get(j)[0];
						if(visited[ve] || mootube.get(vs).get(j)[1] < K) continue;
						visited[ve] = true;
						result++;
						qu.add(ve);
					}
				}
			}
			
			System.out.println(result);
		}
	}
}
