package _210504.boj12764;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main_ja {
	/**
	 * [골드3] 싸지방에 간 준하
	 * 1. 결과 : 성공
	 * 
	 * 2. 시간복잡도 : O(NlogN) -> 정렬
	 * 
	 * 3. 풀이
	 *	(1) 참가자 리스트를 입장시간을 기준으로 오름차순 정렬
	 *	(2) 한명 씩 순회
	 *		- 한명 씩 앉히기 -> 입장한 사람은 우선순위 큐에 추가 (seated.add) => 퇴장 시간을 기준으로 정렬
	 *		- 내가 입장하기 전에 퇴장할 사람 있으면 다 퇴장 시키기 (seated.poll)
	 *		- 앞 자리부터 빈 자리 있으면 내 자리
	 * 		- 빈 자리도 없으면 자리 추가
	 * 
	 * 4. 후기
	 * 	- 번호가 낮은 순부터 채워야한다는 점을 까먹고 구현했다가 다시 짰다..
	 *  - 우선순위 큐를 써서 메모리, 시간 둘 다 비효율적인 코드다.
	 *  	=> 그냥 배열에 저장해서 순차적으로 확인해도 될 듯! 나중에 다시 구현해 볼 예정!
	 */
	static int lastNumber;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int[] seatCnt = new int[100_001]; // 의자에 앉은 횟수
		
		ArrayList<Person> people = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			people.add(new Person(in.nextInt(), in.nextInt()));
		}
		// 입장시간을 기준으로 오름차순
		people.sort((new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				return Integer.compare(o1.start, o2.start);
			}
		}));
		
		// 입장한 사람들 저장
		// 퇴장 시간을 기준으로 오름차순
		PriorityQueue<Person> seated = new PriorityQueue<>(new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				return Integer.compare(o1.end, o2.end);
			}
		});
		
		// 사용 중인 좌석 체크
		boolean[] check = new boolean[100_001];
		
		// 입장 시작
		people.forEach(p -> {
			// p가 입장하기 전에 퇴장할 사람들
			while(seated.size() != 0 && p.start >= seated.peek().end) {
				int ouput = seated.poll().seatNumber; //  퇴장
				check[ouput] = false; // 좌석 비움
			}
			
			
			int number = -1;
			// 빈 자리가 있으면 내 자리
			for (int i = 0; i < lastNumber; i++) {
				if(check[i]) continue;
				number = i;
				break;
			}
			// 없으면 자리 추가
			if(number == -1) number = lastNumber++;
			check[number] = true;
			
			// 앉은 횟수 증가
			seatCnt[number]++;
			
			// 앉은 사람 추가
			p.seatNumber = number++;
			seated.add(p);
		});
		
		System.out.println(lastNumber);
		for (int i = 0; i < lastNumber; i++) {
			System.out.print(seatCnt[i] + " ");
		}
		
	}
	static class Person{
		int seatNumber;
		int start;
		int end;
		
		public Person(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
	}

}
