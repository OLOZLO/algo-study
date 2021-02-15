package _210216.prog42888;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main_jyj {

	public String[] solution(String[] record) {

		// HashMap은 중복이 안되는 자료구조이고 가장 마지막에 넣는 키의 값으로 변경이 되므로 사용함
		// 때문에 따로 change를 생각할 필요가 없다
		// 아이디와 닉네임을 매칭하기 위함
		HashMap<String, String> User = new HashMap<String, String>();

		// 채팅방을 나가는 경우를 제외하고 key , value 값으로 저장
		for (int i = 0; i < record.length; i++) {
			String[] str = record[i].split(" ");
			if (!str[0].equals("Leave")) {
				User.put(str[1], str[2]);
			}
		}

		// 출입 기록
		List<String> result = new ArrayList<String>();

		for (int i = 0; i < record.length; i++) {
			String[] str = record[i].split(" ");

			// 입장일 경우
			if (str[0].equals("Enter")) {
				// 아이디를 매칭해서 입장처리
				result.add(User.get(str[1]) + "님이 들어왔습니다.");
			} else if (str[0].equals("Leave")) {
				// 아이디를 매칭해서 퇴장처리
				result.add(User.get(str[1]) + "님이 나갔습니다.");
			}
		}

		// 결과를 answer를 result 사이즈로 초기화
		String[] answer = new String[result.size()];

		// answer에 결과 저장
		for (int i = 0; i < result.size(); i++) {
			answer[i] = result.get(i);
		}

		return answer;
	}

}
