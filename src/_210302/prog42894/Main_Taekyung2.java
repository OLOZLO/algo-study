package _210302.prog42894;

public class Main_Taekyung2 {
    public int solution(int[][] board) {
        // 블록 모양은 12개인데 직사각형 만들 수 있는건 5개임
        // (0, 0)부터 완탐할 때 블록 만났을 때 밑의 모양이면 직사각형 만들 수 있음
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
                                // 직사각형 만들 수 있는 모양인가
                                if(i + dy[d][r] < 0 || i + dy[d][r] >= n || j + dx[d][r] < 0 || j + dx[d][r] >= n ||
                                        board[i + dy[d][r]][j + dx[d][r]] != board[i][j])
                                    break;
                            }
                            // 만들 수 있는 블록일 때
                            if(r == 3) {
                                // 위로 올라가면서 다른 블록이 막고 있는지 확인
                                for(r = 0; r < 3; r++) {
                                    for(m = i + dy[d][r] - 1; m >= 0; m--)
                                        if(board[m][j + dx[d][r]] != 0) break;
                                    // 확인하는 블록의 위에 칸들만 확인
                                    if(m >= 0 && board[m][j + dx[d][r]] != board[i][j]) break;
                                }
                                // 위에 막고있는 게 없고 없앨 수 있으면 카운트하고, 블록 없앰
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
            if(!chk) break; // 없앨 블록이 없었음, break
        }
        return answer;
    }
}