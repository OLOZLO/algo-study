package _210112.boj16639;

import java.util.ArrayList;
import java.util.Scanner;

public class Main_girawhale {
    static int ans = Integer.MIN_VALUE, opSize;
    static int[] perm;
    static boolean[] visit;
    static ArrayList<Integer> nums;
    static ArrayList<Character> ops;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        String input = sc.next();
        nums = new ArrayList<>();
        ops = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (i % 2 == 0) nums.add(input.charAt(i) - '0');
            else ops.add(input.charAt(i));
        }
        opSize = ops.size();

        perm = new int[opSize];
        visit = new boolean[opSize];

        permutation(0);
        System.out.println(ans);
    }

    static void permutation(int idx) {
        if (idx >= opSize) {
            solve();
            return;
        }

        for (int i = 0; i < opSize; i++) {
            if (!visit[i]) {
                visit[i] = true;
                perm[idx] = i;
                permutation(idx + 1);
                visit[i] = false;
            }
        }
    }

    static void solve() {
        ArrayList<Integer> numList = new ArrayList<>(nums);
        ArrayList<Character> opList = new ArrayList<>(ops);
        int[] diff = new int[opSize];

        for (int i = 0; i < opSize; i++) {
            int idx = perm[i] - diff[perm[i]];
            numList.add(idx, calc(numList.remove(idx), numList.remove(idx), opList.remove(idx)));

            for (int j = perm[i]; j < opSize; j++)
                diff[j]++;
        }

        ans = Math.max(ans, numList.get(0));
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
