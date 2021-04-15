package _210413.boj17143;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
1. 결과 : 성공
2. 시간 복잡도 : O(C * M * (1000 % Math.max(R, C)))
                < O(100 * 10^4 * (10^3 % 100))
                < O(10^6 * 100) = O(10^8) 
    - 이유 : 낚시왕이 움직이는 거리 = C <= 100
            상어의 수 = M <= 10^4
            상어 한마리 당 움직이는 최대 거리 = 1000 % Math.max(R, C)
            
            상어가 이동하고 먹히는 과정은 더하기 연산이므로 제외시켜도 무관

3. 풀이 : 단순 시뮬레이션. 직접 상어를 움직이고 낚시를 해 끝까지 이동하면 되는 문제
*/
public class Main_girawhale {
    static int[] dy = {0, -1, 1, 0, 0};
    static int[] dx = {0, 0, 0, 1, -1};
    static int[] dir = {0, 2, 1, 4, 3}; // 뒤집어줄 방향 저장

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Shark[][] map = new Shark[R + 1][C + 1];
        int[] mod = {0, 2 * R - 2, 2 * C - 2}; // 상하로 움직일때, 좌우로 움직일때 이만큼 이동하면 다시 원상복구된다!

        for (int i = 0; i < M; i++) {
            Shark shark = new Shark(Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray());
            shark.speed %= mod[(shark.dir + 1) / 2]; // mod값만큼 이동하면 처음 위치로 돌아오기 때문에 나머지만큼만 이동하도록 설정
            map[shark.y][shark.x] = shark;
        }

        int pos = 0; // 낚시왕의 x좌표
        int ans = 0; // 낚시한 상어무게

        while (pos < C) {
            pos++;

            // 낚시왕이 위치한 열에서 아래로 내리면서 null이 아니라면 상어가 존재
            // 위치한 상어의 무게를 답에 더하고 map을 비우자!
            for (int y = 1; y <= R; y++) {
                if (map[y][pos] != null) {
                    ans += map[y][pos].size; // 상어 무게 더해준당
                    map[y][pos] = null; // map에 상어가 사라졌어요!
                    break;
                }
            }

            Queue<Shark> move = new LinkedList<>(); // 이동한 상어들의 정보들을 담을 Queue
            for (int i = 1; i <= R; i++) {
                for (int j = 1; j <= C; j++) {
                    if (map[i][j] == null) continue; // 상어 없으면 pass

                    Shark shark = map[i][j];
                    for (int k = 0; k < shark.speed; k++) {
                        if (shark.dir == 1 && shark.y == 1 || shark.dir == 2 && shark.y == R
                                || shark.dir == 3 && shark.x == C || shark.dir == 4 && shark.x == 1) {
                            shark.dir = dir[shark.dir]; // 상어 이동 방향마다 벽에 박으면 뒤집어주기
                        }

                        shark.y += dy[shark.dir];
                        shark.x += dx[shark.dir];
                    }

                    map[i][j] = null; // 상어가 이동했으니까 좌표 비워주고
                    move.add(shark); // queue에 넣어 한번에 이동하자
                }
            }

            while (!move.isEmpty()) {
                Shark shark = move.poll();

		Shark sharkInMap = map[shark.y][shark.x];
		if (sharkInMap == null || (sharkInMap != null && (sharkInMap.size < shark.size))) 
		    map[shark.y][shark.x] = shark;
            }
        }

        System.out.println(ans);
    }

    static class Shark {
        int y, x, speed, dir, size;

        Shark(int[] info) {
            y = info[0];
            x = info[1];
            speed = info[2];
            dir = info[3];
            size = info[4];
        }
    }
}
