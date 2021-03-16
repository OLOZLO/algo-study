package _210316.boj5213;

import java.io.*;
import java.util.*;

public class Main_Taekyung2 {
    static class Tile {
        int idx, num;
        Tile(int idx, int num) {
            this.idx = idx;
            this.num = num;
        }
    }
    static int N;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    static Tile[][] tiles;
    static ArrayList<Integer>[] adj;

    static int stoi(String s) { return Integer.parseInt(s); }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = stoi(br.readLine());
        tiles = new Tile[N][2 * N];
        int idx = 1;
        // 타일 몇번째인지, 숫자 몇인지 배열에 순서대로 입력
        // 홀수 행일때는 처음이랑 끝 null로 저장
        for(int i = 0; i < N; i++) {
            int a = i % 2;
            for(int j = a; j < 2 * N - a; j+=2) {
                st = new StringTokenizer(br.readLine());
                tiles[i][j] = new Tile(idx, stoi(st.nextToken()));
                tiles[i][j + 1] = new Tile(idx++, stoi(st.nextToken()));
            }
        }
        // 이동할 수 있는 타일끼리 인접 리스트 만듬
        adj = new ArrayList[idx];
        for(int i = 1; i < idx; i++) adj[i] = new ArrayList<>();
        for(int i = 0; i < N; i++)
            for(int j = 0; j < 2 * N; j++) {
                Tile cur = tiles[i][j];
                if(cur == null) continue;
                for(int d = 0; d < 4; d++) {
                    int ny = i + dy[d], nx = j + dx[d];
                    if(ny < 0 || nx < 0 || ny >= N || nx >= 2 * N || tiles[ny][nx] == null || cur.idx == tiles[ny][nx].idx) continue;
                    if(cur.num == tiles[ny][nx].num) adj[cur.idx].add(tiles[ny][nx].idx);
                }
            }
        Queue<int[]> q = new LinkedList<>();
        boolean[] visited = new boolean[idx];
        int[] path = new int[idx];
        visited[1] = true;
        q.add(new int[]{1, 1});
        int ret = 0, end = 1;
        // bfs로 갈 수 있는 최대까지 진행
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            if(cur[0] == idx - 1) {
                ret = cur[1];
                break;
            }
            for(int next : adj[cur[0]]) {
                if(visited[next]) continue;
                visited[next] = true;
                path[next] = cur[0];
                end = Math.max(end, next);
                q.add(new int[]{next, cur[1] + 1});
            }
        }
        System.out.println(ret);
        ArrayList<Integer> ans = new ArrayList<>();
        // 경로 복원
        while(end != 0) {
            ans.add(end);
            end = path[end];
        }
        Collections.reverse(ans);
        ans.forEach(x -> System.out.print(x + " "));
    }
}
