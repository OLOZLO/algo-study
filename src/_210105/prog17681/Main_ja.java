package _210105.prog17681;

import java.util.Arrays;

// 비밀지도
public class Main_ja {
	public static void main(String[] args) {
		Main_ja test = new Main_ja();
		String[] r = test.solution(6, new int[]{46, 33, 33 ,22, 31, 50}, new int[]{27 ,56, 19, 14, 14, 10});
		System.out.println(Arrays.toString(r));
	}

	public String[] solution(int n, int[] arr1, int[] arr2) {
		String[] answer = new String[n];
		int[] pow = new int[n];
		pow[0] = 1;
		for(int i=1; i<n; i++)
			pow[i]= pow[i-1]*2;
		
		for (int i = 0; i < n; i++) {
			answer[i] = "";

			long binaryA =convert10to2(pow, arr1[i]);
			long binaryB =convert10to2(pow, arr2[i]);
			
			String binary = String.valueOf(binaryA+binaryB);
			for(int j=0; j<binary.length(); j++) {
				answer[i] += binary.charAt(j)-'0'>=1?"#":" ";
			}
			
			while(answer[i].length()!=n)
				answer[i]=" "+answer[i];
		}
		return answer;
	}

	public long convert10to2(int[] pow, int arr) {
		long result = 0;
		for(int i=pow.length-1; i>=0; i--) {
			if(arr>=pow[i]) {
				result+=Math.pow(10, i);
				arr-=pow[i];
			}
		}
		return result;
	}

}
