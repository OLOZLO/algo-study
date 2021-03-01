package _210302.prog42891;

import java.util.Arrays;

public class Main_1n9yun {
    public int solution(int[] food_times, long k) {
//        섭취 시간 오름차순 정렬해서 하나씩 제거하는 방법
        int[] sorted_food_times = food_times.clone();
        Arrays.sort(sorted_food_times);

        int prev = 0;
//        제거 대상이 한 번에 이 길이 만큼 k를 줄일 것이다. (한 줄을 순회해야 1이 줄어드니까)
        int length = sorted_food_times.length;
        for (int sorted_food_time : sorted_food_times) {
//            이전 섭취 시간과의 차이 (이전 음식을 섭취함으로써 그 시간만큼 다음 음식도 시간이 줄어있을테니 그것을 고려한 것)
            long remain = sorted_food_time - prev;

            if (k >= remain * length) {
                k -= remain * length;
                prev += remain;
                length--;
            } else {
//                여기로 오다는 것은 이 음식의 남은 섭취 시간 만큼 섭취할 수 없다.
//                따라서 가능한 만큼 섭취한 뒤 음식 제거 종료
                k %= length;
                break;
            }
        }
//        남은 시간 k동안 먹어주기
        for(int i=0;i<food_times.length;i++){
            if(food_times[i] <= prev) {
                continue;
            }
            if(--k < 0) return i+1;
        }
        return -1;
    }
}
