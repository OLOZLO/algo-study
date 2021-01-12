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
        
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) 
        		map[i][j] = board[i].charAt(j);
        }
        
        while(true) {
        	if (!erase(m, n))
        		break;
        	
        	clean(m, n);
        }
        
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (map[i][j] == '.')
        			answer++;
        	}
        }
        
        return answer;
    }
	
	public static boolean erase(int m, int n) {
		boolean cont = false;
		
		char[][] next = new char[m][n];
		for (int i = 0; i < next.length; i++) {
			System.arraycopy(map[i], 0, next[i], 0, map[0].length);
		}
		
		for (int i = 0; i < m-1; i++) {
			for (int j = 0; j < n-1; j++) {
				if (map[i][j] == '.')
					continue;
				
				if ((map[i][j] == map[i][j+1]) && (map[i][j] == map[i+1][j])
						&& (map[i][j] == map[i+1][j]) && (map[i][j] == map[i+1][j+1])) {
					cont = true;
					next[i][j] = next[i][j+1] = next[i+1][j] = next[i+1][j+1] = '.';
				}
			}
		}
		
		map = next;
		
		return cont;
	}
	
	public static void clean(int m, int n) {
		
		for (int j = 0; j < n; j++) {
			for (int i = m-1; i >= 0; i--) {
				if (map[i][j] == '.') {
					for (int k = i-1; k >= 0; k--) {
						if (map[k][j] != '.') {
							map[i][j] = map[k][j];
							map[k][j] = '.';
							break;
						}
					}
				}
			}
		}
	}
}
