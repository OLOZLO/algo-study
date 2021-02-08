package _210209.prog17685;

import java.util.Arrays;

public class Main_jyj {

	public int solution(String[] words) {
		int answer = 0;

		// 단어 정렬
		Arrays.sort(words);

		for (int i = 0; i < words.length; i++) {
			char[] prev;
			char[] now;
			char[] next;

			// 맨 앞 단어일 경우
			if (i == 0) {
				now = words[i].toCharArray();
				next = words[i + 1].toCharArray();

				// 가장 길이가 긴 단어 기준으로 반복문 시행
				for (int j = 0; j < Math.min(now.length, next.length); j++) {
					// 같지 않다면 중단
					if (next[j] != now[j]) {
						break;
					}
					// 같다면 1증가
					answer++;
				}

				// 단어가 아예 다르거나 맨 앞 단어가 다음 단어보다 같거나 크면 1증가
				if (answer == 0 || now.length >= next.length) {
					answer++;
				}

			}
			// 맨 마지막 단어일 경우
			else if (i == words.length - 1) {
				now = words[i].toCharArray();
				prev = words[i - 1].toCharArray();
				// 가장 길이가 긴 단어 기준으로 반복문 시행
				for (int j = 0; j < Math.min(now.length, prev.length); j++) {
					// 같지 않다면 중단
					if (prev[j] != now[j]) {
						break;
					}
					// 같다면 1증가
					answer++;
				}
				// 다음으로 비교할 단어가 없으므로 그냥 1증가
				answer++;
			}
			// 그 외
			else {
				prev = words[i - 1].toCharArray();
				next = words[i + 1].toCharArray();
				now = words[i].toCharArray();

				int prevCnt = 0;
				int nextCnt = 0;

				// 이전 단어와 같은 최대 글자수를 구한다.
				for (int j = 0; j < Math.min(prev.length, now.length); j++) {
					if (now[j] != prev[j]) {
						break;
					}
					prevCnt++;
				}
				// 다음 단어와 같은 최대 글자수를 구한다.
				for (int j = 0; j < Math.min(now.length, next.length); j++) {
					if (now[j] != next[j]) {
						break;
					}
					nextCnt++;
				}
				// 이전과 다음 단어중 가장 비슷한거는?
				int result = Math.max(prevCnt, nextCnt);

				// 현재 단어가 result랑 같지 않으면 하나 증가
				if (result != now.length) {
					result += 1;
				}

				answer += result;

			}
		}
		return answer;
	}

}
