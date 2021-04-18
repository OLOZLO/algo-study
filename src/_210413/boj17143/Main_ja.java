package _210413.boj17143;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * [골드2] 낚시왕
 * 1. 결과 : 성공
 * 2. 시간 복잡도 : C*(R*C)*(R*2-1) = (CR)^2 = 100,000,000 ...?
 * 	  - 이유
 * 		접근 방식을 토대로 나옴.
 * 	
 * 	  - 범위
 * 		- R,C : ~ 100
 * 		- M : ~ R*C
 * 		- S : ~ 1,000
 * 
 * 	  - 접근 방식
 * 		- C만큼 반복(낚시왕 이동)
 * 		  1) 해당 열의 최 상단 상어 제거
 * 		  2) 상어 이동
 * 		  -> R*C 전체 탐색
 * 		  -> 상어 만나면 이동, 최대 R*2-2만큼 이동
 * 	
 * 	  - 최적화
 * 		* 시간  
 * 		  1) 속력(s) 그대로 한칸씩 이동 -> 2552ms
 * 		  2) 속력(s)이 R*2-2인 경우, 처음 방향을 그대로 유지하면서 제자리로 돌아온다. (열 이동인 경우, C*2-2)
 * 			 이러한 논리를 토대로, s%=(R*2-2)으로 이동 횟수를 줄임 -> 824ms
 * 
 * 		* 메모리
 * 		  1) 이동해야 하는 상어를 별도의 ArrayList에 저장한 후, 
 * 			  상어 한 마리씩 이동해서 기존 map에 적용 (상어를 clone까지 해야함) -> 80,704 KB
 * 		  2) 새로운 map에다가 상어를 이동신 후,
 * 			  새로운 map을 기존 map에다가 덮어쓰기 -> 35,680 KB
 * 			
 * 3. 후기
 * - 아기 상어랑 비슷해서 구현 자체는 어렵지 않았다. (다행)
 * - 시간이 좀 걸렸던 이유는, 속력을 좀 최적화하고 싶은 마음에 고집 좀 부렸다.
 * 	 -> 최적화를 안하고, 속력 그대로 이동해도 통과는 한다! 그러나 아슬아슬하게 한다. 잘못하면  시초난다. 
 * 
 * - 배열 map 메모리 아끼겠다고 상어 복사하고 별 짓을 다했는데, 
 *   오히려 새로운 map을 만드는게 메모리 절약되니까 약간 허무하면서 신기했다.
 * 	 -> 상어 복사하면서 객체 복사 어떻게 하는걸까 찾아보니까, 
 * 		Copyable 인터페이스의 copy 메서드를 구현해서 사용하드라! js는 쉽게 되는데 ^^;;
 */
public class Main_ja {
	public static int[] inputToIntArr(String str) {
		return Arrays.stream(str.split(" ")).mapToInt(Integer::parseInt).toArray();
	};

	static int[][] dt = { {}, { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
	static int[] reverseDir = { 0, 2, 1, 4, 3 };
	static int R, C;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] input = inputToIntArr(br.readLine());
		R = input[0];
		C = input[1];
		int M = input[2];

		Shark[][] map = new Shark[R][C];
		for (int i = 0; i < M; i++) {
			input = inputToIntArr(br.readLine());
			int r = input[0] - 1;
			int c = input[1] - 1;
			map[r][c] = new Shark(input[2], input[3], input[4]);
			// 제자리로 돌아오는 속력은 제외해서 저장
			map[r][c].size %= 2 * ((map[r][c].dir < 3 ? R : C) - 1);
		}
		
		int result = 0;
		for (int c = 0; c < C; c++) {
			result += fishing(map, c); // 낚시
			map = moveShark(map); // 상어 이동
		}
		System.out.println(result);
	}

	public static int fishing(Shark[][] map, int c) {
		for (int r = 0; r < R; r++) { // 해당 열에서 젤 위의 상어 제거
			Shark target = map[r][c];
			if (target == null)
				continue;
			map[r][c] = null;
			return target.size;
		}
		return 0;
	}

	public static Shark[][] moveShark(Shark[][] map) {
		Shark[][] newMap = new Shark[R][C]; // 상어가 이동할 새로운 맵
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				Shark target = map[i][j];
				if (target == null) continue;
				
				int r = i, c = j;
				int size = target.size;
				while (size-- > 0) { // 속력만큼 상어 이동
					int mr = r + dt[target.dir][0];
					int mc = c + dt[target.dir][1];
					if (mr < 0 || mc < 0 || mr >= R || mc >= C) { // 범위 넘어가면 방향 바꾸고 다시 이동
						target.dir = reverseDir[target.dir];
						size++;
						continue;
					}
					r = mr;
					c = mc;
				}
				if (newMap[r][c] != null && newMap[r][c].size > target.size) // 이동할 자리에 상어 있으면 크기 비교
					continue;
				newMap[r][c] = target;
			}
		}
		return newMap;
	}

	static class Shark {
		int speed;
		int dir;
		int size;

		public Shark(int speed, int dir, int size) {
			this.speed = speed;
			this.dir = dir;
			this.size = size;
		}
	}
}
