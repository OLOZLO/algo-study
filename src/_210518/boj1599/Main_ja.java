package _210518.boj1599;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

/**
 *	[골드5] 민식어 
 * 1. 결과 : 맞았습니다.
 * 2. 시간복잡도 : O(NlogN)
 * 	- 이유 
 * 		-> 민식어를 알파벳으로 바꾸기 : O(N * 문자열길이), 문자열 길이는 최대 50자.
 * 		-> 사전 순으로 정렬 : O(NlogN)
 * 		따라서, O(N*문자열길이 + NlogN) => O(NlogN) 
 * 
 * 3. 풀이
 * 	(1) 민식어 사전 생성
 * 		-> 해시맵을 사용하여, 민식어와 일반 알파벳을 매칭한 사전 생성
 * 	(2) 주어진 민식어를 순회해서 일반 알파벳으로 바꿈
 * 		-> 모든 ng를 z으로 변환한 후, 단어를 순회하여 일반 알파벳으로 바꿈 (z로 한 이유는, 민식어에서 등장하지 않는 알파벳이기 때문)
 *  (3) 일반 알파벳으로 바꾼 문자열을 index 포함하여 별도 배열에 저장 -> 오름차순으로 정렬
 *  (4) 정렬한 배열을 순회해서 해당 인덱스를 가진 민식어를 print
 *  
 * 4. 후기
 * 	- ng를 어떻게 처리해야 할지 고민을 많이 했다.
 *  - 주어진 범위들이 빡빡하지 않아서 편하게 구현했다.
 *  - 정규식을 쓰고 싶었는데 자바 정규식을 어케 쓰는지 기억이 안나더라 :(
 *  
 */
public class Main_ja {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		char[] alphabets = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't'}; // 일반 알파벳
		char[] minsik =    {'a', 'b', 'k', 'd', 'e', 'g', 'h', 'i', 'l', 'm', 'n', 'z', 'o', 'p', 'r', 's', 't', 'u', 'w', 'y'}; // 민식어 (ng -> z)
		
		HashMap<Character, Character> dictionary = new HashMap<>();
		for (int i = 0; i < minsik.length; i++) { // 민식어와 일반 알파벳과 매칭한 사전 생성
			dictionary.put(minsik[i], alphabets[i]);
		}
		
		ArrayList<String> minsikWords = new ArrayList<>(); // 주어진 민식어 단어
		ArrayList<Word> words = new ArrayList<>(); // 민식어 단어를 일반 알파벳으로 변환한 결과를 저장
		
		for (int i = 0; i < N; i++) {
			String origin = in.next();
			
			String check = origin.replace("ng","z"); // 모든 ng는 z로 변환
			String word = ""; // 민식어를 일반 알파벳으로 변환한 결과물
			for(char c : check.toCharArray()) { // 민식어를 일반 알파벳으로 변환
				word += dictionary.get(c);
			}
			
			minsikWords.add(origin); // 원본 민식어 저장
			words.add(new Word(i, word)); // 변환한 일반 알파벳 저장
		}
		
        words.stream()
                .sorted(Comparator.comparing(o -> o.str))
                .forEach(word -> System.out.println(minsikWords.get(word.idx)));
	}
	
	static class Word{
		int idx;
		String str;
		public Word(int idx, String str) {
			this.idx = idx;
			this.str = str;
		}
		
	}
}
