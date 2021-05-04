package _210504.boj15905;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main_ja {
	/**
	 * [실버3] 스텔라(STELLA)가 치킨을 선물했어요
	 * 1. 결과 : 성공
	 * 
	 * 2. 시간복잡도 : O(NlogN) -> 정렬
	 * 
	 * 3. 풀이
	 * 	(1) 해결한 문제 수를 기준으로 내림차순
	 * 		단,  해결한 문제 수가 동일하면 패널티를 기준으로 오름차순
	 * 
	 * 	(2) 5등 참가자를 기준으로 해결한 문제 수가 동일하면서 패널티가 다른 학생 count
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		
		ArrayList<Participant> rank =  new ArrayList<>();
		for (int i = 0; i < N; i++) {
			rank.add(new Participant(in.nextInt(), in.nextInt()));
		}
		
		// 해결한 문제 수를 기준으로 내림차순
		// 해결한 문제 수가 동일하면 패널티를 기준으로 오름차순
		rank.sort(new Comparator<Participant>() {
			@Override
			public int compare(Participant o1, Participant o2) {
				if(o1.score == o2.score)
					return Integer.compare(o1.penalty, o2.penalty);
				return o2.score - o1.score;
			}
		});
		
		Participant rank5 = rank.get(4);
		int result = 0;
		for (int i = 5; i < N; i++) {
			if(rank5.score == rank.get(i).score) // 5등과 해결한 문제 수가 동일한 사람들 count
				result++;
			else
				break;
		}
		System.out.println(result);
		
	}
	static class Participant{
		int score;
		int penalty;
		
		public Participant(int score, int penalty) {
			this.score = score;
			this.penalty = penalty;
		}
	}

}
