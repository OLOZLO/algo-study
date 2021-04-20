package _210420.boj20440;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class Main_ja {
	/** 
	 * [골드4] -🎵 니가 싫어 싫어 너무 싫어 싫어 오지 마 내게 찝쩍대지마🎵 - 1
	 * 1. 결과 : 틀렸습니다. (1%)
	 * 2. 시간복잡도 : ?
	 * 3. 접근 방식
	 *  (1) 입력을 시작 시간 기준으로 오름차순 정렬
	 *  (2) 모기를 하나씩 순회해서, 해당 모기에서 겹칠 수 있는 모기를 count함.
	 *  	-> 그 중 count가 가장 큰 부분을 기록함
	 * 
	 * - 후기
	 * - 연속 구간 전체에서 멘붕이 왔습니다... 아직 어떻게 풀어야할 지 감을 못잡은 상태.
	 * 
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		Time[] mosquito = new Time[N];
		for (int i = 0; i < N; i++) {
			mosquito[i] = new Time(in.nextInt(), in.nextInt());
		}
		
		Arrays.sort(mosquito, new Comparator<Time>() {
			@Override
			public int compare(Time o1, Time o2) {
				return Integer.compare(o1.start, o2.start);
			}
		});
	
		Time maxTime = mosquito[0];
		int maxCnt = 0;
		
		for (int i = 0; i < N; i++) {
			Time pre = mosquito[i];
			Time max = new Time(mosquito[i].start, mosquito[i].end);
			int cnt = 1;
			// i번째 모기와 겹치는 j번째 모기를 count
			for (int j = 0; j < N; j++) {
				if(i == j) continue;
				Time next = new Time(mosquito[j].start, mosquito[j].end);
				// i번째 모기랑 완전히 분리된 모기인 경우 break
				if(pre.end <= next.start) {
					if(maxCnt < cnt) {
						maxCnt = cnt;
						maxTime = new Time(pre.start, pre.end);
					}
					break; 
				}
				// 두 구간이랑 i번째가 겹칠 경우, 1번 구간이 끝나면 2번 구간도 체크해야하니까 초기화
				if(max.end <= next.start){
					if(maxCnt < cnt) {
						maxCnt = cnt;
						maxTime =  new Time(pre.start, pre.end);
					}
					max = new Time(mosquito[i].start, mosquito[i].end);
					cnt = 0;
					j--;
					continue;
				}
				max.start = Math.max(max.start, next.start);
				max.end = Math.min(max.end, next.end);
				cnt++;
			}
			
		}
		System.out.println(maxCnt);
		System.out.println(maxTime.start + " " + maxTime.end);
	}
	
	static class Time {
		int start;
		int end;
		public Time(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
	}
}
