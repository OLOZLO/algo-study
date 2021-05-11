package _210504.boj12886;

import java.util.Arrays;
import java.util.Scanner;

public class Main_Taekyung2 {

    /*
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(N ^2).. 아마도?
     * 3. 풀이
     *    - 문제에서 시키는대로 숫자 세 개를 만들어서, 노드로 만든 뒤 dfs로 탐색합니다
     *    - 정렬을 잘 안해주면 틀림
     *    - 배열 크기를 500으로 하니까 실패가 뜹니다, 중간에 500을 넘어감, 500 * 3으로 넉넉하게 잡아줬습니다
     *
     * */


    // 각각은 500인데 더하다보면 더 올라갑니다
    static int MAX = 1500;
    // 숫자 두 개 정해지면 나머지 하나는 고정이라 2개로도 가능
    static boolean[][] visited = new boolean[MAX + 1][MAX + 1];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] start = new int[3];
        int sum = 0;
        for(int i = 0; i < 3; i++) {
            start[i] = sc.nextInt();
            sum += start[i];
        }

        // 3등분 안되면 실패
        if(sum % 3 != 0) {
            System.out.println(0);
            return;
        }

        Arrays.sort(start);
        System.out.println(dfs(start) ? 1 : 0);
    }


    static boolean dfs(int[] cur) {
        // 했던 거 또 왔으면 더 안감
        if(visited[cur[0]][cur[1]])
            return false;
        // 세 개 다 같으면 트루
        if(cur[0] == cur[1] && cur[1] == cur[2])
            return true;

        visited[cur[0]][cur[1]] = true;

        // 오름차순으로 정렬해놓고 세 개 중에 두 개 뽑기
        for(int i = 0; i < 2; i++) {
            for(int j = i + 1; j < 3; j++) {
                int[] next = cur.clone();
                next[i] *= 2;
                next[j] -= cur[i];
                Arrays.sort(next);
                if(dfs(next))
                    return true;
            }
        }
        return false;
    }
}
