package _210427.boj1072;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


/*
* 1. 결과 : 성공
* 2. 시간복잡도 : O(logX)
* 3. 풀이
*	1) 99%의 승률에서 몇번을 이겨도 100%는 될 수 없다. 따라서
*   2) X와 Y에 대해 Z가 99 이상이면 -1을 출력합니다.
*	3) 새로 찾는 확률은 ((Y + 횟수) * 100) / (X + 횟수)입니다.
*	4) 따라서, start = 0, end = x로 두고 이분탐색을 통해 확률이 달라지는 횟수를 찾습니다.
*	5) temp >= Z인 지점에서 결과가 갱신되기 때문에 mid +1 이 결과입니다.
* 
*/

public class Main_jyj {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine());

		long x = Long.parseLong(st.nextToken());
		long y = Long.parseLong(st.nextToken());
		long z = y * 100 / x;

		if (z >= 99) {
			bw.write("-1");
		}

		// 이분탐색
		else {
			long start = 0;
			long end = x;

			while (start <= end) {
				long middle = (start + end) / 2;

				long temp = (y + middle) * 100 / (x + middle);
				if (temp > z) {
					end = middle - 1;
				} else {
					start = middle + 1;
				}
			}

			bw.write(start + "");
		}
		bw.flush();
		bw.close();
	}
}