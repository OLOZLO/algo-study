package _210420.boj2469;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*

1. 결과 : 성공
2. 시간 복잡도 : O(K*N)?
               잘 모르겠습니다... 모든 출발점에서(K) 모든 도착지까지(N)의 뎁스를 지나고, ?일 때만 3가지 방법으로 분기하기 때문에
               O(K*N)이라 생각했는데 혹시 시간복잡도에 대해 아시는 분은 조언 부탁드립니다.
3. 풀이
    원래는 dfs를 돌면서 ?인 위치에서 왼쪽에 사다리가 있다고 가정, 오른쪽에 사다리가 있다고 가정, 양 옆에 사다리가 없는 경우를 가정하여 완전 탐색을 하려 했지만,
    분기되는 경우로 돌아오는 부분을 어떻게 처리해야될지 몰라 while을 사용하여 완전 탐색을 진행했습니다.
*/
public class Main_hz {

    public static class Pos {
        int i, j;

        Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int K = Integer.parseInt(br.readLine());
        int N = Integer.parseInt(br.readLine());

        // 각 알파벳별 도착 순서를 저장하는 배열
        int[] order = new int[K];
        String in = br.readLine();
        for (int i = 0; i < K; i++) order[in.charAt(i)-'A'] = i;

        // 해당 위치에서 좌, 우로 이동할수 있는지 여부를 저장하는 배열
        boolean[][][] map = new boolean[N+1][K][2];
        int resultIdx = 0;  // ?인 행 저장
        for (int i = 0; i < N; i++) {
            in = br.readLine();

            if (in.charAt(0) == '?') {
                resultIdx = i;
                continue;
            }

            for (int j = 0; j < in.length(); j++) {
                if (in.charAt(j) == '-') {
                    map[i][j][1] = true;
                    if (j+1 < K) map[i][j+1][0] = true;
                }
            }
        }

        boolean impossible = false; // 순서대로 나올 수 없는 경우

        for (int k = 0; k < K; k++) {
            Pos p = new Pos(0, k);  // 출발 위치. 얘를 조작하면서 사다리 이동
            Pos tmp = null; // ?를 만났을 때 위치 저장
            int tmpIdx = 0; // 0: 왼쪽으로 이동 가능, 1: 오른쪽으로 이동 가능, 2: 아래로만 이동 가능

            if (impossible) break;

            while(true) {

                // 맨 아랫줄에 도착한 경우
                if (p.i == N) {
                    if (tmp == null) break;

                    if (p.j == order[k]) {
                        if (tmpIdx == 0) {
                            map[tmp.i][tmp.j][0] = true;
                            if (tmp.j > 0) map[tmp.i][tmp.j-1][1] = true;
                        } else if (tmpIdx == 1) {
                            map[tmp.i][tmp.j][1] = true;
                            if (tmp.j+1 < K) map[tmp.i][tmp.j+1][0] = true;
                        }
                        break;
                    } else {
                        if (tmpIdx < 3) {
                            p = tmp;
                            tmpIdx++;
                            continue;
                        } else {
                            impossible = true;
                            break;
                        }
                    }
                }

                // ? 인 행에 도착했을 때
                if (p.i == resultIdx) {
                    tmp = new Pos(p.i, p.j);    // 원하는 결과를 얻지 못했을 때 다시 돌아오기 위해 현재 위치 저장
                    if (tmpIdx == 0) {  // 왼쪽에 -가 있다고 가정
                        if (p.j > 0) p.j -= 1;
                        else {  // 현재 제일 왼쪽 열에 있을 경우 pass
                            tmpIdx++;
                            continue;
                        }
                    }
                    else if (tmpIdx == 1) { // 오른쪽에 -가 있다고 가정
                        if (p.j+1 < K) p.j += 1;
                        else {  // 현재 제일 오른쪽 열에 있을 경우 pass
                            tmpIdx++;
                            continue;
                        }
                    }
                    // 왼쪽이나 오른쪽에 -가 있다고 가정할 때 계속 왼오왼오로 이동하는 경우를 막기 위해 왼||오로 이동 후 한칸 아래로 이동시킴
                    p.i += 1;
                    continue;
                }

                if (map[p.i][p.j][0] && p.j > 0) p.j -= 1;
                else if (map[p.i][p.j][1] && p.j+1 < K) p.j += 1;
                p.i += 1;
            }
        }

        // map에 저장된 결과를 보며 감춰진 사다리의 모양 생성
        StringBuilder sb = new StringBuilder();
        if (impossible) {
            for (int i = 0; i < K - 1; i++) sb.append("x");
        } else {
            for (int j = 0; j < K-1; j++) {
                if (map[resultIdx][j][1]) sb.append("-");
                else sb.append("*");
            }
        }

        System.out.println(sb.toString());
    }

}
