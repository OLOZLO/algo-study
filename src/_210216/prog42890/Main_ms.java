package _210216.prog42890;

import java.util.*;

public class Main_ms {
        public static int solution(String[][] relation) {
            int m = relation.length;    // m : 튜플의 개수.
            int n = relation[0].length; // n : 속성의 개수.
            List<Integer> answer = new ArrayList<>();   // answer : 후보키를 저장할 변수.

            for (int i = 1; i < (1 << n); i++) { // 비트 표현으로 슈퍼키(i)를 하나씩 만든다.
                Set<String> set = new HashSet<>(); // 유일성 검사를 위한 set 자료구조.

                for (int j = 0; j < m; j++) {
                    StringBuilder sb = new StringBuilder();

                    for (int k = 0; k < n; k++)
                        if ((i & (1 << k)) != 0) sb.append(relation[j][k]); // 슈퍼키에 해당하는 속성 값들을 이어 붙여 문자열을 만든다.

                    set.add(sb.toString()); // 만들어진 문자열을 set에 넣음으로써, 유일성을 검사한다.
                }

                if (set.size() == m && check(answer, i)) // 해당 슈퍼키로 생성된 set의 크기가 튜플의 수와 같고, 해당 슈퍼키가 최소성을 만족시키면,
                    answer.add(i);                       // answer에 추가한다.
            }

            return answer.size();                        // answer의 크기(=후보키 개수)를 리턴한다.
        }

        private static boolean check(List<Integer> list, int value) {

        // answer를 돌며, 각 요소에 파라미터로 받은 value값에 &(AND) 연산을 한 결과가 자기 자신(요소) 이면, 최소성을 만족시키지 못하므로 false를 리턴한다.
        // 다시 말해, 겹치는 속성이 존재하며, 더 적은 속성들로 이뤄진 후보키가 "이미" 존재한다면(list에 있다면), 해당 슈퍼키는 최소성을 만족시키지 못하는 것!
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i) & value) == list.get(i)) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        solution(new String[][]{{"100", "ryan", "music", "2"}, {"200", "apeach", "math", "2"}, {"300", "tube", "computer", "3"}, {"400", "con", "computer", "4"}, {"500", "muzi", "music", "3"}, {"600", "apeach", "music", "2"}});
    }
}
