package _210126.prog17687;

import java.util.*;

public class Main_yj {
	public static String solution(int n, int t, int m, int p) {
		// 진수에 따라서 길이가 달라지므로 arrayList 사용
		ArrayList<String> list = new ArrayList<>();
		StringBuffer s = new StringBuffer();

		// 초기화
		String answer = "";
		int count = 0;
		int idx = 0;

		for (int i = 0; i < t * m; i++) {
			int temp = i;
			if (temp == 0)
				list.add("0");
			// 숫자가 0이 아닐때
			while (temp != 0) {

				// 10진수 이상일 경우
				switch (temp % n) {
				case 10:
					list.add(idx, "A");
					break;
				case 11:
					list.add(idx, "B");
					break;
				case 12:
					list.add(idx, "C");
					break;
				case 13:
					list.add(idx, "D");
					break;
				case 14:
					list.add(idx, "E");
					break;
				case 15:
					list.add(idx, "F");
					break;

				// 10진수 이하일 경우
				default:
					list.add(idx, String.valueOf(temp % n));
					break;
				}
				// 한바퀴 끝나면 다시 처음부터
				temp /= n;
			}
			// 맨뒤에 넣어주기 위해
			idx = list.size();
		}

		for (String i : list) {

			// 자기 순서 이면서 구할 갯수가 t개를 만족할때 까지
			if (count % m == p - 1 && t > 0) {
				answer += i;
				t--;
			}
			count++;
		}
		return answer;
   }

}