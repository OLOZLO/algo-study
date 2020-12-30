package _210105.prog17681;

public class Main_sh {
    class Solution {
        public String[] solution(int n, int[] arr1, int[] arr2) {
            String[] ans = new String[n];

            for (int i = 0; i < n; i++)
                ans[i] = String.format("%" + n + "s", Integer.toBinaryString(arr1[i] | arr2[i])).replace('1', '#').replace('0', ' ');

            return ans;
        }
    }
}