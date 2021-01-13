package _210112.prog17679;

public class Main_jyj {
    class Solution {
        private static char[][] boardMap;
        private static boolean[][] checked;
        
        public int solution(int m, int n, String[] board) {
            int answer = 0;
            boardMap = new char[m][n];
            checked = new boolean[m][n];
            
            for (int i = 0; i < m; i++) {
                boardMap[i] = board[i].toCharArray();
            }
            
            while (true) {
                check(m, n);
                if (countCheck(m, n) == 0) {
                    break;
                }
                answer += countCheck(m, n);
                removeCheck(m, n);
            }
            
            return answer;
        }
        
        
        static void check(int m, int n) {
            int[] dx = { 0, 1, 1 };
            int[] dy = { 1, 0, 1 };
    
            for (int i = 0; i < m - 1; i++) {
                for (int j = 0; j < n - 1; j++) {
    
                    if (boardMap[i][j] == ' ') {
                        continue;
                    }
                    boolean flag = true;
    
                    for (int k = 0; k < 3; k++) {
                        int next_x = i + dy[k];
                        int next_y = j + dx[k];
                        if (next_x >= 0 && next_y >= 0 && next_x < m && next_y < n) {
                            if (boardMap[i][j] != boardMap[next_x][next_y]) {
                                flag = false;
                                break;
                            }
                        }
                    }
    
                    if (flag) {
    
                        checked[i][j] = true;
                        for (int k = 0; k < 3; k++) {
                            int next_x = i + dx[k];
                            int next_y = j + dy[k];
                            checked[next_x][next_y] = true;
                        }
                    }
                }
            }
        }
        
        
        static int countCheck(int m, int n) {
            int count = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (checked[i][j]) {
                        boardMap[i][j] = ' ';
                        count += 1;
                    }
                }
            }
            return count;
        }
        
        
        static void removeCheck(int m, int n) {
    
            String tmpStr;
    
            for (int j = 0; j < n; j++) {
                tmpStr = "";
    
                for (int i = 0; i < m; i++) {
                    if (boardMap[i][j] != ' ') {
                        tmpStr += boardMap[i][j];
                    }
                }
    
                for (int i = 0; i < tmpStr.length(); i++) {
                    boardMap[m - i - 1][j] = tmpStr.charAt(tmpStr.length() - i - 1);
                    checked[m - i - 1][j] = false;
                }
    
                for (int i = 0; i < m - tmpStr.length(); i++) {
                    boardMap[i][j] = ' ';
                    checked[i][j] = false;
                }
    
            }
        }
    }

}
