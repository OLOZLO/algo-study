package _210511.boj20166;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_hz {

    /*
     * 1. 결과 : 시간 내 실패 & 성공
     * 2. 시간복잡도 : O((N*M*8)^6)...?
     * 3. 풀이
     *    : bfs를 사용하여 문자열을 만들었습니다.
     *      이때 bfs를 돌며 이어붙인 문자열이 신이 좋아하는 문자열에 해당하는지 확인하는 속도를 줄이기 위해
     *      신이 좋아하는 문자열을 입력받을때 문자열의 부분집합을 sub라는 HashSet에 저장하여 비교했습니다.
     *
     * */

    public static int[][] dir = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

    public static class Pos {
        int i, j;
        StringBuilder sb;

        Pos(int i, int j, StringBuilder sb) {
            this.i = i;
            this.j = j;
            this.sb = sb;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        Queue<Pos> q = new LinkedList<>();
        char[][] map = new char[N][M];
        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                StringBuilder sb = new StringBuilder();
                q.add(new Pos(i, j, sb.append(map[i][j])));
            }
        }

        String[] result = new String[K];    // 정답 출력시 순서를 보장하기 위해 String 배열에 저장
        HashMap<String, Integer> words = new HashMap<>();   // 경우의 수를 저장하기 위해 Map 사용
        HashSet<String> sub = new HashSet<>();  // 정답 문자의 부분문자를 저장하는 Set
        for (int i = 0; i < K; i++) {
            result[i] = br.readLine();
            words.put(result[i], 0);
            
            for (int j = 0; j < result[i].length(); j++) {
                sub.add(result[i].substring(0, j+1));
            }
        }

        while(!q.isEmpty()) {
            Pos now = q.poll();

            if (words.containsKey(now.sb.toString())) { // 정답 문자에 해당하는지 확인
                words.put(now.sb.toString(), words.getOrDefault(now.sb.toString(), 0)+1);
            }

            for (int d = 0; d < dir.length; d++) {
                int ni = (now.i + dir[d][0] + N) % N;
                int nj = (now.j + dir[d][1] + M) % M;

                StringBuilder sb = new StringBuilder();
                sb.append(now.sb).append(map[ni][nj]);

                // 이어 붙인 문자열이 부분문자열에 해당하는지 확인
                if (sub.contains(sb.toString())) q.add(new Pos(ni, nj, sb));
            }
        }

        for (int i = 0; i < result.length; i++)
            System.out.println(words.getOrDefault(result[i], 0));
    }
}
