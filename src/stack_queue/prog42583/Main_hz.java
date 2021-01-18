package stack_queue.prog42583;

import java.util.LinkedList;
import java.util.Queue;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(solution(2, 10, new int[] {7, 4, 5, 6}));
		System.out.println(solution(100, 100, new int[] {10}));
		System.out.println(solution(100, 100, new int[] {10,10,10,10,10,10,10,10,10,10}));
	}
	
	public static class Truck {
		int w, left;
		
		Truck(int w, int left) {
			this.w = w;
			this.left = left;
		}
	}
	
	public static int solution(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;
        
        Queue<Truck> bridge = new LinkedList<>();
        int idx = 0;
        int bweight = 0;
        
        while (true) {
        	if (idx >= truck_weights.length && bridge.isEmpty())
        		break;

        	answer++;
        	for (Truck t : bridge) {
        		t.left--;
        	}
        	
        	if (!bridge.isEmpty() && bridge.peek().left == 0) {
        		bweight -= bridge.poll().w;
        	}
        	
        	if (idx < truck_weights.length &&  bweight + truck_weights[idx] <= weight) {
        		bridge.add(new Truck(truck_weights[idx], bridge_length));
        		bweight += truck_weights[idx];
        		idx++;
        	}
        }
        
        return answer;
    }

}
