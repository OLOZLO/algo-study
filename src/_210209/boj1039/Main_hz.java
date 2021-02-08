package _210209.boj1039;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main_hz {
    static Integer[] ordered;
    static int result;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String N = sc.next();
        int K = sc.nextInt();

        ordered = new Integer[N.length()];  // 입력 받은 수들의 내림차순 배열
        int[] num = new int[N.length()];    // 입력 받은 수 배열
        for (int i = 0; i < N.length(); i++) {
            num[i] = ordered[i] = N.charAt(i)-'0';
        }

        Arrays.sort(ordered, Collections.reverseOrder());

        // N이 한 자리 수거나, 두 자리 수지만 10의 배수일 경우 교환을 할 수 없음
        if (num.length == 1 || num.length == 2 && Integer.parseInt(N)%10 == 0)
            System.out.println("-1");
        else {
            result = 0;
            change(0, num, K);
            System.out.println(result);
        }
    }

    public static void change(int idx, int[] num, int K) {
        if (K == 0) {   // 종료조건
            int n = num[0];
            for (int i = 1; i < num.length; i++) {  // 각 자리 수들을... 한 숫자로 만듭니다...
                n *= 10;
                n += num[i];
            }

            result = Math.max(result, n);   // 이미 큰 수가 만들어졌더라도 더 큰 수가 정답임~
            return;
        }

        // 아이디어 = idx번째 큰 수를 idx 자리에 가져다 놓자! (이미 ordered배열 만들어놨자나, num을 걔처럼 만드려고 하는거야)
        for (int i = idx; i < num.length; i++) {    // 그래서 우선 idx를 구합니다
            if (num[i] != ordered[i]) {
                idx = i;
                break;
            }
            if (i == num.length-1) {    // 수가 이미 만들 수 있는 제일 큰 수일 경우
                idx = num.length;
            }
        }

        if (idx < num.length) {
            // idx 자리에 idx번째 큰 수 옮길꺼야
            for (int i = 0; i < num.length; i++) { 
                if (num[i] == ordered[idx]) {   // idx번째 큰 수 찾았으면
                    int[] changed = num.clone();
                    int tmp = changed[i];   // idx자리의 값과 교환하는 과정
                    changed[i] = changed[idx];
                    changed[idx] = tmp;
                    change(idx+1, changed, K-1);    // 이 때 idx번째 큰 수가 여러개일 수 있으므로 재귀 이용!
                }
            }
        } else {    // 이미 만들 수 있는 가장 큰 수를 만들었을 때
            int[] cnt = new int[10];    // 0~9의 숫자가 각각 몇개나 있나 확인하기 위해 카운팅 배열 생성
            for (int i = 0; i < num.length; i++)
                cnt[num[i]]++;

            boolean same = false;   // 같은 숫자 2개 이상 존재 => 걔네들끼리 계속 교환하면 되서 안바꿔도 됨!
            for (int i = 0; i < cnt.length; i++)
                if (cnt[i] >= 2) same = true;

            int[] changed = num.clone();
            // 같은 숫자가 2개 이상 존재하지 않고, 홀수 횟수만큼 남았을 경우 (짝수일 경우엔 바꾸고, 돌려놓고 반복하면 되니까 안바꿔도 됨)
            if (!same && K%2 == 1) {    // 1의 자리와 10의자리 수 서로 교환
                int tmp = changed[changed.length-1];
                changed[changed.length-1] = changed[changed.length-2];
                changed[changed.length-2] = tmp;
            }
            change(idx, changed, 0);    // 남은 K번 다 처리 했으니까 끝내려고 change함수 호출!
        }
    }
}
