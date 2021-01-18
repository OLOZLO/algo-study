package _210119.prog17680;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class Main_1n9yun {
    public static void main(String[] args) {
        int cacheSize = 3;
        String[] cities = {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"};
//        String[] cities = {"Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul"};
        System.out.println(solution(cacheSize, cities));
    }
    public static int solution(int cacheSize, String[] cities) {
//        cacheSize가 0이면 전부다 miss니까 걍 5곱해서 리턴
        if(cacheSize == 0) return cities.length * 5;

//        링크드 해쉬셋!!!!!!!!!!!!!!!!!
//        LRU는 Double-linked list, HashMap으로 구현한다!
        LinkedHashSet<String> cache = new LinkedHashSet<>();

        int answer = 0;
        for(int i=0;i<cities.length;i++){
//            대소문자 구분 안한다고 했으니 전부다 소문자로 바꿔버리자
            String city = cities[i].toLowerCase();

//            hit다 hit
            if(cache.contains(city)){
                answer++;

                cache.remove(city);

            }
//            miss다 miss
            else{
                answer += 5;

//                넘쳐흘러 지워
                if(cache.size() >= cacheSize) {
                    Iterator<String> it = cache.iterator();
                    it.next();
                    it.remove();
                }
            }
            cache.add(city);
        }
        return answer;
    }
}
