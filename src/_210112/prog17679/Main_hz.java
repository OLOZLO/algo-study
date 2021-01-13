package _210112.prog17679;

public class Main_hz {
	static char[][] map;
	
	public static void main(String[] args) {
		
		System.out.println(solution(4, 5, new String[] {"CCBDE", "AAADE", "AAABF", "CCBBF"}));
		System.out.println(solution(6, 6, new String[] {"TTTANT", "RRFACC", "RRRFCC", "TRRRAA", "TTMMMF", "TMMTTJ"}));
	}
	
	public static int solution(int m, int n, String[] board) {
        int answer = 0;
        map = new char[m][n];
        
        for (int i = 0; i < m; i++) {	// 맵을 그립니다.
        	for (int j = 0; j < n; j++) 
        		map[i][j] = board[i].charAt(j);
        }
        
        while(true) {
        	if (!erase(m, n))	// 2x2가 붙어있는게 없으면 끝!
        		break;
        	
        	clean(m, n);	// 하나라도 지웠을 경우 블록들을 내려줍니다
        }
        
        for (int i = 0; i < m; i++) {	// 맵을 돌면서 지워진 블록을 카운팅 합니다
        	for (int j = 0; j < n; j++) {
        		if (map[i][j] == '.')
        			answer++;
        	}
        }
        
        return answer;
    }
	
	public static boolean erase(int m, int n) {	// 2x2 모양의 블럭들을 지워주는 함수. 하나라도 지웠을 경우 T/ 아니면 F 리턴
		boolean cont = false;	// 블럭의 지움 유무 표시 (continue의 약자임다... 하나라도 지웠다면 땡기고 다음에도 지울 수 있나 확인할꺼니까)
		
		char[][] next = new char[m][n];	// map을 복사한 배열. 2x2모양들을 지운 상태 저장할 목적
		for (int i = 0; i < next.length; i++) {	// deep copy를 합니다 (같은 블록이 여러 2x2에 포함될 수 있기 때문에 복사한 map에 저장)
			System.arraycopy(map[i], 0, next[i], 0, map[0].length);
		}
		
		for (int i = 0; i < m-1; i++) {
			for (int j = 0; j < n-1; j++) {
				if (map[i][j] == '.')
					continue;
				
				if ((map[i][j] == map[i][j+1]) && (map[i][j] == map[i+1][j])
						&& (map[i][j] == map[i+1][j]) && (map[i][j] == map[i+1][j+1])) {	// 2x2가 있는 경우 다 지워버리고 지웠다고 기록해놓기
					cont = true;
					next[i][j] = next[i][j+1] = next[i+1][j] = next[i+1][j+1] = '.';
				}
			}
		}
		
		map = next;
		
		return cont;
	}
	
	public static void clean(int m, int n) {
		
		// i는 행 j는 열!
		for (int j = 0; j < n; j++) {
			for (int i = m-1; i >= 0; i--) {	// 제일 아래 행부터 위로 올라가면서 확인할껍니다 
				if (map[i][j] == '.') {	// 지금 보는 칸이 지워졌을 때
					for (int k = i-1; k >= 0; k--) {	// 내 위부터 쭉 올라가면서
						if (map[k][j] != '.') {	// 블럭이 있을 경우
							map[i][j] = map[k][j];	// 걔를 지금 보는 칸으로 내리고
							map[k][j] = '.';	// 걔를 지워줍니다
							break;
						}
					}
				}
			}
		}
	}
}
