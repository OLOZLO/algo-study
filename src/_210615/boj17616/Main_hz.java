package _210615.boj17616;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_hz {

    /**
     * [골3] 등수 찾기
     *
     * 1. 결과 : 메모리 초과 -> 성공
     * 2. 시간복잡도 : 모르겠어요...
     * 3. 풀이
     *    : 그래프 탐색을 통해 X학생보다 등수가 낮은 학생, 높은 학생들을 카운트하여 X의 가장 높은 등수, 가장 낮은 등수를 계산했다.
     *    처음에는 N의 범위가 최대 10^5라는 것을 제대로 읽지 않아 boolean 형태의 인접행렬로 구현하여서 메모리 초과가 발생했다.
     *
     * */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());

        List<Integer>[] adj = new ArrayList[N+1];   // 해당 학생보다 등수가 낮은 학생들을 저장하는 인접리스트
        List<Integer>[] reverse = new ArrayList[N+1];   // 해당 학생보다 등수가 높은 학생들을 저장하는 인접리스트 

        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList();
            reverse[i] = new ArrayList();
        }

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adj[a].add(b);
            reverse[b].add(a);
        }

        Queue<Integer> q = new LinkedList<>();

        // Set을 사용하면 따로 카운팅 변수를 생성하지 않아도 된다 생각하여 Set으로 방문처리
        // X보다 낮은 등수의 학생들 구하기
        HashSet<Integer> low = new HashSet<>();

        for (int i : adj[X]) {
            low.add(i);
            q.add(i);
        }

        while(!q.isEmpty()) {
            int now = q.poll();

            for (int x : adj[now]) {
                if (!low.contains(x)) {
                    low.add(x);
                    q.add(x);
                }
            }
        }

        // X보다 높은 등수의 학생들 구하기
        HashSet<Integer> high = new HashSet<>();

        for (int i : reverse[X]) {
            high.add(i);
            q.add(i);
        }

        while (!q.isEmpty()) {
            int now = q.poll();

            for (int x : reverse[now]) {
                if (!high.contains(x)) {
                    high.add(x);
                    q.add(x);
                }
            }
        }

        System.out.println((high.size()+1)+" "+(N-low.size()));
    }
}
