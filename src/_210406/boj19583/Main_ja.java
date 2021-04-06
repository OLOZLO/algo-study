package _210406.boj19583;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * [실버1] 싸이버 개강총회
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(N)
 * 	- 이유 
 * 	(1) 범위 : 채팅 기록(N)->최대 100,000
 * 	(2) HashSet의 시간 복잡도
 * 		- add : O(1)
 * 		- contains : O(1)
 * 	(3) 시간 비교(isBefore, equals, isAfter)
 * 		- 메서드 확인해보면 4가지(시,분,초,나노)를 비교
 * 		- 내부적으로 compare 사용, 시간은 byte로 저장
 * 		- isBefore와 isAfter는 현재 시점의 비교는 제외해서 euqals로 또 비교 해줘야 함.
 * 			-> compare로 3번 비교할거 6번 비교함 (2배)
 * 		- 그럼에도 사용한 이유) 
 * 		    메서드 까보고 나서 알았지만, 알아도 비교하다가 멘붕올까바.. 가독성 때문에 사용할 것 같움! :<
 * 
 * 3. 후기
 * 	- 자바에도 전개자 있으면 좋겠다. [S,E,Q] = [...times] << 이거 왜 못씀
 *  - LocalTime가 생각보다 넘 편했다. '00' << 이런 것두 알아서 체크 및 포맷까지 해주니까 코드가 더 간결해진듯
 */
public class Main_ja {
	public static LocalTime inputToDate(String str) {
		int[] date = Arrays.stream(str.split(":")).mapToInt(Integer::parseInt).toArray();
		return LocalTime.of(date[0], date[1]);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Object[] times = Arrays.stream(br.readLine().split(" ")).map(i -> {
			return inputToDate(i);
		}).toArray();

		LocalTime S = (LocalTime) times[0]; // 개총시작
		LocalTime E = (LocalTime) times[1]; // 개총끝
		LocalTime Q = (LocalTime) times[2]; // 스트리밍끝

		Set<String> enter = new HashSet<>(); // 입장
		Set<String> answer = new HashSet<>(); // 출석 완
		String input = null;
		while ((input = br.readLine()) != null) {
			String[] student = input.split(" ");
			LocalTime time = inputToDate(student[0]);
			String nickname = student[1];
			
			if (time.isBefore(S) || time.equals(S)) // 입실
				enter.add(nickname);
			if (time.equals(E) || time.equals(Q) || (time.isAfter(E) && time.isBefore(Q))) { // 퇴실
				if (!enter.contains(nickname)) continue;
				answer.add(nickname);
			}
		}
		System.out.println(answer.size());
	}

}
