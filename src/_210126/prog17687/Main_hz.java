package _210126.prog17687;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(solution(2, 4, 2, 1));
		System.out.println(solution(16, 16, 2, 1));
		System.out.println(solution(16, 16, 2, 2));
	}
	
	public static String solution(int n, int t, int m, int p) {
        String answer = "";
        
        int number = 0;	// 이것은 n진법으로 바꿀 숫자
        int turn = 1;	// 이것은 순서 
        while (answer.length() < t) {	// t개의 숫자를 미리 구할때까지 (사실 그 이상 구해지기도 함...ㅎ)
        	String ton = "";	// number을 n진수로 변환시킨 값입니다. 곧 구할 예졍...
        	
        	int num = number;
        	// n진수로 변환시키는 과정은 숫자를 계속 n으로 나누면서 나머지를 저장하고 최종 몫까지 저장해서 그걸 역순으로 읽으면 됩니다!
        	while(num >= n) {	// 더이상 N으로 나눌 수 없을 때까지 계속 나머지를 저장하는 과정
        		int mod = num % n;	// 나머지를 구합니다
        		
        		if (mod < 10) {	// 나머지가 10보다 작으면 그냥 저장하고
        			ton += mod;
        		} else if (mod == 10) {	// 10보다 클 경우 각각에 해당하는 알파벳을 저장합니다.
        			ton += 'A';
        		} else if (mod == 11) {
        			ton += 'B';
        		} else if (mod == 12) {
        			ton += 'C';
        		} else if (mod == 13) {
        			ton += 'D';
        		} else if (mod == 14) {
        			ton += 'E';
        		} else if (mod == 15) {
        			ton += 'F';
        		}
        		num/=n;	// N으로 나누기
        	}
        	
        	// 마지막 몫도 저장해야되기 때문에 10보다 작으면 숫자 그대로, 10 이상이면 해당 알파벳으로 저장해줍니다.
        	if (num < 10) {	
    			ton += num;
    		} else if (num == 10) {
    			ton += 'A';
    		} else if (num == 11) {
    			ton += 'B';
    		} else if (num == 12) {
    			ton += 'C';
    		} else if (num == 13) {
    			ton += 'D';
    		} else if (num == 14) {
    			ton += 'E';
    		} else if (num == 15) {
    			ton += 'F';
    		}
        	
        	for (int i = ton.length()-1; i >= 0; i--) {	// 거꾸로 읽으면서 
        		if (turn == p) {	// 내 차례일 때 정답에 저장
        			answer += ton.charAt(i);
        		}
        		turn++;	// 아니면 다음 순서로
        		if (turn > m)	turn = 1;	// m번째 사람 다음 m+1이 아닌 1번 사람한테 턴이 가도록!
        	}
        	
        	number++;	// 다음 숫자로 또 진행해봅니다.
        }
        
        // while문의 조건을 정답의 길이가 t보다 작을때로 해놓았지만
        // 안쪽의 while문을 돌며 n진법으로 바꾼 수에서 여러번 정답을 기록해 둘 수 있기 때문에 필요한만큼만 잘라서 리턴
        return answer.substring(0, t);
    }

}
