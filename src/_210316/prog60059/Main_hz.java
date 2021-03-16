package _210316.prog60059;

public class Main_hz {

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{0, 0, 0}, {1, 0, 0}, {0, 1, 1}}, new int[][]{{1, 1, 1}, {1, 1, 0}, {1, 0, 1}}));
    }

    public static boolean solution(int[][] key, int[][] lock) {
        boolean answer = false;

        // lock을 확장시킨 door를 만듭니다.
        boolean[][] door = new boolean[key.length*2+ lock.length-2][key.length*2+ lock.length-2];
        int cnt = 0;
        for (int i = 0; i < lock.length; i++) {
            for (int j = 0; j < lock.length; j++) {
                if (lock[i][j] == 1) door[i+ key.length-1][j+ key.length-1] = true;
                else cnt++;
            }
        }

        for (int d = 0; d < 4; d++) {
            // door 전체를 탐색하면서
            for (int i = 0; i <= door.length- key.length; i++) {
                for (int j = 0; j <= door.length- key.length; j++) {
                    boolean same = true;
                    int fill = 0;

                    for (int ii = 0; ii < key.length; ii++) {
                        for (int jj = 0; jj < key.length; jj++) {

                            // 자물쇠 영역을 벗어나지 않은 부분에 대해서 키와 자물쇠 영역의 모양이 일치하는지 (1일때 0, 0일때 1) 확인합니다.
                            if (i+ii >= key.length-1 && j+jj >= key.length-1
                                    && i+ii <= door.length- key.length && j+jj <= door.length- key.length) {
                                if (!door[i+ii][j+jj] && key[ii][jj] == 1) fill++;
                                if ((door[i+ii][j+jj] && key[ii][jj] == 1) || (!door[i+ii][j+jj] && key[ii][jj] == 0)) {
                                    same = false;
                                    ii = key.length;
                                    break;
                                }
                            }
                        }
                    }
                    // 모양이 일치하면서 자물쇠영역의 홈 부분을 키가 모두 매울 수 있는 경우 열쇠로 자물쇠를 열 수 있습니다.
                    if (same && fill == cnt) {
                        answer = true;
                        i = door.length- key.length+1;
                        break;
                    }
                }
            }

            if (answer) break;
            lotation(key);
        }

        return answer;
    }

    // 키를 시계방향으로 90도 회전하는 함수입니다.
    public static void lotation(int[][] key) {
        int[][] tmp = new int[key.length][key.length];
        for (int i = 0; i < key.length; i++) tmp[i] = key[i].clone();

        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key.length; j++) {
                key[i][j] = tmp[key.length-1-j][i];
            }
        }
    }
}
