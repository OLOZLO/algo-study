package _210413.boj1052;

import java.util.Scanner;

public class Main_jyj {
	/*
	 * 1. 결과 : 성공 
	 * 2. 시간복잡도 : 0
	 * 3. 후기 :
	 * 	이 문제는 규칙은 이해가 되는데 어떻게 해야할지 방법이 잘 떠오르지 않아서
	 *  블로그를 약간 참고해서 풀긴 했습니다. 구현능력이 아직 부족해서 이런 문제를 더 많이 풀어봐야 할거 같네요. 
	 * 4. 풀이 방법 : 
	 * 	1) 합칠수 있을 만큼 최대한 합칩니다.
	 * 	2) N을 2로 나눠서 만약에 나머지가 발생한다면 cnt(내가 들고가는 물병개수)를 1증가 시킨다.
	 * 	3) N이 0이 될때까지 앞의 과정을 반복한다
	 * 	4) cnt가 k 이하일 경우 조건을 만족하므로 result를 출력한다.
	 */

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int n = sc.nextInt();
		int k = sc.nextInt();

		int result = 0;

		if (n <= k) {
			System.out.println(0);
		} else {

			while (true) {
				int cnt = 0;
				int temp = n;
				while (temp > 0) {
					if (temp % 2 == 1)
						cnt++; // 합쳐지지 못한 병의 개수
					temp /= 2;
				}

				if (cnt <= k) // 합쳐지지 못한 병의 개수가 k보다 작으면 break;
					break;
				result++; // 병을 추가한다.
				n++;
			}

			System.out.println(result);
		}

	}
}
