package _210406.boj19583;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

/*
    1. 결과 : 성공
    2. 시간복잡도 : O(N) (N: 입력으로 받는 채팅의 기록)
        - 이유 : while 문 돌며, 입력받는 채팅에 대해서 시간을 비교하는 작업을 수행합니다.
                37줄에서의 contains 메소드도 O(1)의 시간이 걸리므로, 영향을 안주니까 최종 시간 복잡도는 O(N)이라고 생각합니다.
*/

public class Main_ms {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int S = calTime(st.nextToken());
        int E = calTime(st.nextToken());
        int Q = calTime(st.nextToken());
        HashSet<String> before = new HashSet<>();                   // before : 입장 확인을 해두기 위해 사용합니다.
        int answer = 0;
        String input = null;

        while ((input = br.readLine()) != null) {                   // EOF 만날때까지 한 줄씩 입력 받습니다.
            st = new StringTokenizer(input);
            int time = calTime(st.nextToken());
            String name = st.nextToken();

            // 입력 시간이 개총 시작 시간(S) 보다 짧으면 before 에 넣어 입장 확인을 해둡니다.
            if (time <= S) {
                before.add(name);
                continue;
            }

            // 입력 시간이 개총 끝나는 시간(E) 에서 개총 스트리밍이 끝나는 시간(Q) 사이에 해당하면서,
            // 동시에 학생이 입장 확인이 되었으면,
            // 카운트해주고, 학생을 Set에서 제거합니다. -> 여기 해당되는 시간동안에 또 채팅치면 얘도 세버리기 때문.
            if (E <= time && time <= Q && before.contains(name)) {
                answer++;
                before.remove(name);
            }
        }

        System.out.println(answer);
    }

    // calTime()
    // 입력 파라미터 : 시간을 나타내는 문자열.
    // 리턴        : "HH:mm" 포맷의 시간을 분단위로 환산한 값을 리턴.
    static int calTime(String str) {
        String[] splited = str.split(":");
        int hour = Integer.parseInt(splited[0]);
        int min = Integer.parseInt(splited[1]);

        return hour * 60 + min;
    }
}
