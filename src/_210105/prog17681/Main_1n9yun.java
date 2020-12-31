package _210105.prog17681;

import java.util.Arrays;

public class Main_1n9yun {
    public static void main(String[] args) {
        int n = 5;
        int[] arr1 = {9, 20, 28, 18, 11};
        int[] arr2 = {30, 1, 21, 17, 28};

        System.out.println(Arrays.toString(solution(n, arr1, arr2)));
    }
    static String[] solution(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];

        for(int i=0;i<n;i++) arr1[i] |= (arr2[i] | 1<<n);
        for(int i=0;i<n;i++) answer[i] = Integer.toBinaryString(arr1[i])
                .replaceAll("1", "#")
                .replaceAll("0", " ")
                .substring(1);

        return answer;
    }
}
