package _210518.boj1599;

import java.util.*;


/**
 * [골드5] 민식어
 * 1. 결과 : 성공
 * 2. 시간 복잡도 : O(N * logN * 50)
 *
 * 3. 풀이
 * 	(1) 문자열을 민식어라고 가정하고, 정렬하기 쉽게 기존 알파벳 순서로 치환
 * 	(2) 기존 문자열이랑 묶어놓고 정렬된 순서대로 출력
 *
 *
 * 4. 후기
 * 	- 좀 복잡하게 짠거 같거 같은 느낌
 * 	- 나중에 보니까 이 문제에서는 k랑 ng말고는 기존 알파벳이랑 순서가 똑같아서 두 개만 처리하면 되는 걸 알았음
 *
 */


public class Main_Taekyung2 {

    // 민식어 순서
    static String minsik = "abkdeghilmncoprstuwy";
    // (민식어, 알파벳) 매칭
    static Map<Character, Character> order = new HashMap<>();
    // (바꾸기 전, 바꾼 후)
    static Pair[] arr;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        arr = new Pair[N];

        // 민식어 순서에 따라 알파벳 넣음
        for(int i = 0; i < minsik.length(); i++)
            order.put(minsik.charAt(i), (char) ('a' + i));

        for(int i = 0; i < N; i++) {
            Pair p = new Pair();
            p.prev = sc.next();
            // ng를 안쓰는 알파벳으로 교체
            String s = p.prev.replace("ng", "c");
            StringBuilder sb = new StringBuilder();
            // 민식어를 정렬할 수 있게 알파벳으로 대입
            for(int c = 0; c < s.length(); c++)
                sb.append(order.get(s.charAt(c)));
            p.next = sb.toString();
            arr[i] = p;
        }

        // 바꾼 후 문자열 기준으로 정렬
        Arrays.sort(arr, Comparator.comparing(p -> p.next));
        for(Pair p : arr)
            System.out.println(p.prev);
    }

    static class Pair {
        String prev, next;
    }
}
