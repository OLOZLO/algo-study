package _210209.boj9370;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_girawhale {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken()), m = Integer.parseInt(st.nextToken()), t = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()), g = Integer.parseInt(st.nextToken()), h = Integer.parseInt(st.nextToken());

            List<int[]>[] adj = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

            while (m-- > 0) {
                st = new StringTokenizer(br.readLine());
                int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken()), d = Integer.parseInt(st.nextToken());
                adj[n1].add(new int[]{n2, d});
                adj[n2].add(new int[]{n1, d});
            }

            Set<Integer> dest = new HashSet<>(); // 도착지를 저장할 Set. 있는지 없는지 체크해야되는데 순차탐색하면 걸리니까 찾을 때 O(1)인 Set 사용
            TreeSet<Integer> ans = new TreeSet<>(); // 혹시 중복으로 도착지 넣을까봐 Set으로 함, 순차적으로 출력해야되서 TreeSet으로 함 
            while (t-- > 0)
                dest.add(Integer.parseInt(br.readLine()));

            int[] dist = new int[n + 1];
            Arrays.fill(dist, Integer.MAX_VALUE);

            PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] != o2[1] ? // 길이 짧은 순, g-h를 방문한거 먼저!
                    Integer.compare(o1[1], o2[1]) : Integer.compare(o2[2], o1[2]));

            queue.add(new int[]{s, 0, -1}); // s부터 출발, 현재 거리 : 0 , g-h 방문 안함

            while (!queue.isEmpty()) {
                int[] cur = queue.poll();

                if (dist[cur[0]] <= cur[1]) continue; 
                dist[cur[0]] = cur[1];
                if (dest.contains(cur[0]) && cur[2] == 1) // 도착지 목록에 현재 지점이 있고, cur[2] == 1이면 g-h 방문 완
                    ans.add(cur[0]); // 답에 추가

                for (int[] a : adj[cur[0]]) {
                    if (cur[1] + a[1] > dist[a[0]]) continue;

                    if (cur[0] == g && a[0] == h || cur[0] == h && a[0] == g) // 현재 길이 g-h면 1로
                        queue.add(new int[]{a[0], cur[1] + a[1], 1});
                    else queue.add(new int[]{a[0], cur[1] + a[1], cur[2]}); // 아니면 그대로
                }
            }

            ans.forEach(i -> System.out.print(i + " "));
            System.out.println();
        }
    }
}
