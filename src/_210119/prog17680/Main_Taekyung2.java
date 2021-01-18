package _210119.prog17680;

import java.util.LinkedHashSet;

public class Main_Taekyung2 {
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        final int HIT = 1, MISS = 5;

        LinkedHashSet<String> cache = new LinkedHashSet<>();
        for (String city : cities) {
            city = city.toUpperCase();
            if (cache.contains(city)) answer += HIT;
            else answer += MISS;
            cache.remove(city);
            cache.add(city);
            if (cache.size() > cacheSize)
                cache.remove(cache.iterator().next());
        }
        return answer;
    }
}