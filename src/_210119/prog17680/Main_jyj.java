package _210119.prog17680;

import java.util.*;

public class Main_jyj {
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        
        LinkedList<String> list =new LinkedList<String>();
        
        // size가 0이면 5*길이를 리턴
        if(cacheSize == 0)
            return 5*cities.length;
        
        for(int i = 0; i< cities.length; i++){
            // 소문자로 통일
            String tmp = cities[i].toLowerCase();
            
            // 캐시에 값 있을 때
            if(list.contains(tmp)){
                // 캐시가 있으면 +1하고
                answer++; 
                // 있는 캐시를 지운다
                list.remove(tmp);
                // 마지막에 새로 넣어준다
                list.add(tmp);
            }
            // 캐시에 값이 없을 때
           // 1. 리스트 사이즈가 캐시 사이즈보다 커질때
            else if(list.size() >= cacheSize){
                answer+=5;
                list.add(tmp);
                list.poll();
            }
            else {
                
                list.add(tmp);
                answer+=5;
            }
            
        }
        return answer;
    }
}
