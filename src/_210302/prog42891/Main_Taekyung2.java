package _210302.prog42891;

import java.util.*;

public class Main_Taekyung2 {
    public int solution(int[] food_times, long k) {
        ArrayList<int[]> foods = new ArrayList<>();
        int n = food_times.length;
        for(int i = 0; i < n; i++)
            foods.add(new int[]{food_times[i], i + 1});
        foods.sort(Comparator.comparingInt(f -> f[0]));
        int i = 0;
        for(; i < n; i++) {
            int d = (i == 0) ? foods.get(i)[0] : foods.get(i)[0] - foods.get(i - 1)[0];
            if(k < d * (n - i)) break;
            k -= d * (n - i);
        }
        if(i == n) return -1;
        foods.subList(i, n).sort(Comparator.comparingInt(f -> f[1]));
        return foods.get((int) (i + k % (n - i)))[1];
    }
}