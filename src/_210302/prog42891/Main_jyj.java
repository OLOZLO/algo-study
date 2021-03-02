package _210302.prog42891;

import java.util.*;

class Solution {
	public int solution(int[] food_times, long k) {
		List<Food> foods = new LinkedList<Food>();
		int N = food_times.length, i, pretime;

		// 리스트에 음식 추가
		for (i = 0; i < N; i++) {
			foods.add(new Food(food_times[i], i + 1));
		}
		// 시간이 짧게 걸리는 음식 순으로 정렬
		foods.sort(CompTime);

		pretime = 0; // 이전시간
		i = 0; // 몇번째 처리하고 있는지 i

		for (Food f : foods) {
			// diff는 현재 위치의 음식의 시간 - 이전 시간
			long diff = f.time - pretime;
			if (diff != 0) {
				// diff가 0이 아니므로 소모되는 시간을 계산해 준다
				long spend = diff * N;
				// diff 만큼의 시간이 k보다 작다면
				if (spend <= k) {
					// k에서 빼주고
					k -= spend;
					// 이전 시간의 현재 위치의 과일 시간이 된다.
					pretime = f.time;
				} else {
					// k를 n으로 나누고
					k %= N;
					// 인덱스 순서로 정렬된 배열에서 i+k 번째가 원하는 정답이 된다.
					foods.subList(i, food_times.length).sort(CompIdx);
					return foods.get(i + (int) k).idx;
				}
			}
			++i;
			--N;
		}

		return -1;
	}

	class Food {
		int time;
		int idx;

		Food(int time, int idx) {
			this.time = time;
			this.idx = idx;
		}
	}

	// 먹는 시간이 짧은 순서로 정렬
	Comparator<Food> CompTime = new Comparator<Food>() {
		public int compare(Food a, Food b) {
			return a.time - b.time;
		}
	};

	// 음식의 인덱스가 낮은 수대로 정렬
	Comparator<Food> CompIdx = new Comparator<Food>() {
		public int compare(Food a, Food b) {
			return a.idx - b.idx;
		}
	};
}