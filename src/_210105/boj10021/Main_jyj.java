package _210105.boj10021;

import java.util.Scanner;
public class Main_jyj{

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int n = sc.nextInt();

		int[] arr1 = new int[n];
		int[] arr2 = new int[n];
		char[][] map = new char[n][n];

		for (int i = 0; i < n; i++) {
			arr1[i] = sc.nextInt();

			String str = Integer.toBinaryString(arr1[i]);

			for (int j = 0; j < str.length(); j++) {

				if (str.charAt(str.length() - 1 - j) == '1') {
					map[i][n - 1 - j] = '#';
				}
				else {
					map[i][n - 1 - j] = ' ';
				}
			}
		}
		for (int i = 0; i < n; i++) {
			arr2[i] = sc.nextInt();
			String str = Integer.toBinaryString(arr2[i]);

			for (int j = 0; j < str.length(); j++) {

				if (str.charAt(str.length() - 1 - j) == '1') {
					map[i][n - 1 - j] = '#';
				}
                else if(str.charAt(str.length() - 1 - j) == '0' && map[i][n - 1 - j] != '#'){
                	map[i][n - 1 - j] = ' ';
                }
			}
		}

		String[] result = new String[n];

		for (int i = 0; i < n; i++) {
			String ans = "";
			for (int j = 0; j < n; j++) {
				ans += map[i][j];
			}
			result[i] = ans;
		}

		for (int i = 0; i < n; i++) {
			System.out.println(result[i]);
		}

	}

}
