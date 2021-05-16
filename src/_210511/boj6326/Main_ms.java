package _210511.boj6326;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
*   1. 결과 : 실패(18%)
*   2. 시간복잡도 : O(N*logN)
*       - 이유 : 각 이분 탐색마다 O(N)의 시간 복잡도를 가짐.
*   3. 접근 방식
*       - 이분 탐색.
*       - mid 값으로 확인된 인출 횟수가 M 이상이면, answer에 담는다.
*   4. 후기
*       - 아직도 이분 탐색 문제에서 경계값에 대한 처리를 어떻게 해줘야할 지 잘 모르겠다.
*/

public class Main_ms {
    static int N, M;
    static int[] costs;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        costs = new int[N];

        for (int i = 0; i < N; i++) costs[i] = Integer.parseInt(br.readLine());

        // low : 금액이 최소 1이니까 1로 설정.
        // high : 최대 10,000 금액이 100,000개 있을 수 있으므로 10^9
        int low = 1, high = (int) 1e9, answer = 0;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (check(mid) >= M) {  // 인출 횟수가 M번 이상이면, 더 큰 금액을 출금해봐야 하므로 Lowerbound 조정.
                answer = mid;
                low = mid + 1;
            } else {                // 인출 횟수가 M번 미만이면, 더 적은 금액을 출금해봐야 하므로 Upperbound 조정.
                high = mid - 1;
            }
        }

        System.out.println(answer);
    }

    static int check(int mid) {
        int remain = mid;
        int cnt = 1; // 인출은 최소 한 번 일어남. 1로 시작하자.

        for (int i = 0; i < N; i++) {
            if (remain - costs[i] < 0) { // 커버 못하면 새로 뽑아 써야 함.
                remain = mid;
                cnt++;
            }

            remain -= costs[i]; // 남은 금액 적용시켜주자.
        }

        return cnt; // 인출 횟수 리턴.
    }
}