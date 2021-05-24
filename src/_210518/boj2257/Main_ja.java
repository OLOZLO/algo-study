package _210518.boj2257;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * [실버3] 화학식량 
 * 1. 결과 : 틀렸습니다.(9%) -> 맞았습니다.
 * 2. 시간복잡도 : O(N)
 * 	- 이유 : 스택을 활용하여 N(화학식 문자열 길이)만큼 순회
 * 
 * 3. 풀이
 * 	(1) 화학식 순회
 * 		IF. 숫자면, 
 * 			stack에서 원자 꺼내서 숫자 곱한 후, 다시 집어넣기
 * 		IF. ')' 이면, 
 * 			스택에서 '(' 만날때 까지 원자를 다 더 해준 후, 다시 집어넣기
 * 		ELSE.  
 * 			'('이면 0, 원자(알파벳)면 해당 원자의 질량 값을 스택에 넣어주기
 * 
 * 4. 틀린 코드 풀이 	※ 이전 커밋을 봐주세요! 코드를 새로 짜서..
 * 	(1) 화학식을 스택에 저장
 * 	(2) 하나씩 POP한다.
 * 		IF. 숫자면? 별도로 저장(outerNum)
 * 		ELSE.
 * 			IF. 만약 ')'을 만나면? '('을 만날때까지 순회
 * 				- 숫자 만나면, 다음 원자의 질량과 곱해서 추가
 * 				- 아니면, 원자의 질량을 추가
 * 				- 순회가 끝나면 total * outerNum 추가
 * 			ELSE. 원자의 질량 * outerNum 추가
 *  - 왜 틀렸을까?
 *  	-> 괄호 안에 또 다른 괄호가 있는 경우를 고려 안함! [EX] ((O)3H)2
 *  
 * 	5. 후기
 * 	- 왜 틀렸는지 모르겠어여.. 살려주세여.. ㅠㅡㅠ
 *  - 처음에 스택 안쓰고 구현했는데, 가독성이 너무 안 좋아서 스택을 써봤다. 그래도 틀려서 너무 슬펐다... :(
 *  
 *  [수정]
 *  - 괄호 안에 괄호가 또 존재하다니.. ㄱㅇㄴ
 *  
 */
public class Main_ja {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Deque<Integer> stack = new ArrayDeque<>();
		
		in.next().chars().forEach(ch -> {
			if(Character.isDigit(ch)) { // 숫자면 스택에서 원자 하나 꺼낸 후, 곱해서 다시 저장 
				stack.push(stack.pop() * (ch - '0'));
			}
			else if(ch == ')') { 
				int total = 0;
				int cur =  1;
				while((cur = stack.pop()) != 0)  // '(' 만날 떄 까지 pop한 원자의 질량 추가
					total += cur;
				stack.push(total); // 다시 집어 넣기
			}
			else { 
				stack.push(ch != '(' ? getMass((char) ch) : 0); // 원자의 질량으로 바꿔서 저장, '('인 경우 0으로 저장
			}
		});
		System.out.println(stack.stream().reduce(0, Integer::sum));
	}

	public static int getMass(char c) {
		return c == 'H' ? 1 : (c == 'C' ? 12 : 16);
	}

}
