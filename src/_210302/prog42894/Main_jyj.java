package _210302.prog42894;

import java.util.*;

public class Main_jyj {
	int N;
	int[][] Board;

	// 현재 좌표 위로 박스가 있는지 없는지 판단한다
	boolean canFill(int row, int col) {
		for (int i = 0; i < row; i++) {
			// 박스가 있다면 채울수 없다
			if (Board[i][col] != 0)
				return false;
		}
		// 박스가 없으니 채울수 있다.
		return true;
	}

	boolean find(int row, int col, int h, int w) {

		int emptyCnt = 0;
		// 범위 안에 값이 같은 값인지를 판단하기위한 값 lastValue
		int lastValue = -1;

		for (int r = row; r < row + h; ++r) {
			for (int c = col; c < col + w; ++c) {
				if (Board[r][c] == 0) {
					// 채울 수 있는지 검사
					if (!canFill(r, c))
						return false;
					// 채울수 있는 개수는 2개가 초과되면 안된다.
					if (++emptyCnt > 2)
						return false;
				} else {
					// 블록이 같은 값이 맞는지 판단한다.
					if (lastValue != -1 && lastValue != Board[r][c])
						return false;
					lastValue = Board[r][c];
				}
			}
		}

		// 상자를 채워서 꽉찬 직사각형을 만들었기 때문에 지워준다.
		for (int r = row; r < row + h; ++r) {
			for (int c = col; c < col + w; ++c) {
				Board[r][c] = 0;
			}

		}

		return true;

	}

	public int solution(int[][] board) {
		Board = board;
		N = board.length;

		int answer = 0;

		// 지울 수 있는 블록
		int cnt;

		do {
			cnt = 0;

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// 세로 2 가로 3 크기의 사각형을 검사한다.
					if (i <= N - 2 && j <= N - 3 && find(i, j, 2, 3)) {
						++cnt;
					}
					// 세로 3 가로 2 크기의 사각형을 검사한다.
					else if (i <= N - 3 && j <= N - 2 && find(i, j, 3, 2)) {
						++cnt;
					}
				}
			}

			answer += cnt;

		} while (cnt != 0);

		return answer;
	}
}
