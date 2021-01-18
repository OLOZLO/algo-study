package _210119.prog17680;

import java.util.LinkedList;

// https://ddb8036631.github.io/알고리즘%20풀이/프로그래머스_카카오2018_캐시/
public class Main_ms {
    public static int solution(int cacheSize, String[] cities) {
        int answer = 0;

        LinkedList<String> cache = new LinkedList<>();

        // 캐시를 활용할 수 없다면?
        // 모든 검색에서 cache miss가 발생할 것이므로, cache miss 실행 시간 5에 도시 개수를 곱해 리턴한다.
        if (cacheSize == 0) {
            return cities.length * 5;
        }

        // 캐시를 활용할 수 있다면?
        // 주어진 도시 배열을 순회하며 hit, miss에 따라 캐시 내부를 변경하는 아래와 같은 과정을 거친다.
        for (String city : cities) {

            // cache hit가 발생한다면?
            // cache hit 실행 시간 1을 더해주고, 해당 도시를 지운다. -> 조건 분기가 끝나면 맨 뒤에 갖다 붙일 예정!
            if (cache.contains(city.toUpperCase())) {
                answer += 1;
                cache.remove(cache.indexOf(city.toUpperCase()));

            }

            // cache miss가 발생한다면?
            // cache miss 실행 시간 5를 더해주고, 캐시가 꽉찼다면 가장 오래 참조하지 않은 0번 인덱스에 해당하는 도시를 지운다.
            else {
                answer += 5;

                if (cache.size() == cacheSize)
                    cache.remove(0);
            }

            // 현재 도시를 캐시에서 "가장 최근에 참조했다는 의미"를 갖는 맨 뒤 인덱스에 붙인다.
            cache.addLast(city.toUpperCase());
        }

        return answer;
    }

    public static void main(String[] args) {
        int cacheSize = 3;
        String[] cities = {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"};

//        int cacheSize = 3;
//        String[] cities = {"Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul"};

//        int cacheSize = 2;
//        String[] cities = {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "SanFrancisco", "Seoul", "Rome", "Paris", "Jeju", "NewYork", "Rome"};

//        int cacheSize = 5;
//        String[] cities = {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "SanFrancisco", "Seoul", "Rome", "Paris", "Jeju", "NewYork", "Rome"};

//        int cacheSize = 2;
//        String[] cities = {"Jeju", "Pangyo", "NewYork", "newyork"};

//        int cacheSize = 0;
//        String[] cities = {"Jeju", "Pangyo", "Seoul", "NewYork", "LA"};

        solution(cacheSize, cities);
    }
}
