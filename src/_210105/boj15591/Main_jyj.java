package _210105.boj15591;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_jyj {
	static int N, Q;

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		StringBuilder answer = new StringBuilder();
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		ArrayList<info>[] mootube = new ArrayList[N + 1];

		for (int i = 1; i <= N; i++) {
			mootube[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int p = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
			mootube[p].add(new info(q, r));
			mootube[q].add(new info(p, r));
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int k = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());

			int count = 0;
			Queue<Integer> queue = new LinkedList<>();
			boolean[] visited = new boolean[N + 1];
			visited[v] = true;
			queue.offer(v);
			
			
			while (!queue.isEmpty()) {
				int cur = queue.poll();

				for (int j = 0; j < mootube[cur].size(); j++) {
					int next = mootube[cur].get(j).idx;

					if (visited[next])
						continue;

					int nextVal = mootube[cur].get(j).val;

					if (nextVal >= k) {
						visited[next] = true;
						count++;
						queue.offer(next);
					}
				}
			}

			answer.append(count).append('\n');
		}

		System.out.println(answer);
	}

	private static class info {
		int idx;
		int val;

		public info(int idx, int val) {
			this.idx = idx;
			this.val = val;
		}
	}

}
