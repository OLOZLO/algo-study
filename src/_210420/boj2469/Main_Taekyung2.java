package _210420.boj2469;

import java.util.Scanner;

public class Main_Taekyung2 {

    /**
     * [실버1] 사다리 타기
     * 1. 결과 : 성공
     * 2. 시간 복잡도 : O(N * K)
     * 	- 이유
     *    세로 길이 N * 가로 길이 K만큼 스왑, 비교
     *
     * 	- 접근 방식
     * 		1) 사다리 문제들은 보통 뒤에서부터 해결하면 쉽게 되는 경우가 많아서 쉽게 솔루션을 떠올릴 수 있었음
     *
     * 		2) 가로막대가 연속으로 오지 않기 때문에 인접한 칸들만 비교하면 된다고 생각
     *
     * 3. 후기
     *	- 문자 배열을 출력할 때 실수로 1칸 더 선언을 해놓고, System.out.println(ret)처럼 toString으로 출력을 했더니
     *      테스트할 때는 제대로 나온 것같았는데 계속 틀렸음
     *
     */


    static int K, N, hide;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        K = sc.nextInt();
        N = sc.nextInt();
        char[] start = new char[K];
        char[] fin = sc.next().toCharArray();
        char[] ret = new char[K - 1]; // ???에 들어갈 문자열
        char[][] map = new char[N][K - 1];

        // 시작 문자열 생성 ABC....
        for(int i = 0; i < K; i++)
            start[i] = (char) ('A' + i);

        for(int i = 0; i < N; i++) {
            String tmp = sc.next();
            // ????..가 있는 줄이 몇 번째인지 저장
            if(tmp.charAt(0) == '?')
                hide = i;

            for(int j = 0; j < K - 1; j++)
                map[i][j] = tmp.charAt(j);
        }

        // 제일 위에서부터 물음표까지 사다리를 타고 내려온다
        for(int i = 0; i < hide; i++)
            go(map[i], start);
        // 제일 밑에서부터 물음표까지 사다리를 타고 올라감
        for(int i = N - 1; i > hide; i--)
            go(map[i], fin);

        for(int i = 0; i < K - 1; i++) {
            if(start[i] == fin[i])
                ret[i] = '*';
            else if(start[i] == fin[i + 1] && start[i + 1] == fin[i]){ // 옆 문자랑 같으면 가로막대로 이어본다
                ret[i] = '-';
                swap(start, i, i + 1);
            } else { // 가로막대는 연속으로 오지 않기 때문에 한 칸 옆과 다르면 불가능
                System.out.println("x".repeat(K - 1));
                return;
            }
        }

        System.out.println(ret);
    }

    // 가로막대가 있으면 swap
    static void go(char[] ladder, char[] people) {
        for(int i = 0; i < K - 1; i++) {
            if(ladder[i] == '-')
                swap(people, i, i + 1);
        }
    }

    static void swap(char[] arr, int a, int b) {
        char tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
