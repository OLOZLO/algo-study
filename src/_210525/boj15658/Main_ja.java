package _210525.boj15658;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main_ja {
	/**
	 * [실버3] 연산자 끼워넣기(2)
	 * 1. 결과 : 맞았습니다.
	 * 2. 시간복잡도 : O((4*연산자 등장횟수)^N)
	 * 
	 * 3. 풀이
	 * (1) 각 연산자의 등장 횟수만큼 배열에 저장(opList)
	 * (2) DFS 탐색으로 모든 연산자를 숫자 사이에 집어 넣어보기
	 * 		-> 연산식을 hashSet에 저장하여 중복 연산 체크
	 * 		-> 사용한 연산자 체크
	 * 
	 * 4. 후기
	 * - 세 문제 중 가장 오래 걸렸던 문제. (그래서 3번은 접근도 못함)
	 * - 처음에는, 중복 연산식에 대해 체크를 안함 -> 시초 발생
	 * 
	 */
	static int N, min, max;
	static int[] data;
	static ArrayList<Character> opList;
	static HashSet<String> duplication;
	static boolean[] used;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		N = in.nextInt();
		data = new int[N];
		for (int i = 0; i < N; i++) {
			data[i] = in.nextInt();
		}
		
		char[] op = {'+', '-', '*', '/' };
		
		opList = new ArrayList<>();
		duplication = new HashSet(); // 중복 연산식 제거
		
		// 해당 연산자 개수만큼 배열에 추가
		for (int idx = 0; idx < 4; idx++) {
			int n = in.nextInt();
			while(n-- > 0)
				opList.add(op[idx]);
		}
		
		used = new boolean[opList.size()]; // 사용한 연산자 체크
		min = Integer.MAX_VALUE;
		max = Integer.MIN_VALUE;
		
		solve(data[0], 1, data[0]+"");
		
		System.out.println(max);
		System.out.println(min);
	}
	
	static public void solve(int result, int idx, String exp) {
		if(idx == N) {
			max = Math.max(max, result);
			min = Math.min(min, result);
			return;
		}
		for (int j = 0; j < opList.size(); j++) {
			String newExp = exp + opList.get(j) + data[idx] + "";
			if(used[j] || duplication.contains(newExp)) continue; // 사용했던 연산자거나, 중복 연산식이면 pass
			
			used[j] = true;
			duplication.add(exp);
			solve(calculator(result, data[idx], opList.get(j)), idx + 1, newExp);
			used[j] = false;
		}
	}
	
	static public int calculator(int a,int b,char op) {
		switch (op) {
		case '+':
			return a+b;
		case '-':
			return a-b;
		case '*':
			return a*b;
		case '/':
			return a/b;
		}
		return 0;
	}

}
