package _210518.boj1599;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main_hz {

    /**
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(NlogN)
     * 3. 풀이 :
     *    compare함수를 오버라이딩하여 민식어의 알파벳 순으로 정렬을 함.
     *    이 때 ng 문자의 처리를 위해 ng 문자를 c로 변경한 뒤 (민식어에 해당되지 않는 어떤 문자라도 상관 없음)
     *    알파벳의 순서를 저장해둔 alpha 배열을 활용하여 정렬
     *
     * */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 민식어의 순서대로 1부터 순차적으로 값 부여. 이 때 ng 는 c로 바꿔서 사용할 것이기 때문에 c에 12를 할당함
        // ['a'-'a'] = [0] = 1 => a는 민식어의 첫번째 문자, ['g'-'a'] = [7] = 6 => g는 민식어의 6번째 문자
        int[] alpha = new int[]{1, 2, 12, 4, 5, 0, 6, 7, 8, 0, 3, 9, 10, 11, 13, 14, 0, 15, 16, 17, 18, 0, 19, 0, 20, 0};
        int N = Integer.parseInt(br.readLine());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add(br.readLine());
        }

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 비교할 문자들에서 ng가 포함된 경우 ng를 c로 변경 (c가 민식어에 해당되지 않기 때문에 사용)
                String s1 = o1.replace("ng", "c");
                String s2 = o2.replace("ng", "c");
                int len = Math.min(s1.length(), s2.length());

                // 각 문자열의 첫번째 문자부터 비교하여 같은 문자가 아닐 경우 민식어에서 순서가 더 먼저인 문자가 앞쪽에 오도록 정렬
                for (int i = 0; i < len; i++) {
                    int val1 = alpha[s1.charAt(i)-'a'];
                    int val2 = alpha[s2.charAt(i)-'a'];

                    if (val1 == val2) continue;
                    else return Integer.compare(val1, val2);
                }
                
                // 짧은 문자열의 길이만큼 짧은 문자열과 긴 문자열이 동일하다면 짧은 문자열이 앞쪽에 오도록 정렬
                return Integer.compare(s1.length(), s2.length());
            }
        });

        for (String s : list)
            System.out.println(s);
    }
}
