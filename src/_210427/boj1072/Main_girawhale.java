package _210427.boj1072;

import java.util.Scanner;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : log(10^9)
 *    1 ~ 10^9까지 이분 탐색
 *
 * 3. 접근 방식
 *  이긴 횟수가 m만큼 증가했을 때,z보다 확률이 올라가는지 이분 탐색
 *  만약 확률의 변화가 있다면 답을 저장한 뒤 m을 줄여보고
 *  만약 변화가 없다면 범위를 늘리는 방법으로 탐색
 *  
 *  처음에 나온 식 그대로 문제를 풀다보니 double의 오차값에 걸려 답이 틀리는 경우가 계속 발생
 *  때문에 아예 소수값이 나오지 않도록 미리 100을 곱하는 형태로 %값을 구하는 방법으로 변경
 */
public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt(), y = sc.nextInt();
        int z = getPercent(x, y);

        int ans = -1, l = 0, r = (int) 1e9; // 게임 2배만큼 했는데도 변화 없으면 가망 없는거
        while (l <= r) {
            int m = (l + r) / 2;

            if (getPercent(x + m, y + m) != z) { // 기존 z와 값이 달라졌는지 검증
                ans = m;
                r = m - 1; // 변했으면 값을 저장하고 범위를 줄임
            } else {
                l = m + 1; // 변하지 않으면 범위를 늘림
            }
        }
        System.out.println(ans);
    }

    static int getPercent(int x, int y) {
        return (int) ((long) y * 100 / x); // double을 사용하면 오차때문에 답이 틀린다 주의!
    }
}
