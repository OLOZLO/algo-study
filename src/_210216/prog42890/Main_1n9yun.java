package _210216.prog42890;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main_1n9yun {
//    찾은 후보키 저장
    ArrayList<ArrayList<Integer>> candidateKeys = new ArrayList<>();
    public int solution(String[][] relation) {
        for(int i=1;i<=relation[0].length;i++){
//            후보키를 구성하는 컬럼의 개수 별 조합
            comb(0, 0, IntStream.range(0, relation[0].length).toArray(), new int[i], relation);
        }
        return candidateKeys.size();
    }

    void comb(int idx, int cnt, int[] base, int[] result, String[][] relation){
        if(cnt == result.length){
            ArrayList<Integer> candidateKey = new ArrayList<>(
//                    List로
                    Arrays.asList(
//                            result 배열의 stream
                            Arrays.stream(result)
//                                    wrap
                                    .boxed()
//                                    Integer[]로 리턴하고
                                    .toArray(Integer[]::new)
                    )
            );
//            여태 찾은 키들을 돌며
            for(ArrayList<Integer> key : candidateKeys) {
//                result 키에 후보키를 구성하는 컬럼들이 모두 포함되는지 (포함되면 패스)
                if (candidateKey.containsAll(key)) return;
            }

//            유일성 확인
            for(int i=0;i<relation.length-1;i++){
                for(int j=i+1;j<relation.length;j++){
                    boolean diff = false;

                    for(int column : result){
                        if(!relation[i][column].equals(relation[j][column])){
                            diff = true;
                            break;
                        }
                    }
                    if(!diff) return;
                }
            }

            candidateKeys.add(candidateKey);
            return;
        }

        for(int i=idx;i<base.length;i++){
            result[cnt] = base[i];
            comb(i + 1, cnt + 1, base, result, relation);
        }
    }
}
