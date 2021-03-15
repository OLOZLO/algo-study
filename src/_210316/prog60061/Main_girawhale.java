package _210316.prog60061;

import java.util.ArrayList;
import java.util.List;

public class Main_girawhale {
    int N;
    boolean[][] pillars, beams;

    public int[][] solution(int n, int[][] build_frame) {
        N = n;
        pillars = new boolean[n + 1][n + 1];
        beams = new boolean[n + 1][n + 1];

        for (int[] b : build_frame) {
            if (b[3] == 1) { // 설치. 설치 가능하면 해당칸에 설치함
                if (b[2] == 0) pillars[b[0]][b[1]] = checkPillar(b);
                else if (b[2] == 1) beams[b[0]][b[1]] = checkBeam(b);
            } else {
                boolean[][] tmp = b[2] == 0 ? pillars : beams;
                tmp[b[0]][b[1]] = false; // 먼저 칸에 설치된거 지워봄

                // 모든 칸 돌면서 지워도 정상적으로 설치되는지 확인
                for (int i = 0; i <= n; i++) {
                    for (int j = 0; j <= n; j++) {
                        if ((beams[i][j] && !checkBeam(new int[]{i, j})) ||
                                (pillars[i][j] && !checkPillar(new int[]{i, j}))) { // 만약 조건에 부합하지 않으면 다시 칸에 원래대로 설치해줌
                            tmp[b[0]][b[1]] = true;
                            break;
                        }
                    }
                }
            }
        }

        List<int[]> ans = new ArrayList<>();
        for (int i = 0; i <= n; i++) { // 반복 돌면서 칸에 설치된 애들 모두 리스트에 넣어줌
            for (int j = 0; j <= n; j++) {
                if (pillars[i][j]) ans.add(new int[]{i, j, 0});
                if (beams[i][j]) ans.add(new int[]{i, j, 1});
            }
        }

        return ans.toArray(new int[0][]);
    }

    boolean checkPillar(int[] b) {
        return b[1] == 0 || pillars[b[0]][b[1] - 1] || (b[0] > 0 && beams[b[0] - 1][b[1]]) || beams[b[0]][b[1]];
    }

    boolean checkBeam(int[] b) {
        return pillars[b[0]][b[1] - 1] || pillars[b[0] + 1][b[1] - 1] ||
                b[0] > 0 && b[0] < N && beams[b[0] - 1][b[1]] && beams[b[0] + 1][b[1]];
    }
}
