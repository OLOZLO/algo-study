package _210427.boj20952;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main_hz {

    /*
     * 1. 결과 : 실패 (시간초과)
     * 2. 시간복잡도 : O(MN) => 최악의 경우 10^10여서 시간초과가 난다는 것은 알겠습니다...
     * 3. 풀이 :
     *  문제의 조건에 따라 코드를 작성하였고 원소를 수정해야하는 경우, 삭제하는 경우를 분류할 때
     *  속도를 위해 Hash 친구들을 사용하였지만 M*N 부분부터 시간초과가 난다는 것을 뒤늦게 알았습니다.
     *  분류를 봤는데 어떻게 이분탐색으로 풀어야하는지 전혀 감이 안옵니다... 다시 풀어볼겁니다...
     * */

    final static int mod = 1_000_000_007;
    public static int stoi(String s) {return Integer.parseInt(s);}

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = stoi(st.nextToken());
        int M = stoi(st.nextToken());

        int[] A = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) A[i] = stoi(st.nextToken());

        int[] B = new int[M];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) B[i] = stoi(st.nextToken());

        int cnt = A.length;

        for (int m = 0; m < M; m++) {
            //
            HashMap<Integer, Integer> modify = new HashMap<>();
            HashSet<Integer> remove = new HashSet<>();

            for (int i = 0; i < N; i++) {
                // 이미 제거된 원소일 경우 pass
                if (A[i] == 0) continue;

                int tmp = (A[i]+B[m])%mod;
                if (tmp % 7 == 0) remove.add(i);    // 7의 배수이면 지워야되는 목록에 저장
                else modify.put(i, tmp);    // 그렇지 않을 경우 값을 변경해야하는 목록에 저장
            }

            // 현재 남은 원소들을 모두 지우지 않는 경우 remove의 원소들은 지우고, modify의 원소들은 값 변경
            if (remove.size() != cnt) {
                cnt -= remove.size();
                for (int idx : remove) A[idx] = 0;
                for (int idx : modify.keySet()) A[idx] = modify.get(idx);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(cnt+"\n");
        for (int i = 0; i < N; i++) {
            if (A[i] != 0) sb.append(A[i]+" ");
        }

        System.out.println(sb.toString());
    }
}

/*
* 7 14 21 28 35 42 49
*
* 14
* */
