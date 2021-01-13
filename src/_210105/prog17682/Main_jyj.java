package _210105.prog17682;

public class Main_jyj {
    public int solution(String dartResult) {
        int[] numArr = new int[3];
        int answer = 0;
        int cnt = 0;
        int temp = 0;
        boolean[] starCheck = new boolean[3];
        
			for (int j = 0; j < dartResult.length(); j++) {
				int num = dartResult.charAt(j) - '0';
				if (num >= 0 && num <= 9) {
					if (num == 1 && dartResult.charAt(j + 1) - '0' == 0) {
						temp = 10;
						j++;
					} else {
						temp = num;
					}
				} else if (dartResult.charAt(j) == 'S') {
					double tempNum = Math.pow(temp, 1);
					numArr[cnt] = (int) tempNum;
					cnt++;
				} else if (dartResult.charAt(j) == 'D') {
					double tempNum = Math.pow(temp, 2);
					numArr[cnt] = (int) tempNum;
					cnt++;
				} else if (dartResult.charAt(j) == 'T') {
					double tempNum = Math.pow(temp, 3);
					numArr[cnt] = (int) tempNum;
					cnt++;
				} else if (dartResult.charAt(j) == '*') {

					if (cnt == 1) {
						numArr[0] *= 2;
					} else if (cnt == 2) {
						for (int k = 0; k < 2; k++) {
							numArr[k] *= 2;
						}
					} else if (cnt == 3) {
						for (int k = 1; k < 3; k++) {
							numArr[k] *= 2;
						}
					}
				} else if (dartResult.charAt(j) == '#') {
					numArr[cnt - 1] *= -1;
				}
			}

			for (int j = 0; j < numArr.length; j++) {
				answer += numArr[j];

			}
        
        
        return answer;
    }
}
