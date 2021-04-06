package _210325.boj20208;

import java.awt.*;
import java.util.*;

public class Main_Taekyung2 {
    static int N, M, H, ret;
    static boolean[] visited;
    static Point home;
    static ArrayList<Point> mincho = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        H = sc.nextInt();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                int n = sc.nextInt();
                if(n == 1)
                    home = new Point(j, i);
                else if(n == 2)
                    mincho.add(new Point(j, i));
            }
        }
        visited = new boolean[mincho.size()];
        simulation(home, 0, M);
        System.out.println(ret);
    }

    static void simulation(Point cur, int cnt, int heal) {
        if(dist(cur, home) <= heal) ret = Math.max(ret, cnt);
        for(int i = 0; i < mincho.size(); i++) {
            int d = dist(cur, mincho.get(i));
            if(!visited[i] && heal >= d) {
                visited[i] = true;
                simulation(mincho.get(i), cnt + 1, heal - d + H);
                visited[i] = false;
            }
        }
    }

    static int dist(Point a, Point b) {
        return Math.abs(a.y - b.y) + Math.abs(a.x - b.x);
    }
}