package _210420.boj2469;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
1. 결과 : 성공
2. 시간 복잡도 : O(N * K)
    - 이유 : 위에서 0 ~ ?까지 K번
            아래서 N-1 ~ ?까지 K번
            K번 1회 돌림

3. 풀이 : 시작점 ~ ? 직전까지 도착한 참가자 구함
         도착점 ~ ? 직후까지 어떻게 나왔는지 구함
         만약 두개가 다르면 바꿔줘야 하므로 사다리를 설치해 양 옆을 swap
         마지막까지 사다리를 설치했음에도 둘이 다르면 사다리를 설치해도 노답 => X를 출력
*/
public class Main_girawhale {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int K = Integer.parseInt(br.readLine());
        int N = Integer.parseInt(br.readLine());

        char[] dest = br.readLine().toCharArray();
        char[][] ladder = new char[N][];
        int point = 0;
        for (int i = 0; i < N; i++) {
            ladder[i] = br.readLine().toCharArray();
            if (ladder[i][0] == '?') point = i;
        }

        char[] start = new char[K];
        for (int i = 0; i < K; i++)
            start[i] = (char) ('A' + i); // 시작 초기화 'ABCDEFG...'

        for (int i = 0; i < point; i++) { // 시작 ~ ?
            for (int j = 0; j < K - 1; j++) {
                if (ladder[i][j] == '-') // 사다리 설치됬으면 양옆에 사람 위치 바뀌니까 swap
                    swap(start, j, j + 1);
            }
        }

        for (int i = N - 1; i > point; i--) { // 도착 ~ ?
            for (int j = 0; j < K - 1; j++) {
                if (ladder[i][j] == '-')
                    swap(dest, j, j + 1);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < K - 1; i++) {
            if (start[i] != dest[i]) { // 시작이랑 도착 사람 다르면 사다리 설치하고 바꿔봄
                swap(start, i, i + 1);
                sb.append('-');
            } else sb.append('*'); // 같으니까 pass
        }

        if (!Arrays.equals(start, dest)) { // 다 설치했는데도 실패. 사다리 설치해도 답 안나온다.
            System.out.println("x".repeat(K - 1));
        } else
            System.out.println(sb); // 아니면 사다리 출력

    }

    static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}