package _210420.boj2469;

import java.util.Scanner;

public class Main_jyj {
	private static char[][] sadari = new char[1000][25];
	private static char[] startArr = new char[26];
	private static char[] endArr = new char[26];
	private static char[] result = new char[25];
	private static int line = 0;
	private static boolean flag = true;
	
    /**
     * [실버1] 사다리 타기
     * 1. 결과 : 실패
     * 2. 시간 복잡도 : O 
     * 	이유 : 세로 * 가로 (N * K)
     * 	접근 방식
     * 		1) 위에서 부터 사다리를 타면서 '-'를 만날경우 SWAP을 시켜준다
     * 		2) '?'를 만날때 까지 1의 과정을 계속한다.
     *      3) '?'를 만나면 이번에는 맨 밑에서 부터 위로 올라가면서 사다리를 타준다.
     *      4) '?'를 만나면 위에서 내려온 알파벳 위치와 밑에서부터 올라온 알파벳을 비교한다.
     *      5) 비교해서 결과를 도출한다.
     *
     * 3. 후기
     *	- 이 코드를 돌리면 58% 까지 갔다가 FAIL이 나옵니다.
     *	  이론상 맞다고 생각하는데 어느 부분에서 틀린지 잘 모르겠습니다.
     *    막 풀다보니 코드 정리를 잘 못해서 풀면서도 코드 정리하는게 필요할거 같습니다.
     *
     */

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int k = sc.nextInt();
		int n = sc.nextInt();

		String start = "";

		// 처음 알파벳 초기화
		for (int i = 0; i < k; i++) {
			startArr[i] = (char) ('A' + i);
		}

		String end = sc.next();
		// 결과로 나올 알파벳
		for (int i = 0; i < end.length(); i++) {
			endArr[i] = end.charAt(i);
		}

		for (int i = 0; i < n; i++) {
			String str = sc.next();
			for (int j = 0; j < str.length(); j++) {
				sadari[i][j] = str.charAt(j);
				// 만약 사다리에서 '?'인 부분을 만나게 되면 해당 위치를 기억한다.
				if (sadari[i][j] == '?') {
					line = i;
				}
			}
		}

		// 위에서 부터 아래로 사다리를 타고 내려 갑니다.
		for (int i = 0; i < line; i++) {
			for (int j = 0; j < k - 1; j++) {
				// '-'를 만나면 SWAP을 해줍니다.
				if (sadari[i][j] == '-') {
					char temp = startArr[j];
					startArr[j] = startArr[j + 1];
					startArr[j + 1] = temp;
				}
			}
		}
		// 아래에서 부터 위로 사다리를 타고 올라갑니다.
		for (int i = n - 1; i > line; i--) {
			for (int j = 0; j < k - 1; j++) {
				// '-'를 만나면 SWAP을 해줍니다.
				if (sadari[i][j] == '-') {
					char temp = endArr[j];
					endArr[j] = endArr[j + 1];
					endArr[j + 1] = temp;
				}
			}

		}

		// 위에서 내려온 사다리 결과와 아래에서 올라온 사다리 결과를 비교합니다.
		for (int i = 0; i < k - 1; i++) {
			// 위 사다리 결과와 아래 사다리 결과가 같으면 '*'
			if (startArr[i] == endArr[i]) {
				result[i] = '*';
			}

			// 결과가 다르고 위의 사다리 앞뒤와 아래 사다리 앞뒤가 같으면
			else if (startArr[i] == endArr[i + 1] && startArr[i + 1] == endArr[i]) {
				// 결과에 '-'를 추가하고 SWAP 해줍니다.
				result[i] = '-';

				char temp = startArr[i];
				startArr[i] = startArr[i + 1];
				startArr[i + 1] = temp;
			} 
			// 사다리를 넣어도 만들수 없는 경우이기 때문에 flag를 false로 바꿉니다.
			else {
				flag = false;
				break;
			}
		}

		// 사다리 출력
		if (flag) {
			for (int i = 0; i < k - 1; i++) {
				System.out.print(result[i]);
			}
			System.out.println();
		} else {
			for (int i = 0; i < k - 1; i++) {
				System.out.print('x');
			}
			System.out.println();
		}
	}
}
