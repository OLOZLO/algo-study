package _210420.boj20440;

import java.util.HashMap;
import java.util.Scanner;

/*
 1. 결과 : 실패 (시간초과)
 2. 시간 복잡도 : O(N*2_100_000_000) (2_100_000_000은 상수가 아니라 입장시간의 최대 차라고 생각해주세요)
 3. 완탐밖에 생각나지 않았습니다...
 */

public class Main_hz {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] result = new int[3];

        HashMap<Integer, Integer> check = new HashMap<>();

        for (int i = 0; i < N; i++) {
            int start = sc.nextInt();
            int end = sc.nextInt();

            for (int time = start; time < end; time++) {
                check.put(time, check.getOrDefault(time, 0)+1);

                if (check.get(time) > result[0]) {
                    result[0] = check.get(time);
                    result[1] = time;
                    result[2] = time+1;
                } else if (check.get(time) == result[0]) {
                    if (time == result[2]) result[2]++;
                    else if (time < result[1]) {
                        result[1] = time;
                        result[2] = time+1;
                    }
                }
            }
        }

        System.out.println(result[0]);
        System.out.println(result[1]+" "+result[2]);
    }
}
