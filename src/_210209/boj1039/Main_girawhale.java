package _210209.boj1039;

import java.util.Scanner;

public class Main_girawhale {
    static int N, K, len, max = -1; // 끝까지 탐색을 못했다면 반환할 -1을 미리 할당
    static int[] pow;
    static boolean[][] visit;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        len = String.valueOf(N).length(); // 숫자의 길이를 저장
        visit = new boolean[K][1_000_001];// [n][m] : n번 돌렸을 때 m이 이미 등장했다면 같은 탐색을 반복하기때문에 반복을 제거하자!

        pow = new int[7]; // 계속 사용하게 될 10^n을 미리 계산해두고 사용하자
        pow[0] = 1;
        for (int i = 1; i < 7; i++) pow[i] = pow[i - 1] * 10;

        solve(0, N);
        System.out.println(max);
    }

    static void solve(int cnt, int num) {
        if (cnt == K) { // K번 변경이 종료!
            max = Math.max(max, num);
            return;
        }

        for (int i = 0; i < len; i++) { // 6 5 4 3 2 1 0 순서임
            for (int j = i + 1; j < len; j++) { // i < j, i와 j를 swap!
                int tmp = num; // num이 바뀌면 안되니까 tmp를 생성
                int n1 = tmp / pow[i] % 10;
                int n2 = tmp / pow[j] % 10;

                tmp = tmp - n1 * pow[i] - n2 * pow[j] +
                        n1 * pow[j] + n2 * pow[i]; // swap

                if (tmp < pow[len - 1] || visit[cnt][tmp]) continue; // 바꿧는데 앞이 0이라서 너무 작아지거나, 이미 방문했나여? 그러면 제낌
                visit[cnt][tmp] = true; // 안했으면 나 했다고 알려쥼

                solve(cnt + 1, tmp); //다음!
            }
        }
    }
}
