package _210119.prog17680;

import java.util.LinkedHashSet;

public class Main_girawhale {
    public int solution(int cacheSize, String[] cities) {
        int ans = 0;
        LinkedHashSet<String> cache = new LinkedHashSet<>();

        for (String city : cities) {
            city = city.toLowerCase();
            ans += cache.contains(city) ? 1 : 5;

            cache.remove(city);
            cache.add(city);

            if (cache.size() > cacheSize)
                cache.remove(cache.iterator().next());
        }

        return ans;
    }
}
