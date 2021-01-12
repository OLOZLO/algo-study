package _210112.prog17677;

import java.util.ArrayList;
import java.util.List;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(solution("FRANCE", "french"));
		System.out.println(solution("handshake", "shake hands"));
		System.out.println(solution("aa1+aa2", "AAAA12"));
		System.out.println(solution("E=M*C^2", "e=m*c^2"));
	}
	
	public static int solution(String str1, String str2) {
        int answer = 0;
        
        List<String> list1 = makeSet(str1);
        List<String> list2 = makeSet(str2);
        
        double eq = 0;
        for(int i = 0; i < list1.size(); i++) {
        	String s = list1.get(i);
        	
        	for (int j = 0; j < list2.size(); j++) {
        		if (list2.get(j).equals(s)) {
    				eq += 1;
    				list2.remove(j);
    				break;
        		}
        	}
        }
        
        if (eq == 0 && (list1.size()+list2.size()) == 0)
        	answer = 65536;
        else
        	answer = (int)((eq/(list1.size()+list2.size()))*65536);
        
        return answer;
    }
	
	public static List<String> makeSet(String str) {
		List<String> list = new ArrayList<>();
		
		for (int i = 0; i < str.length()-1; i++) {
			String s = str.substring(i, i+2).toLowerCase();
			
			if (Character.isLetter(s.charAt(0)) && Character.isLetter(s.charAt(1))) {
				list.add(s);
			}
		}
		
		return list;
	}

}
