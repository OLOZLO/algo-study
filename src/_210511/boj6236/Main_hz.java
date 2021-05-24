package _210511.boj6236;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_hz {

    /*
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(log1_000_000_000)
     * 3. 풀이 :
     *    N일동안 사용할 금액 중 최대값 ~ N*10_000 사이의 값이 정답이 될 것이라 생각하여 이분탐색을 사용했습니다.
     *    현재 가진 돈으로 하루를 보낼 수 없는 경우만 돈을 꺼내고 카운팅한 후 그 횟수가 M보다 작을 경우 억지로 돈을 인출하여 정답을 만들 수 있으므로
     *    "정확히 M번을 맞추기 위해서 남은 금액이 그날 사용할 금액보다 많더라도 남은 금액은 통장에 집어넣고 다시 K원을 인출할 수 있다." 라는 문장을 무시했습니다.
     *    따라서 추측한 K값으로 인출한 횟수가 M보다 작거나 같은 결과로 임시 저장한 뒤 범위를 왼쪽으로 줄여주었고,
     *    그렇지 않은 경우 범위를 오른쪽으로 줄여 정답을 구했습니다.
     *
     * */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] day = new int[N];
        int start = 0;
        for (int i = 0; i < N; i++) {
            day[i] = Integer.parseInt(br.readLine());
            start = Math.max(start, day[i]);
        }

        int end = N*1000;
        int result = Integer.MAX_VALUE;
        while(start <= end) {
            int mid = (start+end)/2;
            int left = 0;
            int cnt = 0;

            for (int i = 0; i < N; i++) {
                if (left < day[i]) {
                    cnt++;
                    left = mid;
                }

                left -= day[i];
            }

            if (cnt <= M) {
                end = mid -1;
                result = Math.min(result, mid);
            }
            else {
                start = mid+1;
            }
        }

        System.out.println(result);



        
    }
}
