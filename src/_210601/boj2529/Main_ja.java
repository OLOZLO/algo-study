package _210601.boj2529;

import java.util.Scanner;

public class Main_ja {
	/**
	 * [실버3] 부등호
	 * 1. 결과 : 맞았습니다.
	 * 2. 시간복잡도 : O((k+1)!) -> 최악의 경우, 10! 
	 * 	- 이유 : 순열 알고리즘 사용
	 * 
	 * 3. 풀이 
	 * 	(1) 순열 알고리즘을 적용하여 수 생성
	 * 	(2) 0-9까지 전부 배치한 경우, 앞에서부터 순회
	 * 		-> 해당 위치의 부등호 관계를 만족 시키는지 확인
	 * 
	 * 4. 후기
	 * 	- k가 최대 9라서 충분히 순열로 가능할 것이라 판단했다.
	 * 	- int 범위를 넘는 경우를 생각하지 못했다.. 틀린 이유를 너무 늦게 발견함.. ㅠ-ㅠ 
	 */
	static char[] compare;
	static boolean[] used;
	static String[] result;
	static int K;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		K = in.nextInt(); 
		compare = new char[K];
		used = new boolean[10];
		for (int i = 0; i < K; i++) {
			compare[i] = in.next().charAt(0);
		}
		result = new String[2];
		permutation(new int[K+1], 0);

		System.out.println(((max+"").length()!=K+1 ? "0":"") + max);
		System.out.println(((min+"").length()!=K+1 ? "0":"") + min);
	}
	
	static long max = Long.MIN_VALUE, min = Long.MAX_VALUE;
	
	public static void permutation(int[] selected, int idx) {
		if(idx == K+1) {
			boolean check = true;
			long num =(long) (selected[0] * Math.pow(10, K));
			for (int i = 0; i < K; i++) {
				if( (compare[i] == '>' && selected[i] < selected[i+1]) 
						|| (compare[i] == '<' && selected[i] > selected[i+1]) ) {
					check = false;
					break;
				}
				num += selected[i+1]*Math.pow(10, K-1-i);
			}
			if(check) {
				System.out.println(num);
				if(max < num) max = num;
				if(min > num) min = num;
			}
			return;
		}
		for (int i = 0; i <= 9; i++) {
			if(used[i]) continue;
			used[i] = true;
			selected[idx] = i;
			permutation(selected, idx+1);
			used[i] = false;
		}
	}

}
