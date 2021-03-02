package _210302.prog42894;

import java.util.HashSet;

public class Main_hz {

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,4,0,0,0},{0,0,0,0,0,4,4,0,0,0},{0,0,0,0,3,0,4,0,0,0},{0,0,0,2,3,0,0,0,5,5},{1,2,2,2,3,3,0,0,0,5},{1,1,1,0,0,0,0,0,0,5}}));
        System.out.println(solution(new int[][]{{0,0,0,0,0},{1,0,0,2,0},{1,2,2,2,0},{1,1,0,0,0},{0,0,0,0,0}}));
    }

    public static int solution(int[][] board) {
        int answer = 0;

        boolean atLeast = true;

        while(atLeast) {
            HashSet<Integer> cant = new HashSet<>();
            atLeast = false;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0 ; j < board[0].length; j++) {
                    // 블록이 없는 칸이거나 이미 직사각형을 만들 수 없다고 판정된 블록은 무시!
                    if (board[i][j] == 0 || cant.contains(board[i][j])) continue;

                    // 속이 꽉 채워진 직사각형 만들 수 있는지 확인 (1~5인 경우는 직사각형 만들 수 있는 블록)
                    int type = blockType(i, j, board);
                    if (type == 0) cant.add(board[i][j]);
                    else {
                        // 직사각형 모양을 만들 수 있는 때, 블록이 추가되는 열의 위쪽으로 다른 블록이 존재하는지 확인해야됨
                        boolean exist = confirmUpper(type, i, j, board);
                        // 없을 경우 지우면 된다
                        if (!exist) {
                            atLeast = true;
                            answer++;
                            removeBlock(type, i, j, board);
                        }
                    }
                }
            }
        }
        return answer;
    }

    public static int blockType(int i, int j, int[][] board) {

        if (i+1 < board.length && board[i+1][j] != board[i][j]) return 0;
        else if (i+1 < board.length && j+2 < board[0].length &&
                board[i][j] == board[i+1][j+1] && board[i+1][j+1] == board[i+1][j+2]) return 1;
        else if (i+2 < board.length && board[i][j] == board[i+2][j]) {
            if (j-1 >= 0 && board[i+2][j] == board[i+2][j-1]) return 2;
            else if (j+1 < board[0].length && board[i+2][j] == board[i+2][j+1]) return 3;
        }
        else if (i+1 < board.length && j-1 >= 0 && board[i][j] == board[i+1][j-1]) {
            if (j-2 >= 0 && board[i+1][j-1] == board[i+1][j-2]) return 4;
            else if (j+1 < board[0].length && board[i+1][j-1] == board[i+1][j+1]) return 5;
        }
        return 0;
    }

    public static boolean confirmUpper(int type, int i, int j, int[][] board) {
        boolean block = false;

        if (type == 1 || type == 4 || type == 5) {
            int x, y1, y2;
            if (type == 1) {
                x = i; y1 = j+1; y2 = j+2;
            } else if (type == 4) {
                x = i; y1 = j-1; y2 = j-2;
            } else {
                x = i; y1 = j-1; y2 = j+1;
            }

            while (x >= 0) {
                if (board[x][y1] > 0 || board[x][y2] > 0) {
                    block = true;
                    break;
                }
                x--;
            }
        } else if (type == 2 || type == 3) {
            int x = i+1, y;
            if (type == 2) {
                y = j-1;
            } else {
                y = j+1;
            }

            while (x >= 0) {
                if (board[x][y] > 0) {
                    block = true;
                    break;
                }
                x--;
            }
        }

        return block;
    }

    public static void removeBlock(int type, int i, int j, int[][] board) {
        if (type == 1) {
            board[i][j] = board[i+1][j] = board[i+1][j+1] = board[i+1][j+2] = 0;
        } else if (type == 2) {
            board[i][j] = board[i+1][j] = board[i+2][j] = board[i+2][j-1] = 0;
        } else if (type == 3) {
            board[i][j] = board[i+1][j] = board[i+2][j] = board[i+2][j+1] = 0;
        } else if (type == 4) {
            board[i][j] = board[i+1][j] = board[i+1][j-1] = board[i+1][j-2] = 0;
        } else if (type == 5) {
            board[i][j] = board[i+1][j] = board[i+1][j-1] = board[i+1][j+1] = 0;
        }
    }
    
}
