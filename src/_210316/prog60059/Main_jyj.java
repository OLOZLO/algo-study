package _210316.prog60059;

public class Main_jyj {
	public boolean solution(int[][] key, int[][] lock) {

		// 최소 하나의 칸이 겹쳐야 하기 때문에
		// 맨 처음 위치를 잡기 위한 offset
		int offset = key.length - 1;

		for (int r = 0; r < offset + lock.length; r++) {
			for (int c = 0; c < offset + lock.length; c++) {

				// 90도 회전 경우의 수
				for (int rot = 0; rot < 4; rot++) {
					int[][] arr = new int[58][58];

					// 자물쇠 복사
					for (int i = 0; i < lock.length; i++) {
						for (int j = 0; j < lock.length; j++) {
							arr[offset + i][offset + j] = lock[i][j];
						}
					}

					// 키 복사
					match(arr, key, rot, r, c);
					if (check(arr, offset, lock.length)) {
						return true;
					}

				}
			}
		}

		return false;
	}

	private boolean check(int[][] arr, int offset, int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (arr[offset + i][offset + j] != 1) {
					return false;
				}
			}
		}

		return true;
	}

	private void match(int[][] arr, int[][] key, int rot, int r, int c) {
		int n = key.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (rot == 0) {
					arr[r + i][c + j] += key[i][j];
				} else if (rot == 1) {
					arr[r + i][c + j] += key[j][n - 1 - i];
				} else if (rot == 2) {
					arr[r + i][c + j] += key[n - 1 - i][n - 1 - j];
				} else if (rot == 3) {
					arr[r + i][c + j] += key[n - 1 - j][i];
				}
			}
		}

	}
}
