package _210504.boj15905;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_hz {

    /*
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(NlogN)
     * 3. 풀이
     *    : 해결한 문제의 개수가 많은 순, 해결한 문제의 개수가 많을 때에는 패털티가 적은 순으로 정렬하여
     *      5등 이후의 학생 중 5등과 해결된 문제의 개수가 같은 학생들을 카운트했습니다.
     *
     * */

    public static class Attendant {
        int solved, penalty;

        Attendant(int solved, int penalty) {
            this.solved = solved;
            this.penalty = penalty;
        }
    }

    public static int stoi(String s) {return Integer.parseInt(s);}
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = stoi(br.readLine());
        StringTokenizer st;
        List<Attendant> list = new ArrayList<>();

        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            list.add(new Attendant(stoi(st.nextToken()), stoi(st.nextToken())));
        }

        Collections.sort(list, new Comparator<Attendant>() {
            @Override
            public int compare(Attendant o1, Attendant o2) {
                if (o1.solved == o2.solved) return Integer.compare(o1.penalty, o2.penalty);
                else return -Integer.compare(o1.solved, o2.solved);
            }
        });

        int n = list.get(4).solved;
        int result = 0;
        int idx = 5;

        while(idx < list.size()) {
            if (list.get(idx).solved == n) {
                result++;
                idx++;
            } else break;
        }

        System.out.println(result);
    }
}
