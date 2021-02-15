package _210216.prog42890;

import java.util.ArrayList;
import java.util.HashSet;

public class Main_jyj {

	private int columnCount;
	private int rowCount;
	private ArrayList<Integer> keyList = new ArrayList<>();

	public int solution(String[][] relation) {
		columnCount = relation[0].length;
		rowCount = relation.length;

		// 비트마스킹을 이용해 모든 경우의 수를 구한다.
		// 최소 하나는 있어야 하기 때문에 1부터 시작
		for (int bitMask = 1; bitMask < (1 << columnCount); bitMask++) {

			// 최소성을 만족하지 않으면 continue
			if (isSubKey(bitMask) == true)
				continue;

			// 유일성을 만족하면 후보키 리스트에 추가
			if (isUnique(bitMask, relation)) {
				keyList.add(bitMask);
			}

		}

		return keyList.size();
	}

	// 유일성 판단
	private boolean isUnique(int bitMask, String[][] relation) {
		// 중복 여부를 판단하기위해 HashSet 사용
		HashSet<String> checkKeySet = new HashSet<>();
		StringBuilder sb = new StringBuilder();

		for (int r = 0; r < rowCount; r++) {
			sb.setLength(0);

			for (int c = 0; c < columnCount; c++) {
				// 비트마스크와 같은 경우인지 판단
				if ((bitMask & (1 << c)) != 0) {
					sb.append(relation[r][c]);
				}

			}

			String key = sb.toString();
			// 같은 문자가 포함되는지 판단
			if (checkKeySet.contains(key) == false) {
				checkKeySet.add(key);
			} else {
				return false;
			}
		}

		return true;
	}

	// 최소성을 만족하는지 검사
	private boolean isSubKey(int bitMask) {

		for (Integer subKey : keyList) {

			// 만약 subKey가 0001 이고 bitMask가 0011 이라면
			// subKey가 bitMask에 부분집합으로 포함되기 때문에 최소성을 만족하지 않는다.

			// subKey가 bitMask에 포함여부 판단 = 최소성 판단
			if ((bitMask & subKey) == subKey) {
				return true;
			}
		}

		return false;
	}

}