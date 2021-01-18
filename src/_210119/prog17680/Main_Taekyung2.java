package _210119.prog17680;

import java.util.LinkedHashSet;

public class Main_Taekyung2 {
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        // HIT 실행시간 1초, MISS 실행시간 5초
        final int HIT = 1, MISS = 5;
        // 중복되지 않는 순서가 유지되는 컬렉션
        LinkedHashSet<String> cache = new LinkedHashSet<>();
        for (String city : cities) {
            city = city.toUpperCase();
            // HIT하면 + 1
            if (cache.contains(city)) answer += HIT;
            // MISS면 + 5
            else answer += MISS;
            cache.remove(city);
            cache.add(city);
            if (cache.size() > cacheSize)
                // 맨 앞에꺼 제일 오래 안쓴거 없앰
                cache.remove(cache.iterator().next());
        }
        return answer;
    }
}