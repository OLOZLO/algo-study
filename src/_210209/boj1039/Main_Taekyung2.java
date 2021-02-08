package _210209.boj1039;

import java.util.*;

public class Main_Taekyung2 {
    static class Num {
        String num;
        int c;

        Num(String num, int c) {
            this.num = num;
            this.c = c;
        }
    }
    static String N;
    static int K, ret = -1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.next();
        K = sc.nextInt();
        int s = N.length();
        // 1자리 수이거나 2자리 수인데 뒤에가 0이면 못바꿈
        if(s == 1 || (s == 2 && N.charAt(1) == '0')) {
            System.out.println(ret);
            return;
        }
        Queue<Num> q = new LinkedList<>();
        q.add(new Num(N, 0));
        while(!q.isEmpty()) {
            int c = q.size();
            // 방문 체크
            boolean[] visited = new boolean[(int)Math.pow(10, s)];
            for(int k = 0; k < c; k++) {
                Num cur = q.poll();
                if (cur.c == K) {
                    ret = Math.max(ret, Integer.parseInt(cur.num));
                    continue;
                }
                boolean chk = false;
                for (int i = 0; i < s - 1; i++) {
                    for (int j = i + 1; j < s; j++) {
                        // 크거나 같을 때만 바꾸려고했음
                        if ((i == 0 && cur.num.charAt(j) == '0') || (cur.num.charAt(i) > cur.num.charAt(j))) continue;
                        String next = swap(cur.num, i, j);
                        if (visited[Integer.parseInt(next)]) continue;
                        visited[Integer.parseInt(next)] = true;
                        q.add(new Num(next, cur.c + 1));
                        chk = true;
                    }
                }
                // 바꿀 수가 없으면 뒤에꺼 두개 서로 바꿈
                if (!chk) q.add(new Num(swap(cur.num, s - 2, s - 1), cur.c + 1));
            }
        }
        System.out.println(ret);
    }

    public static String swap(String s, int i, int j) {
        // 바꾸기
        return s.substring(0, i) + s.charAt(j) + s.substring(i + 1, j) + s.charAt(i) + s.substring(j + 1);
    }
}