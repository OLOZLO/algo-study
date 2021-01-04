package _210105.prog17682;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(solution("1S2D*3T"));
		System.out.println(solution("1D2S#10S"));
		System.out.println(solution("1D2S0T"));
		System.out.println(solution("1S*2T*3S"));
		System.out.println(solution("1D#2S*3S"));
		System.out.println(solution("1T2D3D#"));
		System.out.println(solution("1D2S3T*"));
	}
	
	static public int solution(String dartResult) {
        int answer = 0;
        int[] score = new int[3];
        int idx = 0;
        
        for (int i = 0; i < dartResult.length(); i++) {
        	char c = dartResult.charAt(i);
        	
        	if (c == 'S') {
        		continue;
        	} else if (c == 'D') {
        		score[idx-1] = (int) Math.pow(score[idx-1], 2);
        	} else if (c == 'T') {
        		score[idx-1] = (int) Math.pow(score[idx-1], 3);
        	} else if (c == '*') {
        		score[idx-1] *= 2;
        		
        		if (idx != 1) 
        			score[idx-2] *= 2;
        	} else if (c == '#') {
        		score[idx-1] *= -1;
        	} else {
        		if (dartResult.charAt(i+1) == '0') {
        			score[idx] = 10;
        			i++;
        		} else {
        			score[idx] = c - '0';
        		}
        		idx++;
        	}
        }
        
        for (int i = 0; i < 3; i++) 
        	answer += score[i];
        
        return answer;
    }

}
