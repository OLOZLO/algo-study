package _210216.boj2931;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_jyj {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int r = sc.nextInt();
		int c = sc.nextInt();

		char[][] map = new char[r][c];
		boolean[][] visit = new boolean[r][c];

		Queue<Point> q = new LinkedList<Point>();

		for (int i = 0; i < r; i++) {
			String str = sc.next();
			for (int j = 0; j < c; j++) {
				map[i][j] = str.charAt(j);
			}
		}

	}

	public static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
