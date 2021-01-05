package _210105.prog17682;

import java.util.Stack;

// 다트게임
public class Main_ja {
	/**
	 * 다트게임 - 3번 기회
	 * 점수 : 0 ~ 10
	 * 
	 * S(Single) D(Double) T(Triple) 영역
	 * - 각각 점수의 1제곱, 2제곱, 3제곱으로 계산 
	 * - 점수마다 하나씩 존재
	 * 
	 * 스타상(*) 해당 점수와 바로 전에 얻은 점수를 각 2배로 만듬
	 * 아차상(#) 해당 점수는 마이너스
	 * 
	 * 스타상(*)
	 * - 첫 번째 기회에서도 올 수 있음 -> 첫 번째 스타상의 점수만 2배
	 * - 다른 스타상과 중첩 효과 있음 -> 중첩된 스타상 점수는 4배
	 * - 아차상과 중첩 효과 있음 -> 아차상의 점수는 -2배
	 * 
	 * 스타상, 아차상은 점수마다 둘 중 하나만 존재. 존재하지 않을 수도.
	 * 
	 * 총 점수를 반환하는 함수
	 * 
	 */

	public static void main(String[] args) {
		Main_ja test = new Main_ja();
		System.out.println(test.solution("1S*2T*3S"));
	}

	public int solution(String dartResult) {
		int answer = 0;
		Stack<Integer> result = new Stack<>();
		int pointer = 0;
		
		for (int i = 0; i < dartResult.length(); i++) {
			char dart = dartResult.charAt(i);

			if(Character.isDigit(dart)) continue;
			
			String str = dartResult.substring(pointer, i);
			int num = !str.isBlank()?Integer.parseInt(str):0;
			switch (dart) {
			case 'S':
				break;
			case 'D':
				num = num*num;
				break;
			case 'T':
				num = num*num*num;
				break;
			case '*':
				num = result.pop()*2;
				if(!result.isEmpty())result.push(result.pop()*2);
				break;
			case '#':
				num = result.pop()*-1;
				break;
			}
			pointer=i+1;
			result.push(num);
			
		}
		while(!result.isEmpty())answer+=result.pop();
		
		return answer;
	}
}
