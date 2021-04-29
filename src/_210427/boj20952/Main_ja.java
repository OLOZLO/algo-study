package _210427.boj20952;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_ja {
	/**
	 * [골드5] 게임 개발자 승희
	 * 
	 * 1. 결과 : 시간초과 -> 맞았습니다.
	 *	※ 틀린 코드는 이전 커밋을 봐주세요!
	 *	※ 다른 사람의 코드를 참고한 풀이법입니다. 참고해주세요!
	 * 
	 * 2. 시간 복잡도 : O(N+M)
	 * 	-> 풀이 참고
	 * 
	 * 3. 풀이
	 * - B순열의 누적합과 A원소를 7로 나눈 나머지를 활용한 풀이법
	 * 
	 * 	(1) A순열 순회 (N)
	 * 		-> A원소들을 7로 나눈 나머지를 가지고 생존 여부를 판단.
	 * 		-> boolean 배열을 준비해서, A원소들을 7로 나눈 나머지는 true(살아있음)을 저장
	 * 
	 * 	(2) B순열 순회 (M)
	 * 		-> B순열의 누적합을 별도로 저장(sum)
	 * 		-> 해당 누적합(sum)에서 삭제될 A원소를 찾기
	 * 			-> 이때, A원소를 순회해서 삭제 여부를 판단하는 것이 아님!
	 * 			-> 해당 누적합(sum)을 7로 나눈 나머지에 어떤 수(x)를 더했을 때 값이 7이 되는지를 계산. => x는 삭제 대상이 됨.
	 * 			-> 즉, 해당 누적합(sum)에서 7로 나눈 나머지가 x값인 A원소는 삭제 대상이다. 
	 * 			-> 여기서, x = (7 - (누적합 % 7)) % 7로 표현할 수 있음.
	 * 				*** 마지막에 %7를 해준 이유는, 7인 경우 0으로 반환하기 위함
	 * 
	 * 		POINT. 살아남은 A원소의 나머지의 개수 체크해주기.
	 * 			-> 해당 누적합(sum)을 더해줄 때, 모든 원소가 삭제되면 삭제 X
	 * 
	 * 	(3) 살아남은 A원소 찾기 (N)
	 * 		-> 해당 A원소를 7로 나눈 나머지가 살아있으면 정답에 추가해주기.
	 * 
	 * 4. 시간초과 : O(NM)
	 * 	- 완탐 : B의 원소 수(M)만큼 A순열(N)을 반복 순회 -> NM
	 * 	- 순차적으로 비교하는 방법밖에 없다고 생각함.
	 *		-> 그래서 최대한 계산하면서 많이 걸러줘야겠다고 생각해서 도전했다가 시초남.
	 *
	 *	- 시초난 풀이 
	 *		(1) M만큼 반복 -> B[i]
	 * 		(2) A 순열 복사해서, 복사한 배열에 B[i]와 더하고, 이떄 7의 배수면 삭제
	 *  	(3) 복사한 배열이 비어있으면(순열 전체 삭제), A순열에 반영 X
	 *  		-> 삭제 여부는 별도의 boolean 배열을 사용
	 *		*** 복사 비용을 줄이기 위해, B순열의 누적합을 구해서 도전했지만 그래도 시초남.
	 * 
	 * 5. 후기
	 * 	- 어떻게하면 효율적으로 삭제할 수 있을지에 대한 생각을 많이 했다.
	 *  - 결과값을 바로바로 원본에 반영해야한다. 라는 생각에 박혀서 다른 방법이 생각나지 않았다 :(
	 *  - 결국 답지를 봤지만, 신박한 방법이라 기억에 오래 남을 듯!
	 *  - 이게 왜 골5야! 파들
	 */
	static int[] inputToIntArr(String str) {
		return Arrays.stream(str.split(" ")).mapToInt(Integer::parseInt).toArray();
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] input = inputToIntArr(br.readLine());
		int N = input[0];
		int M = input[1];

		int[] A = inputToIntArr(br.readLine());
		int[] B = inputToIntArr(br.readLine());

		// A원소들을 7로 나눈 나머지를 대상으로 생존 여부를 저장할 것
		boolean[] remain = new boolean[7];

		int cnt = 0; // 살아있는 A원소들의 나머지 개수
	
		for (int i = 0; i < N; i++) {
			if (!remain[A[i] % 7]) {
				remain[A[i] % 7] = true; // A[i]를 7로 나눈 나머지는 true(살아있음)을 저장!
				cnt++; 
			}
		}

		long sum = 0; // B원소의 누적합
		for (int i = 0; i < M; i++) {
			sum += B[i];
			int remove = (int) ((7 - (sum % 7)) % 7); // 해당 누적합(sum)의 나머지에 어떤 나머지(remove)를 더했을 때 7이 되는지를 계산

			if (!remain[remove]) continue; // 이미 삭제된 나머지면, pass
			
			if (cnt == 1) { // 남은 나머지가 1개인 경우, 제거 취소!
				sum -= B[i];
				continue;
			}
			remain[remove] = false; // 나머지 제거
			cnt--;
		}

		int aCnt = 0;
		StringBuilder aList = new StringBuilder();

		for (int i = 0; i < N; i++) { // A원소를 7로 나눈 나머지가 살아남은 원소만 출력
			if (!remain[A[i] % 7]) continue;
			aCnt++;
			aList.append((int) ((sum + A[i]) % (1e9 + 7)) + " ");
		}

		System.out.println(aCnt);
		System.out.println(aList);
	}

}
