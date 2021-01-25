import java.util.*;

public class Main_yj {
class Solution {
    public static String[] solution(String[] files) {
		String[] answer = new String[files.length];
		// 원본 글자, 헤드 , 넘버를 저장하기 위한 2차배열
		String[][] headNumberTail = new String[files.length][3];

		for (int i = 0; i < files.length; i++) {

			// 원본, 헤드 ,넘버 초기화
			headNumberTail[i][0] = "";
			headNumberTail[i][1] = "";
			headNumberTail[i][2] = "";

			// 원본 글자를 0번째 인덱스에 저장
			headNumberTail[i][0] = files[i];

			// 나머지 부분 대문자 변형
			files[i] = files[i].toUpperCase();

			// 헤더 부분 저장
			headNumberTail[i][1] = files[i].split("[0-9]")[0];

			// 헤더 부분을 제거한 나머지 부분
			files[i] = files[i].replace(headNumberTail[i][1], "");

			char[] arr = files[i].toCharArray();

			for (char c : arr) {
				// 숫자이면서 숫자의 크기는 5자리를 넘지 않으면
				if (Character.isDigit(c) && headNumberTail[i][2].length() < 5) {
					headNumberTail[i][2] += c;
				} else {
					break;
				}
			}
		}

		Arrays.sort(headNumberTail, new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				// 헤드가 같으면
				if (o1[1].equals(o2[1])) {
					// 넘버를 비교
					return Integer.parseInt(o1[2]) - Integer.parseInt(o2[2]);
				}
				// 같지 않으면
				else {
					return o1[1].compareTo(o2[1]);
				}
			}
		});

		for (int i = 0; i < files.length; i++) {
			answer[i] = headNumberTail[i][0];
		}
		return answer;
    }
}
}