package _210216.prog42890;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main_hz {
    static List<int[]> list;

    public static void main(String[] args) {
        System.out.println(solution(new String[][]{{"a", "b", "c"}, {"1", "b", "c"}, {"a", "b", "4"}, {"a", "5", "c"}}));
        System.out.println(solution(new String[][]{{"a", "1", "4"}, {"2", "1", "5"}, {"a", "2", "4"}}));
        System.out.println(solution(new String[][]{{"b", "2", "a", "a", "b"}, {"b", "2", "7", "1", "b"}, {"1", "0", "a", "a", "8"},
                {"7", "5", "a", "a", "9"}, {"3", "0", "a", "f", "9"}}));
        System.out.println(solution(new String[][]{{"100","ryan","music","2"},
                {"200","apeach","math","2"},{"300","tube","computer","3"},
                {"400","con","computer","4"},{"500","muzi","music","3"},{"600","apeach","music","2"}}));
    }

    public static int solution(String[][] relation) {
        int answer = 0;
        
        List<int[]> answers = new ArrayList<>();    // 후보키가 가능한 튜플 조합을 저장하는 리스트
    
        for (int n = 1; n <= relation[0].length; n++) {
            list = new ArrayList<>();
            comb(0, 0, relation[0].length, new int[n]); // 길이가 n인 튜플의 조합 생성 (list에 저장)

            for (int i = 0; i < list.size(); i++) {
                 int[] cand = list.get(i);  // 가능한 튜플 조합 하나를 선택해서

                // 아래 과정들은 조합 결과가 이미 정답인 것들을 포함하는지, 최소성 조건을 확인하기 위한 과정
                 boolean[] check = new boolean[relation[0].length]; // 조합 결과로 뽑힌 튜플에 check
                 for (int j = 0; j < cand.length; j++) {
                     check[cand[j]] = true;
                 }

                 boolean pass = false;
                 for (int j = 0; j < answers.size(); j++) { // answer들을 돌면서 조합 결과가 이미 정답인 것들을 포함하면 다음 조합 선택
                     int[] compare = answers.get(j);
                     for (int k = 0; k < compare.length; k++) {
                         if (!check[compare[k]]) break;
                         if (k == compare.length-1) {
                             pass = true;
                             j = answers.size()-1;
                             break;
                         }
                     }
                 }

                 if (pass) continue;

                 HashSet<String> fk = new HashSet<>();  // 유일성을 확인하기 위한 Set
                 for (int j = 0; j < relation.length; j++) {
                     StringBuilder sb = new StringBuilder();
                     for (int k = 0; k < cand.length; k++) {
                         sb.append(relation[j][cand[k]]+" ");   // 튜플에서 조합에 해당되는 속성들을 공백을 포함하여 저장
                     }

                     if (!fk.add(sb.toString())) break; // Set에 추가를 할 때 이미 존재하는 속성이면 유일성 만족X

                     if (fk.size() == relation.length) {    // Set에 있는 튜플의 개수와 전체 튜플의 개수가 일치할 경우 유일성 만족
                         answer++;
                         answers.add(cand);
                     }
                 }
            }
        }
        return answer;
    }

    public static void comb(int idx, int cnt, int n, int[] result) {    // 조합 만드는 함수
        if (idx == result.length) {
            list.add(result.clone());
            return;
        }

        for (int i = cnt; i < n; i++) {
            result[idx] = i;
            comb(idx+1, i+1, n, result);
        }
    }
}
