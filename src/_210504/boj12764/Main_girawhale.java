package _210504.boj12764;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(NlogN)
 *              시작과 종료 분할 => 2N
 *              이것들을 정렬함 => 2Nlog2N
 *
 *              정렬한 list를 하나씩 돌면서 우선순위 큐에서 삽입/삭제 => 2Nlog2N
 *
 *              => O(NlogN)
 *
 * 3. 접근 방식
 *      옛날옛날 풀었던 카카오의 추석트래픽? 암튼 그런 투포인터 쓰는 문제가 생각남 => 투포인터 ㄱㄱ
 *
 *      1) 시작과 종료를 분리해서 저장하고 이를 정렬
 *      2) 하나씩 탐색
 *         2-1) 시작? 우선순위큐에 있는 빈 자리 있으면 거기가서 앉고 아니면 새로운 자리를 할당해줌
 *         2-2) 종료? 앉은 자리 반납해서 큐에 넣어줌
 *
 *
 */
public class Main_girawhale {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        List<int[]> list = new ArrayList<>();

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()), e = Integer.parseInt(st.nextToken());

            // 종료할 때 자리 반납하기 위한 index도 함께 저장
            list.add(new int[]{s, 1, i}); // 시작
            list.add(new int[]{e, -1, i}); // 종료
        }

        list.sort(Comparator.comparingInt(o -> o[0]));// 시작이 빠른 순. 시작/종료는 구분❌. 어차피 안겹친다

        PriorityQueue<Integer> queue = new PriorityQueue<>(); // 빈자리를 작은순으로 정렬할 우선순위 큐
        TreeMap<Integer, Integer> map = new TreeMap<>(); // 앉은 자리에 몇명 앉았었는지 누적. 정렬도 필요하니까 TreeMap으로 한번에 ㄱ
        int[] enter = new int[N]; // 현재 인덱스의 사람이 무슨 자리에 앉았는지 저장
        int sum = 0; // 지금 앉은 사람 누적

        for (int[] n : list) {
            sum += n[1];

            if (n[1] == 1) { // 시작
                enter[n[2]] = queue.isEmpty() ? sum : queue.poll(); // 큐 비었으면 누적 사람수만큼의 새로운 자리 할당
                map.put(enter[n[2]], map.getOrDefault(enter[n[2]], 0) + 1); // 앉은 자리에 몇명 앉았는지 누적
            } else { // 퇴장
                queue.add(enter[n[2]]); // 앉았던 자리를 큐에 반납
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(map.size()).append('\n');
        map.keySet().forEach(k -> sb.append(map.get(k)).append(' '));
        System.out.println(sb);
    }
}
