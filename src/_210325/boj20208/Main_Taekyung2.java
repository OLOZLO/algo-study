package _210325.boj20208;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main_Taekyung2 {
    static int N, M, H, ret, s;
    static int[] pick;
    static int[][] map;
    static boolean[] visited;
    static Point home;
    static ArrayList<Point> mincho = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        H = sc.nextInt();
        map = new int[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                map[i][j] = sc.nextInt();
                if(map[i][j] == 1)
                    home = new Point(j, i);
                else if(map[i][j] == 2)
                    mincho.add(new Point(j, i));
            }
        }
        mincho.add(home);
        Collections.reverse(mincho);
        s = mincho.size();
        pick = new int[s];
        visited = new boolean[s];
        simulation(1);
        System.out.println(ret);
    }

    static void simulation(int cnt) {
        if(cnt == s) {
            int heal = M, ans = 0;
            for(int i = 1; i < s; i++) {
                heal -= dist(pick[i - 1], pick[i]);
                if(heal >= 0) ans++;
                else break;
                heal += H;
                if(heal >= dist(0, pick[i]))
                    ret = Math.max(ret, ans);
            }
            return;
        }
        for(int i = 1; i < s; i++) {
            if(!visited[i]) {
                visited[i] = true;
                pick[cnt] = i;
                simulation(cnt + 1);
                visited[i] = false;
            }
        }
    }

    static int dist(int a, int b) {
        return Math.abs(mincho.get(a).y - mincho.get(b).y) + Math.abs(mincho.get(a).x - mincho.get(b).x);
    }
}