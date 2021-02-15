package _210216.prog42890;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main_girawhale {
    List<Integer> key = new ArrayList<>(); // 키로 가능한거 저장할거

    boolean check(int i) {
        for (int k : key)
            if ((k & i) == k) return false; //이미 저장된 키랑 탐색할 키를 비교했는데 내 키를 포함한 키가 있으면 false
        return true;
    }

    public int solution(String[][] relation) {
        int n = relation.length, m = relation[0].length;

        for (int k = 1; k < (1 << m); k++) { // 비트로 조합만들기
            if (!check(k)) continue;

            Set<String> set = new HashSet<>(); // 선택한 키를 기준으로 속성들을 붙여 저장할 Set
            for (String[] row : relation) {
                StringBuilder key = new StringBuilder(); 

                for (int j = 0; j < m; j++) 
                    if (((1 << j) & k) > 0) key.append(row[j]).append('/'); // 조합하기로 한 키라면, 속성을 append함

                if (!set.add(key.toString())) break; // 반복이 끝나면 set에 add해보는데 이미 있다면 false가 반환되니까 종료
            }
            if (set.size() == n) key.add(k); // set의 사이즈가 전체 튜플 사이즈와 동일하면 key로 인정
        }
        return key.size();
    }
}
