package _210601.boj20040;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(MlogM)
 *               크루스칼 알고리즘 사용
 *               시간 복잡도 ElogE 이므로 여기서는 간선이 M개
 *
 * 3. 접근 방식
 *      선분을 그릴 때마다 같은 집합으로 만들어 줌
 *      만약 선에 있는 두 점이 같은 집합이라면 선분이 생기는 순간 싸이클이 발생 => 해당 순번을 출력
 *
 */
public class Main_girawhale {
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // parent 초기화
        parent = IntStream.range(0, N).toArray();

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            if (find(n1) == find(n2)) { // 같은 집합이면 종료
                System.out.println(i);
                return;
            }
            union(n1, n2); // 아니라면 선분을 이어 같은 집합으로 만들어 줌
        }

        System.out.println(0);
    }

    static int find(int n) {
        return parent[n] = parent[n] == n ? n : find(parent[n]);
    }

    static void union(int n1, int n2) {
        int p1 = find(n1), p2 = find(n2);

        if (p1 < p2) parent[p2] = p1;
        else parent[p1] = p2;
    }
}
