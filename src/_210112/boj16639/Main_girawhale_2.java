package _210112.boj16639;

import java.util.Arrays;
import java.util.Scanner;

public class Main_girawhale_2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        char[] in = sc.next().toCharArray();
        int[][] min = new int[N][N],
                max = new int[N][N];
        for (int i = 0; i < N; i += 2) {
            Arrays.fill(min[i], Integer.MAX_VALUE);
            Arrays.fill(max[i], Integer.MIN_VALUE);
            min[i][i] = max[i][i] = in[i] - '0';
        }

        for (int k = 2; k < N; k += 2) {
            for (int i = 0; i < N - k; i += 2) {
                for (int j = 0; j < k; j += 2) {
                    int[] ret = new int[4];
                    int m = i + j;

                    ret[0] = calc(max[i][m], max[m + 2][i + k], in[m + 1]);
                    ret[1] = calc(max[i][m], min[m + 2][i + k], in[m + 1]);
                    ret[2] = calc(min[i][m], max[m + 2][i + k], in[m + 1]);
                    ret[3] = calc(min[i][m], min[m + 2][i + k], in[m + 1]);

                    Arrays.sort(ret);
                    min[i][i + k] = Math.min(min[i][i + k], ret[0]);
                    max[i][i + k] = Math.max(max[i][i + k], ret[3]);
                }
            }
        }
        System.out.println(max[0][N - 1]);
    }

    static int calc(int n1, int n2, char op) {
        switch (op) {
            case '+':
                return n1 + n2;
            case '-':
                return n1 - n2;
            default:
                return n1 * n2;
        }
    }
}
