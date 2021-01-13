package _210119.prog17680;

import java.util.LinkedHashSet;

public class Main_girawhale {
    public int solution(int cacheSize, String[] cities) {
        int ans = 0;
        LinkedHashSet<String> cache = new LinkedHashSet<>(); // 입력 순서대로 정렬을 가지고 있는 LinkedHashSet으로 Cache 생성

        for (String city : cities) { // for문을 돌며 city가 cache에 있는지 확인
            city = city.toLowerCase();
            ans += cache.contains(city) ? 1 : 5; // cache에 city가 있다면? 1, 아니면? 5를 반환값에 더함

            cache.remove(city); // cache에 city가 있다면 삭제, 없으면 무시
            cache.add(city); // cache에 찾은 city 추가

            if (cache.size() > cacheSize) //만약 cache에 저장된 개수가 cacheSize보다 크다면?
                cache.remove(cache.iterator().next()); // cache에서 가장 오래된 값을 제거한다
        }

        return ans;
    }
}
