package _210302.prog42891;

import java.util.*;

public class Main_Taekyung2 {
    public int solution(int[] food_times, long k) {
        // foods -> 먹는데 걸리는 시간, 인덱스
        ArrayList<int[]> foods = new ArrayList<>();
        int n = food_times.length;
        for(int i = 0; i < n; i++)
            foods.add(new int[]{food_times[i], i + 1});
        // 먹는데 걸리는 시간 오름차순으로 정렬
        foods.sort(Comparator.comparingInt(f -> f[0]));
        int i = 0;
        for(; i < n; i++) {
            int d = (i == 0) ? foods.get(i)[0] : foods.get(i)[0] - foods.get(i - 1)[0];
            // d * (n - i) -> 먹는데 걸리는 시간 작은 거부터 하나씩 지움, 제일 작은거 만큼 전체에서 뺀다
            if(k < (long) d * (n - i)) break;
            k -= (long) d * (n - i);
        }
        // 전부 다먹었으면 -1
        if(i == n) return -1;
        // i - 1위치까지만 다 먹었으니까, 다음 꺼부터는 인덱스로 정렬 시킴
        foods.subList(i, n).sort(Comparator.comparingInt(f -> f[1]));
        // 남은 k초만큼 1초씩 돌아가면서 확인
        return foods.get((int) (i + k % (n - i)))[1];
    }
}