package _210302.prog42891;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main_girawhale {
    public int solution(int[] food_times, long k) {
        List<Food> foods = IntStream.range(0, food_times.length)
                .mapToObj(i -> new Food(i + 1, food_times[i]))
                .sorted(Comparator.comparing(f -> f.time)).collect(Collectors.toList()); // food의 time으로 정렬한 list만들기

        long prev = 0;
        int idx = 0;
        while (idx < foods.size()) {
            long min = foods.get(idx).time - prev; // 내 이전꺼보다 더 먹은만큼 해서 쭉쭉 빼줌

            if (min * (foods.size() - idx) > k) break; // 잔여횟수보다 먹을양 많으면 나오기
            k -= min * (foods.size() - idx);

            prev += min;
            idx++;
        }

        long finalPrev = prev; // prev가 반복문에서 값바뀐다고 stream에서 못쓴다길래 한번 만들어줌..
        foods = foods.stream().filter(f -> f.time > finalPrev) // 그냥 필터를 써보고 싶었숨미다... 이거 이상인 애들만 거르기
                .sorted(Comparator.comparingInt(f -> f.idx)).collect(Collectors.toList());
        return foods.size() == 0 ? -1 : foods.get((int) (k % foods.size())).idx; // foods size가 0이면 다먹은거 -1. 아니면 계산해서 반환
    }

    class Food {
        int idx, time;

        Food(int idx, int time) {
            this.idx = idx;
            this.time = time;
        }
    }
}