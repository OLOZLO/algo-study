package _210105.prog17681;

import java.util.Arrays;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(solution(5, new int[] {9, 20, 28, 18, 11}, new int[] {30, 1, 21, 17, 28})));
		System.out.println(Arrays.toString(solution(6, new int[] {46, 33, 33 ,22, 31, 50}, new int[] {27 ,56, 19, 14, 14, 10})));
	}
	
	static public String[] solution(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];
        
        for (int i = 0; i < n; i++) {
        	String bin1 = Integer.toBinaryString(arr1[i]);
        	String bin2 = Integer.toBinaryString(arr2[i]);
        	
        	while(bin1.length() < n) 
        		bin1 = "0" + bin1;
        	while(bin2.length() < n)
        		bin2 = "0" + bin2;

        	String result = "";
        	for (int j = 0; j < n; j++) {
        		if (bin1.charAt(j) == '0' && bin2.charAt(j) == '0') {
        			result += " ";
        		} else {
        			result += "#";
        		}
        	}
        	
        	answer[i] = result;
        	
        }
        return answer;
    }

}
