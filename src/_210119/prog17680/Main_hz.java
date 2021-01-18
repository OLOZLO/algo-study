package _210119.prog17680;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(solution(3, new String[] {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"}));
		System.out.println(solution(3, new String[] {"Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul"}));
		System.out.println(solution(2, new String[] {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "SanFrancisco", "Seoul", "Rome", "Paris", "Jeju", "NewYork", "Rome"}));
		System.out.println(solution(5, new String[] {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "SanFrancisco", "Seoul", "Rome", "Paris", "Jeju", "NewYork", "Rome"}));
		System.out.println(solution(2, new String[] {"Jeju", "Pangyo", "NewYork", "newyork"}));
		System.out.println(solution(0, new String[] {"Jeju", "Pangyo", "Seoul", "NewYork", "LA"}));
		System.out.println(solution(0, new String[] {"Jeju", "Pangyo", "Seoul", "la", "LA"}));
	}
	
	public static int solution(int cacheSize, String[] cities) {
        int answer = 0;
        
        LinkedHashMap<String, String> cache = new LinkedHashMap<>();
        for (int i = 0; i < cities.length; i++) {
        	// 캐시가 존재하면서 캐시에 해당 도시가 있을 때(소문자로 통일했습니당)
        	if (cacheSize > 0 && cache.containsKey(cities[i].toLowerCase())) {
        		answer += 1;
        		
        		cache.remove(cities[i].toLowerCase());	// 캐시에 꺼내서 
        		cache.put(cities[i].toLowerCase(), cities[i].toLowerCase());	// 가장 최근에 방문했다고 표시해줍니다
        	} 
        	// 캐시에 없을 때
        	else {
        		answer += 5;
        		
        		if (cache.size() < cacheSize) {	// 캐시에 지금 빈공간이 있으니까 그냥 넣어!
        			cache.put(cities[i].toLowerCase(), cities[i].toLowerCase());
        		} else {	// 빈공간이 없을때
        			for (Entry<String, String> e : cache.entrySet()) {	
        				cache.remove(e.getKey());	// 가장 앞에 있는것을 꺼내준다 (가장 오래된 것!)
        				break;	// 하나만 꺼낼꺼니까 끝내버림
        			}
        			cache.put(cities[i].toLowerCase(), cities[i].toLowerCase());	// 캐시에 넣어줍니다~
        		}
        	}
        }
        

        return answer;
    }

}
