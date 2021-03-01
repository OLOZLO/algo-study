package _210302.prog42894;

public class Main_Taekyung2 {
    public int solution(int[][] board) {
        int[][] dy = {{1, 1, 1}, {1, 2, 2}, {1, 2, 2}, {1, 1, 1}, {1, 1, 1}};
        int[][] dx = {{0, 1, 2}, {0, 0, -1}, {0, 0, 1}, {0, -1, -2}, {-1, 0, 1}};

        int i, j, r, m, d;
        int n = board.length;
        int answer = 0;
        while(true) {
            boolean chk = false;
            for(i = 0; i < n; i++) {
                for(j = 0; j < n; j++) {
                    if(board[i][j] != 0) {
                        for(d = 0; d < 5; d++) {
                            for(r = 0; r < 3; r++) {
                                if(i + dy[d][r] < 0 || i + dy[d][r] >= n || j + dx[d][r] < 0 || j + dx[d][r] >= n ||
                                        board[i + dy[d][r]][j + dx[d][r]] != board[i][j])
                                    break;
                            }
                            if(r == 3) {
                                for(r = 0; r < 3; r++) {
                                    for(m = i + dy[d][r] - 1; m >= 0; m--)
                                        if(board[m][j + dx[d][r]] != 0) break;

                                    if(m >= 0 && board[m][j + dx[d][r]] != board[i][j]) break;
                                }
                                if(r == 3) {
                                    answer++;
                                    chk = true;
                                    board[i][j] = 0;
                                    for(r = 0; r < 3; r++)
                                        board[i + dy[d][r]][j + dx[d][r]] = 0;
                                }
                                break;
                            }
                        }
                    }
                }
            }
            if(!chk) break;
        }
        return answer;
    }
}