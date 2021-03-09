package _210309.prog60058;

import java.util.Stack;

public class Main_jyj {

	int pos;

	// 제일 짧은 균형잡힌 괄호를 찾는다
	boolean isCorrect(String str) {
		boolean ret = true;
		// left는 열림 , right는 닫힘
		int left = 0, right = 0;
		Stack<Character> mystack = new Stack<Character>();

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') {
				left++;
				mystack.push('(');
			} else {
				right++;
				// ')' 일때 stack이 비어있다면 올바른 괄호 문자열이 아니다
				if (mystack.empty())
					ret = false;
				else
					mystack.pop();
			}
			if (left == right) {
				pos = i + 1;
				return ret;
			}
		}

		return true;
	}

	public String solution(String p) {

		// 입력이 빈 문자열인 경우, 빈 문자열을 반환합니다.
		if (p.isEmpty())
			return p;

		// 문자열 w를 두 "균형잡힌 괄호 문자열" u, v로 분리합니다.
		boolean correct = isCorrect(p);
		String u = p.substring(0, pos);
		String v = p.substring(pos, p.length());

		// 문자열 u가 "올바른 괄호 문자열" 이라면 문자열 v에 대해 1단계부터 다시 수행합니다.
		if (correct) {
			return u + solution(v);
		}

		String answer = "(" + solution(v) + ")";

		for (int i = 1; i < u.length() - 1; i++) {
			if (u.charAt(i) == '(') {
				answer += ")";
			} else
				answer += "(";
		}

		return answer;
	}
}
