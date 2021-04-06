package _210406.boj19583;

import java.io.*;
import java.util.HashMap;
import java.util.stream.Stream;

public class Main_Taekyung2 {

    /**
     *  1. 결과 : 성공
     *  2. 시간 복잡도 : O( 채팅 개수 )
     *      - 이유 : 채팅 당 map에 접근 한 번
     *  3. 아이디어
     *      - 시간을 분으로 바꾸고, 세 구간으로 나눈다
     *      - 개강총회 시작 전 -> map에 (이름, false) 입력
     *      - 개강총회 종료 후, 스트리밍 종료 전 -> map에 (이름, false)가 있으면 카운트
     */

    static int stoi(String s) { return Integer.parseInt(s); }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] time = Stream.of(br.readLine().split(" ")).mapToInt(x -> getMinute(x)).toArray();
        HashMap<String, Boolean> check = new HashMap<>();
        int ret = 0;
        String input;
        while((input = br.readLine()) != null) {
            String[] log = input.split(" ");
            int t = getMinute(log[0]);
            String name = log[1];
            if(t <= time[0]) {
                check.put(name, false);
            } else if(t >= time[1] && t <= time[2]) {
                if(!check.getOrDefault(name, true)) {
                    check.put(name, true);
                    ret++;
                }
            }
        }
        System.out.println(ret);
    }

    static int getMinute(String s) {
        String[] tmp = s.split(":");
        return stoi(tmp[0]) * 60 + stoi(tmp[1]);
    }
}
