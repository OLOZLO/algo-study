package _210601.boj20040;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 *   https://ddb8036631.github.io/boj/20040_사이클-게임/
 *   1. 결과 : 맞았습니다.
 *   2. 시간복잡도 : O(mlogm)
 *       - 이유 : 코드 작성한 게 크루스칼 포맷이랑 같다. 그래서 크루스칼의 시간 복잡도 O(ElogE)와 같은 맥락이지 않을까?
 *   3. 접근 방식
 *       - Union-find
 *       - 두 정점을 union 해가며, 어느 두 정점을 골랐을 때 이미 둘의 부모 정점이 같다면 사이클이 발생한 것으로 여기자.
 */

public class Main_ms {
    static int n, m;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = stoi(st.nextToken());
        m = stoi(st.nextToken());
        parent = new int[n];

        for (int i = 0; i < n; i++) parent[i] = i;

        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = stoi(st.nextToken());
            int b = stoi(st.nextToken());

            int pa = find(a);
            int pb = find(b);

            // 두 정점 a와 b의 부모 정점(pa와 pb)가 같다면, 사이클이 발생한 것.
            if (pa == pb) {
                System.out.println(i);
                System.exit(0);
            }

            // 사이클 발생 안했으면 union 해서 부모 정점을 갱신시켜주자.
            union(pa, pb);
        }

        System.out.println(0);
    }

    static int find(int x) {
        if (x == parent[x]) return x;
        return find(parent[x]);
    }

    static void union(int x, int y) {
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }

    static int stoi(String s) {
        return Integer.parseInt(s);
    }
}
