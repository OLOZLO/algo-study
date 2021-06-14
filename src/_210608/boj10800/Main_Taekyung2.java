package _210608.boj10800;

import java.io.*;
import java.util.*;

public class Main_Taekyung2 {


    /**
     * [골드3] 컬러볼
     * 1. 결과 : 맞았습니다.
     * 2. 시간복잡도 : O(N * lgN), 정렬
     *
     * 3. 풀이
     * 	(1) 정렬, 누적합
     *
     * 4. 후기
     *  - 크기순으로만 정렬하고, 컬러는 정렬 안해서 계속 틀렸음
     *  - 이런거 풀이가 바로 생각나면 좋을텐데
     */


    static int N, sum = 0;
    static Ball[] balls;
    // ret = 출력 저장,  colorArr = 같은 컬러 누적합, sizeArr = 같은 크기 누적합
    static int[] ret, colorArr, sizeArr;

    public static int stoi(String s) { return Integer.parseInt(s); }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = stoi(br.readLine());
        balls = new Ball[N];
        ret = new int[N];
        colorArr = new int[N + 1];
        sizeArr = new int[2001];

        for(int i = 0; i < N; i++) {
            String[] tmp = br.readLine().split(" ");
            balls[i] = new Ball(i, stoi(tmp[0]), stoi(tmp[1]));
        }

        // 사이즈 오름차순으로 정렬합니다, 크기가 같다면 컬러 오름차순으로 정렬합니다
        Arrays.sort(balls, (b1, b2) -> {
            if(b1.size == b2.size) return b1.color - b2.color;
            else return b1.size - b2.size;
        });

        for(int i = 0; i < N; i++) {
            Ball b = balls[i];
            // sum = 현재 공까지의 누적합
            // colorArr[b.color] = 현재 공이랑 같은 색깔인 거 합, 못잡아먹으니까 빼준다
            // sizeArr[b.size] = 현재 공이랑 같은 크기인 거 합, 못잡아먹으니까 빼준다
            ret[b.idx] = sum - colorArr[b.color] - sizeArr[b.size];

            // 같은 색인거, 같은 크기인거 이미 뺐는데 같은 색이면서 같은 크기이면 2중으로 빼는게 된다, 같은 크기이고 같은 색이면 처음 계산한 답으로 일치시켜준다
            if(i != 0 && balls[i - 1].size == b.size && balls[i - 1].color == b.color)
                ret[b.idx] = ret[balls[i - 1].idx];

            // 누적합 처리
            sum += b.size;
            colorArr[b.color] += b.size;
            sizeArr[b.size] += b.size;
        }

        // 출력 단계
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++)
            sb.append(ret[i]).append("\n");
        System.out.println(sb);
    }

    static class Ball {
        int idx, color, size;

        public Ball(int idx, int color, int size) {
            this.idx = idx;
            this.color = color;
            this.size = size;
        }
    }
}
