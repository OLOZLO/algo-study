package _210427.boj20952;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main_ja {
	/**
	 * [골드5] 게임 개발자 승희
	 * 
	 * 1. 결과 : 시간초과
	 * 2. 시간복잡도 : O(N^2*M)
	 * 	- 이유
	 * 	(1) B원소 하나당 N을 전부 순회하는데, B의 원소 수 (M)만큼 반복 -> NM
	 *  (2) A 순열를 복사 후, 값 변경 -> N
	 *  	=> 따라서, O(N^2M)
	 *  
	 * 3. 풀이
	 * 	(1) M만큼 반복 -> B[i]
	 * 		이때, B[i]이 7의 배수면 pass
	 * 	(2) A 순열 복사해서, 복사한 배열에 B[i]와 더하고, 이떄 7의 배수면 삭제
	 *  (3) 복사한 배열이 비어있으면(순열 전체 삭제), A 순열에 반영 X
	 *  	-> 삭제 여부는 별도의 boolean 배열을 사용
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
		boolean[] check = new boolean[N];
		int cnt = N;
		
		// B 순열의 원소를 하나씩 꺼내기
		for (int i = 0; i < M; i++) {
			if(B[i]%7 == 0) continue; // B의 원소가 7의 배수면 pass 
			
			int[] add = A.clone(); // A 순열의 복사본 만들기
			ArrayList<Integer> change = new ArrayList<>(); // 삭제한 원소의 idx값 저장
			for (int j = 0; j < N; j++) {
				if(check[j]) continue;
				
				if((add[j]+=B[i]) % 7 == 0) { // 삭제 대상
					cnt--;
					change.add(j);
				}
			}
			
			if(cnt > 0) { // 삭제되지 않은 원소가 있으면 원본에 반영
				A = add;
				for(int c : change)
					check[c] = true;
			}
		}
		
		String result = "";
		for (int i = 0; i < N; i++) {
			if(check[i]) continue;
			result += A[i] + " ";
		}
		
		System.out.println(cnt);
		System.out.println(result);
	}
	
}
