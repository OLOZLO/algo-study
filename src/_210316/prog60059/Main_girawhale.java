package _210316.prog60059;

public class Main_girawhale {
    int N, M;

    public boolean solution(int[][] key, int[][] lock) {
        M = key.length;
        N = lock.length;

        for (int k = 0; k < 4; k++) {
            for (int i = -M; i < N; i++) {
                for (int j = -M; j < N; j++)
                    if (solve(key, lock, i, j)) return true;  //key를 i와 j만큼 이동시켜 대볼거임. 근데 마지막까지 돌면 키 작동하는거니까 true 리턴
            }

            int[][] rotate = new int[M][M]; // 키 실패했으니까 돌림
            for (int i = 0; i < M; i++)
                for (int j = 0; j < M; j++)
                    rotate[i][j] = key[M - j - 1][i];
            key = rotate;
        }

        return false;
    }

    boolean solve(int[][] key, int[][] lock, int y, int x) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int ky = i + y, kx = j + x, k; // key의 좌표를 y, x만큼 이동시킴
                if (ky < 0 || kx < 0 || ky >= M || kx >= M) k = 0; // 근데 범위를 벗어나면? 빈칸이니까 0으로
                else k = key[ky][kx];

                if (lock[i][j] == k) return false; // 같으면 0,0 / 1,1이니까 둘다 안되므로 false 반환
            }
        }
        return true; // 마지막까지 이상없으면 true
    }
}
