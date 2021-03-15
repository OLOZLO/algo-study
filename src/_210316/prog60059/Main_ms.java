package _210316.prog60059;

// https://ddb8036631.github.io/알고리즘%20풀이/프로그래머스_카카오2020_자물쇠와-열쇠/

public class Main_ms {
    public static boolean solution(int[][] key, int[][] lock) {
        int keyLen = key.length;
        int lockLen = lock.length;
        int[][] board = new int[2 * (keyLen - 1) + lockLen][2 * (keyLen - 1) + lockLen];
        int boardLen = board.length;

        for (int i = keyLen - 1; i <= boardLen - keyLen; i++) {
            for (int j = keyLen - 1; j <= boardLen - keyLen; j++) {
                board[i][j] = lock[i - keyLen + 1][j - keyLen + 1];
            }
        }

        for (int d = 0; d < 4; d++) {
            key = rotate(key, keyLen);

            for (int i = 0; i <= boardLen - keyLen; i++) {
                for (int j = 0; j <= boardLen - keyLen; j++) {
                    int[][] tmp = match(i, j, board, boardLen, key, keyLen);

                    if (unlock(tmp, boardLen, keyLen, lockLen)) return true;
                }
            }
        }

        return false;
    }

    static int[][] rotate(int[][] key, int keyLen) {
        int[][] tmp = new int[keyLen][keyLen];

        for (int i = 0; i < keyLen; i++) {
            for (int j = 0; j < keyLen; j++) {
                tmp[j][keyLen - 1 - i] = key[i][j];
            }
        }

        return tmp;
    }

    static int[][] match(int x, int y, int[][] board, int boardLen, int[][] key, int keyLen) {
        int[][] tmp = new int[boardLen][boardLen];

        for (int idx = 0; idx < boardLen; idx++)
            System.arraycopy(board[idx], 0, tmp[idx], 0, board[idx].length);

        for (int i = x; i < x + keyLen; i++) {
            for (int j = y; j < y + keyLen; j++) {
                tmp[i][j] += key[i - x][j - y];
            }
        }

        return tmp;
    }

    static boolean unlock(int[][] board, int boardLen, int keyLen, int lockLen) {
        int cnt = 0;

        for (int i = keyLen - 1; i <= boardLen - keyLen; i++) {
            for (int j = keyLen - 1; j <= boardLen - keyLen; j++) {
                if (board[i][j] == 1) cnt++;
            }
        }

        return cnt == lockLen * lockLen ? true : false;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{0, 0, 0}, {1, 0, 0}, {0, 1, 1}}, new int[][]{{1, 1, 1}, {1, 1, 0}, {1, 0, 1}}));
    }
}
