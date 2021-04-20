package _210420.boj2469;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*

1. 결과 : 실패 (테케도 안돌아갑니다...)
2. 시간 복잡도 :

3. 풀이
    각 참가자가 DFS를 돌며 사다리를 타고 ?를 만났을 경우 -일 때, *일 때 두 가지 다 돌려서 완전 탐색을 하려고 했습니다.
    이 방법이 잘못됐다고 생각하여 수정을 하려 했는데, 입력으로 준 맵의 가로가 k-1인것을 방금 발견하여 코드를 아예 갈아 엎어야할 것 같습니다.
    리뷰 안해주셔도 됩니다... 고치는대로 수정해서 올리겠습니다...
*/
public class Main_hz {
    static int N;
    static char[][] map;
    static boolean[][] visited;

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
        N = Integer.parseInt(br.readLine());

        int[] order = new int[K];
        String in = br.readLine();
        for (int i = 0; i < K; i++) {
            order[in.charAt(i)-'A'] = i;
        }

        map = new char[N][K];
        int resultIdx = 0;
        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
            if (map[i][0] == '?') resultIdx = i;
        }

        boolean impossible = false;
        visited = new boolean[N][K];
        for (int j = 0; j < K; j++) {
            dfs(new Pos(0, j), null, order[j]);
        }

        System.out.println(Arrays.toString(map[resultIdx]));
    }

    public static void dfs(Pos p, Pos tmp, int dest) { // 도착해야되는 곳

        if (p.i == N) {
            if (p.j == dest) map[tmp.i][tmp.j] = '*';
            else map[tmp.i][tmp.j] = '-';

            return;
        }

        visited[p.i][p.j] = true;

        if (map[p.i][p.j] == '-') {
            if (!visited[p.i][p.j+1]) dfs(new Pos(p.i, p.j+1), tmp, dest);
            else dfs(new Pos(p.i+1, p.j), tmp, dest);
        }
        else if (map[p.i][p.j] == '*') {
            if (p.j-1 >= 0 && !visited[p.i][p.j-1] && map[p.i][p.j-1] == '-') dfs(new Pos(p.i, p.j-1), tmp, dest);
            else dfs(new Pos(p.i+1, p.j), tmp, dest);
        }
        else if (map[p.i][p.j] == '?') {
            dfs(new Pos(p.i+1, p.j), new Pos(p.i, p.j), dest);
//            dfs(new Pos(p.i, ))
        }

        visited[p.i][p.j] = false;
    }

}
