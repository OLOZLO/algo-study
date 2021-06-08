package _210608.boj1669;

import java.util.Scanner;



public class Main_Taekyung2 {

    /**
     * [실버1] 멍멍이 쓰다듬기
     * 1. 결과 : 맞았습니다.
     * 2. 시간복잡도 : O(Y - X)
     *
     * 3. 풀이
     * 	(1) 처음과 끝이 1, 2, 3 ... 으로 대칭 형태로 나타나게 됨
     * 	(2) 1, 1, 2, 2, 3, 3, 4, 4 ..로 쭉쭉 더해나간다, 원숭이가 멍멍이 키보다 크거나 같아지면 종료
     *
     * 4. 후기
     * 	- 백준 1011번 문제랑 완전 똑같음, 풀어본거라서 쉽게 풀 수 있었다
     */


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();

        // long 처리 안해줘서 틀렷음 ㅠ
        long d = 0;
        int day = 1;
        while(d < y - x) {
            // 1, 1, 2, 2, 3, 3, 4, 4 ...
            d += (day + 1) / 2;
            day++;
        }

        // 마지막에 한 번 더 더했으니까 1 빼준다
        System.out.println(day - 1);
    }
}
