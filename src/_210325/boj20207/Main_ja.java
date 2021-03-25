package _210325.boj20207;

import java.util.Scanner;

public class Main_ja{
	/**
	 * [ 달력 ]
	 * 1-365
	 * 
	 * 코팅지를 붙일거야
	 * - 연속된 두 일자에 각각 일정에 1개 이상 있으면, 연속된 일정
	 * 	 -> 하나의 직사각형에 포함
	 * 
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int[] day = new int[366];
		for (int i = 0; i < N; i++) {
			int start = in.nextInt();
			int end = in.nextInt();
			for (int j = start; j <= end; j++) {
				day[j]+=1;
			}
		}
		int result = 0;
		for (int i = 1; i < 366; i++) {
			if(day[i] > 0) {
				int col = 0;
				int max = Integer.MIN_VALUE;
				for (int j = i; j < 366; j++) {
					if(day[j] > 0) {
						col++;
						max = Math.max(max, day[j]);
					}
					else {
						result += col*max;
						i=j;
						break;
					}
				}
			}
		}
		System.out.println(result);
		
	}
	
}
