package _210406.boj19583;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

// 1. 결과 : 성공
// 2. 시간 복잡도 : O(N)
// - 이유
// 채팅 기록의 수 = N <= 100,000
// 시간("hh:MM")의 길이 = 5 => String.compareTo() = 5
// set.add() = set.contains() = O(1) => 무시
// set.remove() = ?...
// =====================
// 채팅 기록당 시간 비교가 최대 3번 발생
// 따라서, 최대 100,000개의 기록이 최대 3번의 비교가 발생
// 100,000 * 5^3 = 125 * 10^5 <= 10^8
public class Main_girawhale {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        String S = st.nextToken(); // 개강총회를 시작한 시간
        String E = st.nextToken(); // 개강총회를 끝낸 시간
        String Q = st.nextToken(); // 스트리밍을 끝낸 시간

        Set<String> enter = new HashSet<>(); // 시간 내 입장을 한 사람을 저장할 Set
        String input;
        int ans = 0;

        while ((input = br.readLine()) != null) {
            String[] split = input.split(" ");
            String time = split[0], name = split[1];

            // hh:MM의 형태가 동일하기 때문에 문자열 비교가 가능.
            // "11:40".compareTo("12:00") <= 0
            if (time.compareTo(S) <= 0) enter.add(split[1]); // 개총 시작 전에 들어온 사람을 Set에 넣음
            else if (time.compareTo(E) >= 0 // 개총 종료 이후,
                    && time.compareTo(Q) <= 0 // 스트리밍 종료 이전에 들어온 사람이면서
                    && enter.contains(name)) { // 개총 시작 전에 들어온 사람?
                enter.remove(name); // Set에서 제거하고 답에 추가
                ans++;
            }
        }

        System.out.println(ans);
    }
}
