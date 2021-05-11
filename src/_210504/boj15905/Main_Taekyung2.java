package _210504.boj15905;

import java.util.Arrays;
import java.util.Scanner;

public class Main_Taekyung2 {

    /*
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(NlogN)
     * 3. 풀이
     *    - 그냥 정렬하고 5등이랑 같은 애들 카운트해줬습니다.
     *
     * */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Student[] students = new Student[N];

        for(int i = 0; i < N; i++)
            students[i] = new Student(sc.nextInt(), sc.nextInt());

        // 푼 문제수 내림차순, 페널티 오름차순 정렬
        Arrays.sort(students, (s1, s2) -> {
            if(s1.cnt == s2.cnt) return s1.penalty - s2.penalty;
            return s2.cnt - s1.cnt;
        });

        int ret = 0;
        int cnt = students[4].cnt;

        // 정렬하고 5등이랑 푼 문제 같은 사람 체크합니다
        for(int i = 5; i < N; i++) {
            if(students[i].cnt == cnt)
                ret++;
            else
                break;
        }

        System.out.println(ret);
    }

    static class Student {
        int cnt, penalty;

        Student(int cnt, int penalty) {
            this.cnt = cnt;
            this.penalty = penalty;
        }
    }
}
