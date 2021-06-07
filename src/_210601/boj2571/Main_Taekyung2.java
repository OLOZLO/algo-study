package _210601.boj2571;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [골드4] 사이클 게임
 * 1. 결과 : 맞았습니다.
 * 2. 시간복잡도 : O(MlogM)
 *      - 랭크 작업을 안해서 더 느리긴 할 듯
 * 3. 후기
 * 	- 크루스칼 원리를 생각해서 풀었음니다
 *
 */

public class Main_Taekyung2 {
    static int N, M;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        parent = new int[N];
        int ret = 0;

        for(int i = 0; i < N; i++)
            parent[i] = i;

        for(int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            // 사이클이 생겼음, 종료
            if(!merge(u, v)) {
                ret = i;
                break;
            }
        }

        System.out.println(ret);
    }

    // 일반적인 유니온 파인드
    public static int find(int a) {
        if(parent[a] == a) return a;
        return parent[a] = find(parent[a]);
    }

    // 머지 불가능하면 false 반환, 이미 있는 노드이므로 사이클이 생긴다
    public static boolean merge(int a, int b) {
        a = find(a);
        b = find(b);
        if(a == b) return false;
        parent[a] = b;
        return true;
    }
}
