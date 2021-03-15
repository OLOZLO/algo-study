package _210316.boj5213;

import java.util.*;

public class Main_girawhale {
    public static void main(String[] args) {
        int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};

        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), cnt = 0, size = N * N - N / 2;
        int[][] nums = new int[N][2 * N], tiles = new int[N][2 * N]; // nums: 조각의 번호를 저장하는 배열, tiles: 1 1 2 2 3 3 등 타일 번호를 저장

        for (int i = 0; i < N; i++) {
            int tmp = i % 2;
            for (int j = tmp; j < 2 * N - tmp; j++) { // 짝수줄이면 2*N개의 조각, 홀수면 2*N-2의 조각
                nums[i][j] = sc.nextInt();
                tiles[i][j] = (cnt++ / 2) + 1;
            }
        }

        ArrayList<Integer>[] adj = new ArrayList[N * N + 1];
        for (int i = 1; i <= N * N; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 2 * N; j++) {
                if (nums[i][j] == 0) continue; // 0이면 빈 조각. pass

                for (int k = 0; k < 4; k++) {
                    int ny = i + dy[k], nx = j + dx[k];
                    if (ny < 0 || nx < 0 || ny >= N || nx >= 2 * N ||
                            tiles[ny][nx] == tiles[i][j] || nums[ny][nx] != nums[i][j]) // 타일의 번호가 같고 같은 타일이 아니면 add
                        continue;

                    adj[tiles[i][j]].add(tiles[ny][nx]);
                }
            }
        }

        int[] visit = new int[size + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        visit[1] = -1; // 시작 타일에 -1을 넣어 표시

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if (cur == size) break; // 마지막 타일이면 탈출

            for (int a : adj[cur]) {
                if (visit[a] != 0) continue; // 방문한 타일이면 pass

                queue.add(a);
                visit[a] = cur; // 내가 방문하기 이전 타일 번호를 넣어줌
            }
        }
        Deque<Integer> stack = new ArrayDeque<>();
        int n = size;
        while (visit[n] == 0) n--;// 마지막 번호부터 방문한 타일을 찾을 때까지 --

        stack.push(n);
        while (visit[stack.peek()] != -1) // 스택에 넣은 타일이 시작 타일이 될때까지 stack에 넣기
           stack.push(visit[stack.peek()]);

        System.out.println(stack.size());
        stack.forEach(i -> System.out.print(i + " "));
    }
}
