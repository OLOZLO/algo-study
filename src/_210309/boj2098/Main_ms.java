package _210309.boj2098;

import java.util.Arrays;
import java.util.Scanner;

public class Main_ms {
    static int N;
    static int[][] W;
    static int[][] dp;
    static final int INF = 987654321;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        W = new int[N][N];
        dp = new int[N][1 << N];

        for (int i = 0; i < N; i++) Arrays.fill(dp[i], INF);

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                W[i][j] = sc.nextInt();

        System.out.println(TSP(0, 1));
    }

    // TSP : 재귀 함수로써, 모든 정점들을 방문하며 드는 비용들의 최소를 dp 배열에 갱신함.
    // 첫 번째 인자는 "현재 방문 노드"를, 두 번재 인자는 "현재 방문 노드까지를 포함한 방문된 노드들"을 가리킴.
    // 두 번째 인자 visit은 비트 표현을 10진수 정수로 바꿔 표현한 것.
    //      > 예를 들어, 0번, 1번, 3번 노드들이 방문된 상태면 1011(2)로 표기되며, 11(10)으로 전달됨.
    static int TSP(int now, int visit) {
        if (dp[now][visit] != INF) return dp[now][visit];                   // visit 정점들을 찍고, now 정점을 방문한 이력이 있으면? 그거 그냥 리턴. 이미 구해둔 거 갖다 쓰자.

        // visit == (1 << N) - 1이면 모든 정점들을 방문한 상태임.
        //      예를 들어, N이 4이면, 1111(2) 와 같이 모든 비트가 다 켜지면 모든 정점들이 방문 처리된 상태.
        //      이 때, 이 값은 15(10)이며, 1을 4번 좌쉬프트한 16에서 1을 뺀 값과 같음.
        // 마지막으로 방문한 정점(now)에서 최초 0번 정점으로 갈 수 없으면 해당 W[now][0]은 0일 것임. 이러면 이 경로로는 못돌아가니까 INF를 반환하자.
        // W[now][0]이 0이 아니면, 마지막으로 방문한 정점에서 0으로 돌아가는 길이 존재하는 것이므로, 해당 비용을 반환하자.
        if (visit == (1 << N) - 1) return W[now][0] == 0 ? INF : W[now][0];

        int min = INF;

        // 모든 정점에 대해 진행해보며, 비용을 dp에 갱신.
        for (int next = 0; next < N; next++) {
            // visit & (1 << next) != 0이면, 다음 정점 next는 이미 방문한 상태이므로 PASS.
            // W[now][next] == 0이면, 다음 정점 next로는 이어진 간선이 없으므로 PASS.
            if ((visit & (1 << next)) != 0 || W[now][next] == 0) continue;

            // 다음 정점(next)을 바꿔가며 TSP를 호출해 비용을 계산한 결과들의 최소값을 min에 저장한다.
            // W[now][next] + TSP(next, visit | (1 << next))
            //      > W[now][next]                      : 다음 정점까지 이어진 간선의 비용
            //      > TSP(next, visit | (1 << next))    : next를 시작으로 끝까지 진행했을 때의 최소 비용.
            //      > OR(|) 연산을 통해 next 정점에 대한 비트를 켠 값을 인자로 전달한다.
            min = Math.min(min, W[now][next] + TSP(next, visit | (1 << next)));
        }

        return dp[now][visit] = min; // 최소값을 갱신해주고 그 값을 리턴.
    }
}
