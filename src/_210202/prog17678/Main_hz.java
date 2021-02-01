package _210202.prog17678;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(solution(1, 1, 5, new String[] {"08:00", "08:01", "08:02", "08:03"}));
		System.out.println(solution(2, 10, 2, new String[] {"09:10", "09:09", "08:00"}));
		System.out.println(solution(2, 1, 2, new String[] {"09:00", "09:00", "09:00", "09:00"}));
		System.out.println(solution(1, 1, 5, new String[] {"00:01", "00:01", "00:01", "00:01", "00:01"}));
		System.out.println(solution(1, 1, 1, new String[] {"23:59"}));
		System.out.println(solution(10, 60, 45, new String[] {"23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59"}));
	}
	
	public static class Time {
		int hour, min, toMin;
		
		Time(String in) {
			String[] t = in.split(":");
			this.hour = Integer.parseInt(t[0]);
			this.min = Integer.parseInt(t[1]);
			this.toMin = this.hour*60 + this.min;
		}
	}
	
	public static String solution(int n, int t, int m, String[] timetable) {
		String answer = "";
		
		List<Time> ttb = new ArrayList<>();
		// 입력받은 timetable을 ttb 리스트로 다시 저장을 해줍니다. 이유는... 내가 편하려고...ㅎ
		for (String time : timetable)
			ttb.add(new Time(time));
		
		Collections.sort(ttb, new Comparator<Time>() {	// 그래서 온 순서대로 정렬을 해줍니다

			@Override
			public int compare(Time o1, Time o2) {
				return o1.toMin-o2.toMin;
			}
		});
		
		for (int bus = 0; bus < n ; bus++) {
			int startTime = 540 + bus*t;	// 각 버스의 출발 시간 계산
			
			if (bus != n-1) {	// 마지막 버스가 아닐때는 왔을 때 태울수 있는 크루들을 태워버려(지운다!)
				int cnt = m;
				
				while (cnt > 0) {	// m만큼만 태울 수 있는데
					if (ttb.get(0).toMin <= startTime) {	// 그중에서도 버스가 올때까지 도착한 사람만 태움
						cnt--;
						ttb.remove(0);
					} else {
						break;
					}
				}
			} else {	// 마지막 버스야! 꼭 이거 타야돼!
				int ans;
				
				if (ttb.size() < m) {	// 탈 수 있는것보다 크루들이 안왔으면 버스 출발시간에 오면 됨!
					ans = startTime;
				} else {	// 그렇지 않을 경우
					int cnt = m;
					Stack<Time> st = new Stack<>();
					while(cnt > 0) {	// 우선 태울 수 있는 만큼 다 태워가지고 스택에 잠깐 넣어둡니다
						if (ttb.get(0).toMin <= startTime) {	// 물론 제 시간에 도착한 사람만!
							st.add(ttb.remove(0));
							cnt--;
						} else {
							break;
						}
					}
					
					if (st.size() < m) {	// 태울 수 있는 사람 다 태웠는데 자리가 남았다?
						ans = startTime;	// 그럼 차 출발 할 떄 도착해도 됨!
					} else {	// 태울 수 있는 사람 다 태워서 내가 못탄다?
						ans = st.pop().toMin-1;	// 가장 늦게온 사람 언제왔는지 알아내서 걔보다 1분 일찍 오자!
					}
				}
				
				// 제가 시:분을 다 분단위로 바꿔놔서 정답을 다시 시:분 단위로 고치는 과정임다...
				if (ans/60 < 10) answer += "0";
				answer += ans/60;
				answer += ":";
				if (ans%60 < 10) answer += "0";
				answer += ans%60;
			}
		}
		
		return answer;
	}

}
