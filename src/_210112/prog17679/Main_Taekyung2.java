package _210112.prog17679;

public class Main_Taekyung2 {
    char[][] map;

    public int solution(int m, int n, String[] board) {
        int answer = 0;
        map = new char[m][n];
        for(int i = 0; i < m; i++)
            map[i] = board[i].toCharArray();
        while(true) {
            int cnt = remove(m, n);
            if(cnt == 0) break;
            answer += cnt;
            down(m, n);
        }
        return answer;
    }

    public int remove(int m, int n) {
        int cnt = 0;
        boolean[][] visited = new boolean[m][n];
        for(int i = 0; i < m - 1; i++) {
            for(int j = 0; j < n - 1; j++) {
                char cur = map[i][j];
                if(cur != '.' && cur == map[i][j + 1] && cur == map[i + 1][j] && cur == map[i + 1][j + 1]) {
                    visited[i][j] = true;
                    visited[i][j + 1] = true;
                    visited[i + 1][j] = true;
                    visited[i + 1][j + 1] = true;
                }
            }
        }

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(visited[i][j]) {
                    cnt++;
                    map[i][j] = '.';
                }
            }
        }
        return cnt;
    }

    public void down(int m, int n) {
        for(int k = 0; k < m; k++){
            for(int i = m - 1; i > 0; i--){
                for(int j = 0; j < n; j++){
                    if(map[i][j] =='.' && map[i - 1][j] != '.'){
                        map[i][j] = map[i - 1][j];
                        map[i - 1][j] = '.';
                    }
                }
            }
        }
    }
}