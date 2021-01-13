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
        
        double eq = 0;	// 교집합 수
        for(int i = 0; i < list1.size(); i++) {
        	String s = list1.get(i);
        	
        	for (int j = 0; j < list2.size(); j++) {	// 두 집합의 원소 비교
        		if (list2.get(j).equals(s)) {	// 두 집합의 공통 원소가 있을 경우 교집합수+=1, 두번째 집합에서만 삭제
    				eq += 1;
    				list2.remove(j);
    				break;
        		}
        	}
        }
        
        if (eq == 0 && (list1.size()+list2.size()) == 0)	// 공통 요소가 없으면서 둘 다 공집합일 때
        	answer = 65536;
        else
        	answer = (int)((eq/(list1.size()+list2.size()))*65536);	// 자카드 유사도 (=교집합크기/합집합크기) * 65536
        
        return answer;
    }
	
	// 입력받은 문자열에서 두 글자씩 끊어서 다중집합을 만드는 함수
	public static List<String> makeSet(String str) {
		List<String> list = new ArrayList<>();
		
		for (int i = 0; i < str.length()-1; i++) {	// 두 글자씩 끊어서 대소문자 차이는 무시한다 해서 소문자로 통일
			String s = str.substring(i, i+2).toLowerCase();
			
			if (Character.isLetter(s.charAt(0)) && Character.isLetter(s.charAt(1))) {	// 두 글자 모두 문자인 경우 리스트에 추가
				list.add(s);
			}
		}
		
		return list;
	}

}
