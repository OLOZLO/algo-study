package _210316.prog60059;

// https://ddb8036631.github.io/알고리즘%20풀이/프로그래머스_카카오2020_자물쇠와-열쇠/

public class Main_ms {
    public static boolean solution(int[][] key, int[][] lock) {
        int keyLen = key.length;
        int lockLen = lock.length;
        int[][] board = new int[2 * (keyLen - 1) + lockLen][2 * (keyLen - 1) + lockLen]; // 이 배열 크기에 대한 설명은 블로그에 설명해놈.
        int boardLen = board.length;

        for (int i = keyLen - 1; i <= boardLen - keyLen; i++) {
            for (int j = keyLen - 1; j <= boardLen - keyLen; j++) {
                board[i][j] = lock[i - keyLen + 1][j - keyLen + 1]; // board 라는 새로운 배열 정가운데에 자물쇠를 옮겨 적어두자.
            }
        }

        // 90도, 180도, 270도, 360도 총 네 번 회전.
        for (int d = 0; d < 4; d++) {
            key = rotate(key, keyLen);                                       // 회전을 먼저 시키고,

            for (int i = 0; i <= boardLen - keyLen; i++) {
                for (int j = 0; j <= boardLen - keyLen; j++) {
                    int[][] tmp = match(i, j, board, boardLen, key, keyLen); // 자물쇠에 키를 꽂아보자.

                    if (unlock(tmp, boardLen, keyLen, lockLen)) return true; // 해당 경우에 열리면? true 리턴.
                }
            }
        }

        return false;                                                        // 모든 경우 다 해봤는데 안열리면? false 리턴.
    }

    // rotate() : 2차원 배열을 시계방향으로 90도 회전시키는 메소드.
    static int[][] rotate(int[][] key, int keyLen) {
        int[][] tmp = new int[keyLen][keyLen];

        for (int i = 0; i < keyLen; i++) {
            for (int j = 0; j < keyLen; j++) {
                tmp[j][keyLen - 1 - i] = key[i][j];
            }
        }

        return tmp;
    }

    // match() : 열쇠를 자물쇠에 매칭시키는 메소드.
    // tmp라는 board를 복사한 2차원 배열을 하나 만들고, 여기에다 누적 작업(좌표 맞춰 키 값을 누적)을 함.
    static int[][] match(int x, int y, int[][] board, int boardLen, int[][] key, int keyLen) {
        int[][] tmp = new int[boardLen][boardLen];

        for (int idx = 0; idx < boardLen; idx++)
            System.arraycopy(board[idx], 0, tmp[idx], 0, board[idx].length);

        for (int i = x; i < x + keyLen; i++) {
            for (int j = y; j < y + keyLen; j++) {
                tmp[i][j] += key[i - x][j - y];
            }
        }

        return tmp; // tmp 주소를 리턴하고, 이 배열 주소를 unlock 파라미터로 전달.
    }

    // unlock() : 모든 요소가 1, 즉 자물쇠가 열리는 지 확인하는 메소드.
    static boolean unlock(int[][] board, int boardLen, int keyLen, int lockLen) {
        int cnt = 0;

        for (int i = keyLen - 1; i <= boardLen - keyLen; i++) {
            for (int j = keyLen - 1; j <= boardLen - keyLen; j++) {
                if (board[i][j] == 1) cnt++; // "정확히 1"이어야만 cnt++
            }
        }

        return cnt == lockLen * lockLen ? true : false; // 1의 개수가 (자물쇠 크기 X 자물쇠 크기)와 같아야 자물쇠가 열린 것.
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{0, 0, 0}, {1, 0, 0}, {0, 1, 1}}, new int[][]{{1, 1, 1}, {1, 1, 0}, {1, 0, 1}}));
    }
}
