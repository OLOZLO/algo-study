package _210216.prog42889;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main_jyj {

	public int[] solution(int N, int[] stages) {

		int[] arr = new int[N];
		double[] result = new double[N + 1];

		ArrayList<Stage> list = new ArrayList<Stage>();

		// 몇번째 스테이지에 몇명이 있는지
		for (int i = 0; i < stages.length; i++) {
			arr[stages[i] - 1]++;
		}

		// 스테이지 1부터 누적되는 사용자
		int cnt = 0;

		for (int i = 0; i < result.length - 1; i++) {

			// 스테이지에 도달한 사람이 한명이라도 있다면 실패율 계산
			if (arr[i] != 0) {
				result[i] = arr[i] / (double) (stages.length - cnt);
				cnt += arr[i];
			}
			// 스테이지에 도달한 사람이 없다면 실패율은 0
			else {
				result[i] = 0;
			}

			// 실패율이 숫자인지 판단해서 숫자면 list에 넣어준다.
			if (!Double.isNaN(result[i])) {
				list.add(new Stage(i, result[i]));
			}

		}

		Comparator<Stage> comparatorSort = new Comparator<Stage>() {

			@Override
			public int compare(Stage o1, Stage o2) {

				// 내림차순
				if (o1.failPoint > o2.failPoint) {
					return -1;
				}
				// 만약 실패율이 같다면 더 낮은 스테이지가 우선
				else if (o1.failPoint == o2.failPoint) {
					return o1.idx - o2.idx;
				}

				return 1;
			}
		};

		// 정렬 해준다.
		Collections.sort(list, comparatorSort);

		int[] answer = new int[N];

		for (int i = 0; i < N; i++) {
			answer[i] = list.get(i).idx + 1;
		}

		for (int i = 0; i < answer.length; i++) {
			System.out.println(answer[i]);
		}

		return answer;
	}

	// 스테이지의 번호와 실패율을 저장하기 위한 클래스 선언
	public class Stage {
		int idx;
		double failPoint;

		public Stage(int idx, double failPoint) {
			this.idx = idx;
			this.failPoint = failPoint;
		}
	}

}
