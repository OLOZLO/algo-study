package _210518.boj2257;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * [실버3] 화학식량 
 * 1. 결과 : 틀렸습니다.(9%)
 * 2. 시간복잡도 : O(N)
 * 	- 이유 : 스택을 활용하여 N(화학식 문자열 길이)만큼 순회
 * 
 * 3. 풀이
 * 	(1) 화학식을 스택에 저장
 * 	(2) 하나씩 POP한다.
 * 		IF. 숫자면? 별도로 저장(outerNum)
 * 		ELSE.
 * 			IF. 만약 ')'을 만나면? '('을 만날때까지 순회
 * 				- 숫자 만나면, 다음 원자의 질량과 곱해서 추가
 * 				- 아니면, 원자의 질량을 추가
 * 				- 순회가 끝나면 total * outerNum 추가
 * 			ELSE. 원자의 질량 * outerNum 추가
 * 
 * 	4. 후기
 * 	- 왜 틀렸는지 모르겠어여.. 살려주세여.. ㅠㅡㅠ
 *  - 처음에 스택 안쓰고 구현했는데, 가독성이 너무 안 좋아서 스택을 써봤다. 그래도 틀려서 너무 슬펐다... :(
 *
 */
public class Main_ja {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char[] input = in.next().toCharArray();
		Deque<Character> alphabet = new ArrayDeque<>();
		
		for(char c : input) // 스택에 저장
			alphabet.push(c);
		
		int result = 0; // 모든 원자들의 질량의 합
		int outerNum = 1; // 분자 혹은 원자 뒤에 있는 숫자
		
		while(!alphabet.isEmpty()) { // 화학식 순회
			char cur = alphabet.pop();
			if(Character.isDigit(cur)) // 숫자면 별도로 저장
				outerNum = cur - '0';
			
			else {
				if(cur == ')') { 
					int total = 0;
					while(true) { // '('를 만날때 까지 순회
						char next = alphabet.pop();
						if(next == '(') break; 
						
						if(Character.isDigit(next)) 
							total += getMass(alphabet.pop()) * (next - '0'); // 숫자면 다음 원자의 질량과 곱해서 저장
						else 
							total += getMass(next); // 아니면 그냥 저장
					}
					result += total * outerNum; // 괄호 밖의 있는 숫자와 곱해서 저장
				}else {
					result += getMass(cur) * outerNum; // 뒤에 있는 숫자와 곱해서 저장
				}
				outerNum = 1;
			}
		}
		System.out.println(result);
	}

	public static int getMass(char c) {
		return c == 'H' ? 1 : (c == 'C' ? 12 : 16);
	}

}
