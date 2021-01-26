package _210126.prog17684;

import java.util.*;

public class Main_yj {
		public static int[] solution(String msg) {
				// 알파벳을 담을 list
				ArrayList<String> list = new ArrayList<String>();
				// 결과를 담을 ans
				ArrayList<Integer> ans = new ArrayList<>();
				// list에 알파벳 초기화
				for (int i = 0; i < 26; i++) {
					list.add(String.valueOf((char) ((int) 'A' + i)));
				}

				String word = "" + msg.charAt(0);
				int index = 1;
				while (index <= msg.length()) {
					// 인덱스가 글자 길이랑 같으면 찾을 단어가 없다
					if (index == msg.length()) {
						// 마지막 인덱스를 추가하고 break;
						ans.add(list.indexOf(word) + 1);
						break;
					}

					char now = msg.charAt(index);
					String wc = word + now;

					// 리스트에 단어가 있다면
					if (list.contains(wc)) {
						// 다음 워드에 다음 문자를 붙이기 위해 word에 wc 대입
						word = wc;
						// 인덱스 하나 증가
						index++;
						continue;
					}
					// 리스트에 단어가 없다면
					else {
						list.add(wc);
						// 결과 리스트에 새롭게 추가해준 인덱스 번호를 추가한다.
						ans.add(list.indexOf(word) + 1);
						// 단어가 있으므로 word를 초기화 해준다.
						word = "" + now;
						index++;
					}

				}

				int[] answer = new int[ans.size()];

				for (int i = 0; i < answer.length; i++)
					answer[i] = ans.get(i);

				return answer;
		}
	
}