package _210406.boj19583;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;


/*

결과 : 성공
시간복잡도 : O(채팅 기록 개수) (최악의 경우 O(10만))
최대 10만개의 채팅 기록을 확인하며 참석자를 추가, 삭제 하는 과정이 HashSet을 사용하여 O(1)이기 때문에
시간 복잡도가 채팅 기록 갯수와만 관련이 있다고 생각했습니다.

그런데 왜 나 메모리 시간 미쳤지...?
*/
public class Main_hz {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] schedule = new int[3];
        StringTokenizer st = new StringTokenizer(br.readLine());

        // HH:MM 형식의 개강총회 시작 시간, 종료 시간, 스트리밍 종료 시간을 분 단위로 변경합니다.
        for (int i = 0; i < 3; i++) {
            String t = st.nextToken();
            schedule[i] += Integer.parseInt(t.split(":")[0])*60;
            schedule[i] += Integer.parseInt(t.split(":")[1]);
        }

        HashSet<String> attendant = new HashSet<>();
        int result = 0;

        String input;
        while((input = br.readLine()) != null) {
            st = new StringTokenizer(input);
            String time = st.nextToken();

            // 채팅이 입력된 시간을 분 단위로 변경합니다.
            int now = Integer.parseInt(time.split(":")[0])*60;
            now += Integer.parseInt(time.split(":")[1]);

            // 개강 총회 시작 전 채팅을 한 학생들을 참석자 목록에 넣어줍니다.
            if (now <= schedule[0]) attendant.add(st.nextToken());
            // 개강 총회 종료 시간 ~ 스트리밍 종료 시간 간에 채팅을 한 학생 중
            // 참석자 목록에 있는 학생들은 카운팅 한 후 중복 카운팅 방지를 위해 참석자 목록에서 제거해줍니다.
            else if (now >= schedule[1] && now <= schedule[2]) {
                String name = st.nextToken();
                if (attendant.contains(name)) {
                    result++;
                    attendant.remove(name);
                }
            }
            // 스트리밍 종료 시간 이후의 채팅이 있을 경우 입력과 상관 없이 로직을 종료해줍니다.
            else if (now >= schedule[2]) break;
        }

        System.out.println(result);

    }
}
