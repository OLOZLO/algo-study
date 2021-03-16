package _210316.prog60059;

public class Main_Taekyung2 {
    public boolean solution(int[][] key, int[][] lock) {
        int M = key.length, N = lock.length;
        int size = 2 * M + N;
        int[][] map = new int[size][size];
        // 키를 어디서든 대볼 수 있게 자물쇠 배열 확장, 중간에 자물쇠 값 저장
        for(int i = M; i < M + N; i++)
            for(int j = M; j < M + N; j++)
                map[i][j] = lock[i - M][j - M];
        for(int d = 0; d < 4; d++) {
            for(int i = 0; i < size - M; i++) {
                for(int j = 0; j < size - M; j++) {
                    // 딱 맞는지 확인
                    if(chk(map, key, i, j, M, N))
                        return true;
                }
            }
            // 회전
            key = rotate(key);
        }
        return false;
    }

    static boolean chk(int[][] map, int[][] key, int y, int x, int m, int n) {
        boolean check = true;
        // key 다 더해봄
        for(int i = y; i < y + m; i++)
            for(int j = x; j < x + m; j++)
                map[i][j] += key[i - y][j - x];
        // 자물쇠 영역에 딱 맞는지 확인
        for(int i = m; i < m + n; i++)
            for(int j = m; j < m + n; j++)
                if(map[i][j] != 1) check = false;
        // 원상복구
        for(int i = y; i < y + m; i++)
            for(int j = x; j < x + m; j++)
                map[i][j] -= key[i - y][j - x];
        return check;
    }

    static int[][] rotate(int[][] key) {
        int n = key.length;
        int[][] tmp = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tmp[i][j] = key[j][n - i - 1];
        return tmp;
    }
}
