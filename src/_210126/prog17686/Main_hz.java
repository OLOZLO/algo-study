package _210126.prog17686;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(solution(new String[] {"img12.png", "img10.png", "img02.png", "img1.png", "IMG01.GIF", "img2.JPG"})));
		System.out.println(Arrays.toString(solution(new String[] {"F-5 Freedom Fighter", "B-50 Superfortress", "A-10 Thunderbolt II", "F-14 Tomcat"})));
	}
	
	public static class Fname {
		String origin, head, number;
		
		Fname(String origin) {
			this.origin = origin;
			
			int si = 0, ei = 0;
			boolean find = false;
			for (int i = 0; i < origin.length(); i++) {	// 파일명에서 number에 해당되는 부분을 찾기위한 반복문
				if (!find && Character.isDigit(origin.charAt(i))) {	// 처음 숫자가 나왔을 때
					si = i;	// number의 시작 인덱스 si에 저장
					find = true;	// 숫자가 적어도 한번 나왔음을 표시 
				}
				if (find && !Character.isDigit(origin.charAt(i))) {	// 숫자가 나온 후 숫자가 아닌 문자가 나왔을 때. 즉 tail 부분이 시작될 때
					ei = i;	// tail의 시작 인덱스 ei에 저장
					break;	// 뒤는 더 안봐도 됨!
				}
			}
			
			if (ei == 0)	ei = origin.length();	// tail이 없는 경우가 있으므로 그럴 경우 ei는 파일명의 길이로 설정
			this.head = origin.substring(0, si);
			this.number = origin.substring(si, ei);
		}
	}
	
	public static String[] solution(String[] files) {
        String[] answer;
        
        List<Fname> flist = new ArrayList<>();
        // 파일명들을 Fname의 리스트에 저장을 하면서
        // 각 Fname 생성 시 원본 파일명(origin)과 파일명의 head, number, tail이 분리됨
        for (int i = 0; i < files.length; i++) {
        	flist.add(new Fname(files[i]));
        }
        
        // compare 함수의 정렬 조건에 따라 파일명 정렬
        Collections.sort(flist, new Comparator<Fname>() {

			@Override
			public int compare(Fname o1, Fname o2) {
				// 모두 소문자로 변환 후 비교할거임!
				if (o1.head.toLowerCase().compareTo(o2.head.toLowerCase()) == 0) {
					// 기준 3. head와 number이 같을 경우 입력 순대로 정렬
					if (Integer.parseInt(o1.number) == Integer.parseInt(o2.number)) {
						return 0;
					} else {	// 기준 2. number를 숫자의 오름차순으로 정렬
						return Integer.parseInt(o1.number)-Integer.parseInt(o2.number);
					}
				} else {	// 기준 1. head를 문자의 오름차순으로 정렬 
					return o1.head.toLowerCase().compareTo(o2.head.toLowerCase());
				}
			}
		});
        
        
        answer = new String[flist.size()];
        for (int i = 0; i < flist.size(); i++)	// 정렬된 파일명 리스트들을 정답 배열에 저장
        	answer[i] = flist.get(i).origin;
        	
        return answer;
    }
}
