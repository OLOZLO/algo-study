package _210525.boj14718;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_hz {

    /**
     * [골3] 용감한 용사 진순
     *
     * 1. 결과 : 시간초과 -> 성공
     * 2. 시간복잡도 : O(N^4)
     * 3. 풀이
     *    - 시간초과 풀이법
     *      - 조합을 통해 K명의 병사를 구해 진수의 스탯 포인트 계산
     *      - 당연히 100!/50!50! 이여서 시간 초과가 나는데 왜 될 꺼라 생각했을까요...
     *    - 송히가 조합의 기준을 사람 대신 능력치로 바꿔보라 했지만 모르겠어서 답을 참고했습니다.
     *      - 3중 포문을 통해 진수의 스탯 포인트 조합을 구합니다.
     *      - 이 때 몇 명의 병사를 이길 수 있는지 카운팅 한 후 K명 이상을 이길 수 있으면 민수의 스탯 포인트를 최솟값으로 갱신합니다.
     *
     * */


    public static int stoi(String s) {return Integer.parseInt(s);}
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = stoi(st.nextToken());
        int K = stoi(st.nextToken());

        int[][] power = new int[N][3];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                power[i][j] = stoi(st.nextToken());
            }
        }

        int result = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    // 현재 민수의 능력치는 power[i][0], power[j][1], power[k][2]
                    int cnt = 0;

                    // 몇명의 병사를 이길 수 있는지 카운팅
                    for (int a = 0; a < N; a++) {
                        if (power[i][0] >= power[a][0] && power[j][1] >= power[a][1] && power[k][2] >= power[a][2]) {
                            cnt++;
                        }
                    }

                    // K명 이상 이길 수 있을 경우 스탯포인트 최솟값으로 갱신
                    if (cnt >= K) {
                        result = Math.min(result, power[i][0]+power[j][1]+power[k][2]);
                    }
                }
            }
        }

        System.out.println(result);
    }

}
