package stack_queue.prog42586;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(solution(new int[] {93, 30, 55}, new int[] {1, 30, 5})));
		System.out.println(Arrays.toString(solution(new int[] {95, 90, 99, 99, 80, 99}, new int[] {1, 1, 1, 1, 1, 1})));
	}
	
	 public static int[] solution(int[] progresses, int[] speeds) {
        int[] answer;
        
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < progresses.length; i++) {
        	if ((100-progresses[i])%speeds[i] == 0) 
        		q.add((100-progresses[i])/speeds[i]);
        	else
        		q.add((100-progresses[i])/speeds[i]+1);
        }
        
        ArrayList<Integer> release = new ArrayList<>();
       
        int base = q.poll();
        int cnt = 1;
        while(!q.isEmpty()) {
        	int now = q.poll();
        	
        	if (base < now) {
        		release.add(cnt);
        		base = now;
        		cnt = 1;
        	} else {
        		cnt++;
        	}
        }
        release.add(cnt);
        
        answer = new int[release.size()];
        int idx = 0;
        for (int r : release)
        	answer[idx++] = r;
        
        return answer;
    }
}
