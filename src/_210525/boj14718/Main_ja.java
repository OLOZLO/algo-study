package _210525.boj14718;

import java.util.ArrayList;
import java.util.Scanner;

public class Main_ja {
	/**
	 * [골드3] 용감한 용사 진수
	 * 1. 결과 : 맞았습니다. -> 시간 내에 못 품(40분 걸림)
	 * 2. 시간복잡도 : O(N^4)
	 * 3. 풀이
	 * 	(1) 세 개의 스탯을 기준으로 하나씩 선택해서 모든 경우를 탐색
	 *	(2) 해당 스택으로 이길 수 있는 적의 수 체크
	 * 		-> 이긴 적의 수가 K인 경우, 세 개 스택의 합의 최솟값을 저장
	 */
	ArrayList<Enemy> enemies = new ArrayList<>();
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int K = in.nextInt();
		ArrayList<Enemy> enemies = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			Enemy enemy = new Enemy(in.nextInt(), in.nextInt(), in.nextInt());
			enemies.add(enemy);
		}
		
		int result = Integer.MAX_VALUE;
		for (int p = 0; p < N; p++) {
			int power = enemies.get(p).power;
			for (int a = 0; a < N; a++) {
				int agility = enemies.get(a).agility;
				for (int i = 0; i < N; i++) {
					int intelligence = enemies.get(i).intelligence;
					
					int win = 0;
					for (int idx = 0; idx < N; idx++) {
						Enemy enemie = enemies.get(idx);
						if(enemie.power <= power && enemie.agility <= agility && enemie.intelligence <= intelligence) win++;
						if(win == K) {
							result = Math.min(result, power + agility + intelligence);
							break;
						}
					}
				}
			}
		}
		System.out.println(result);
		
	}

	static class Enemy{
		int power, agility, intelligence;

		public Enemy(int power, int agility, int intelligence) {
			this.power = power;
			this.agility = agility;
			this.intelligence = intelligence;
		}
		
	}
}
