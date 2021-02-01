package _210202.prog17676;

import java.util.ArrayList;
import java.util.List;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(solution(new String[] {"2016-09-15 01:00:04.001 2.0s", "2016-09-15 01:00:07.000 2s"}));
		System.out.println(solution(new String[] {"2016-09-15 01:00:04.002 2.0s", "2016-09-15 01:00:07.000 2s"}));
		System.out.println(solution(new String[] {"2016-09-15 20:59:57.421 0.351s", "2016-09-15 20:59:58.233 1.181s", "2016-09-15 20:59:58.299 0.8s", "2016-09-15 20:59:58.688 1.041s", "2016-09-15 20:59:59.591 1.412s", "2016-09-15 21:00:00.464 1.466s", "2016-09-15 21:00:00.741 1.581s", "2016-09-15 21:00:00.748 2.31s", "2016-09-15 21:00:00.966 0.381s", "2016-09-15 21:00:02.066 2.62s"}));
	}
	
	public static class Log {
		int st, et;
		
		Log(String s) {
			String[] in = s.split(" ");	// 입력받은 문자열을 공백 단위로 쪼개요
			
			int et = 0;	// spl[1]인 시간정보를(끝나는 시간) 밀리초로 바꿀껍니다
			String[] time = in[1].split(":");	// 시:분:초.밀리 를 :로 스플릿하구
			et += Integer.parseInt(time[0])*60*60*1000;	// 시를 밀리초로
			et += Integer.parseInt(time[1])*60*1000;	// 분을 밀리초로
			et += Integer.parseInt(time[2].split("\\.")[0])*1000;	// 초를 밀리초로
			et += Integer.parseInt(time[2].split("\\.")[1]);
			this.et = et;
			
			String[] sec = in[2].substring(0, in[2].length()-1).split("\\.");	// 이거는 시작 시간 구하꺼에여 우선 맨 뒤에 s 빼주고
			this.st = et-(Integer.parseInt(sec[0])*1000);
			if (sec.length > 1)
				this.st -= Integer.parseInt(sec[1]);
			
			this.st += 1;
		}
	}
	
	public static int solution(String[] lines) {
        int answer = 0;
        
        List<Log> logs = new ArrayList<>();
        for (String line : lines) 
        	logs.add(new Log(line));	// 입력값을 Log의 리스트로 만들면서 시작 시간을 구해줍니다.
        
        for (int i = 0; i < logs.size(); i++) {
        	int time = logs.get(i).et;	
        	int cnt = 1;
        	
        	// 끝나는 시간을 기준으로 끝나는 시간 ~ 끝나는시간+1초 사이에 해당하는 값들이 몇개나 있나 구할껍니다. 
        	for (int j = i+1; j < logs.size(); j++) {	// 어차피 끝나는 시간 오름차순으로 정렬되있으니까 뒤에만 볼꺼
        		if (logs.get(j).st <= time+1000-1) {
        			cnt++;
        		} 
//        		else	break;
        		// 원래 시간 줄이려구 이 부분 넣었는데... 반례 존재해서 걍 무식하게 N^2 돌렸슴다
        	}
        	
        	answer = Math.max(answer, cnt);
        }
        
        return answer;
    }

}
