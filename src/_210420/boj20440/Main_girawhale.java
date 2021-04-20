package _210420.boj20440;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
1. 결과 : 성공
2. 시간 복잡도 : 못구하겠슴미다...
               중복 제거때문에 다 안겹치면 2*10^6개 나오는데
               범위마다 합 구하고 이분탐색 2번하고....
               누적합 부분의 시간복잡도를 모르겠음

3. 풀이 : 모기가 들어오고 나간 시간의 중복을 제거
         중복을 제거한 시간에 모기의 수를 누적합
         해당 누적합이 연속되는 가장 큰 범위를 구해준다.
*/
public class Main_girawhale {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[][] arr = new int[N][2]; // 모기의 입출입을 저장, 0:입 / 1:출
        Set<Integer> set = new HashSet<>();
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i] = new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
            set.add(arr[i][0]); // 입출입의 중복을 제거
            set.add(arr[i][1]);
        }

        int[] sum = new int[set.size()];
        List<Integer> list = new ArrayList<>(set);
        list.sort(null); // 이분탐색을 위한 정렬

        for (int i = 0; i < N; i++) {
            int start = Collections.binarySearch(list, arr[i][0]); // 시작 인덱스
            int end = Collections.binarySearch(list, arr[i][1]); // 종료 인덱스

            while (start < end) { // 범위의 모기 수 증가
                sum[start++]++;
            }
        }

        int max = 0;
        int[] range = null;
        for (int i = 0; i < sum.length; i++) {
            if (max < sum[i]) { // 모기 수가 최대값보다 크다면
                max = sum[i];
                int j = i;
                while (j < sum.length && sum[i] == sum[j]) // 연속되는 값들 구함
                    j++;

                range = new int[]{list.get(i), list.get(j)}; // 연속 시작, 종료를 기준으로 범위를 정해줌
            }
        }

        System.out.println(max);
        System.out.println(range[0] + " " + range[1]);
    }
}