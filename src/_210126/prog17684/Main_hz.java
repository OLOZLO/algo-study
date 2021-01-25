package _210126.prog17684;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(solution("KAKAO")));
		System.out.println(Arrays.toString(solution("TOBEORNOTTOBEORTOBEORNOT")));
		System.out.println(Arrays.toString(solution("ABABABABABABABAB")));
	}
	
    public static int[] solution(String msg) {
    	int[] answer;
        List<Integer> result = new ArrayList<>();
        
        HashMap<String, Integer> dic = new HashMap<>();	// 이것은 사전
        for(int i = 0; i < 26; i++)	// 사전에  A-Z를 넣으며 초기화시켜줍니다.
        	dic.put(Character.toString('A'+i), i+1);
        
        
        int idx = 0;
        while(idx < msg.length()) {	// idx는 우리가 읽기 시작할 문자의 시작인덱스
        	int cnt = 0;	// cnt는 읽을 문자의 길이
        	// 길이를 1씩 더해보면서 해당 글자가 사전에 있나 확인합니다. (사전 없는 단어가 나올 때까지)
        	while(idx+cnt < msg.length() && dic.containsKey(msg.substring(idx, idx+cnt+1)))
        		cnt++;
        	
        	result.add(dic.get(msg.substring(idx, idx+cnt)));	// 있는 단어의 색인번호를 결과값에 저장하고
        	if (idx+cnt+1 < msg.length())	// 없는 단어를 사전에 넣는다
        		dic.put(msg.substring(idx, idx+cnt+1), dic.size()+1);
        	
        	idx += cnt;	// 
        }
        
        answer = new int[result.size()];
        for (int i = 0; i < result.size(); i++) 
        	answer[i] = result.get(i);
        
        return answer;
    }

}
