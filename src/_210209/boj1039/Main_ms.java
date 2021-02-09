package _210209.boj1039;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_ms {
    static int N, K;
    static boolean[][] visit = new boolean[1000001][11]; // 메모리 초과가 여기서 난거였군요,, 실수로 천만으로 잡았네요,,
    static int answer = -1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        K = sc.nextInt();

        Queue<Pair> queue = new LinkedList<>();
        visit[N][0] = true;
        queue.add(new Pair(N, 0));

        while (!queue.isEmpty()) {
            Pair now = queue.poll();

            if (now.cnt == K) { // cnt가 K가 되는 순간 최대값을 갱신시켜 나갑니다.
                answer = Math.max(answer, now.num);
                continue;
            }

            int length = String.valueOf(N).length();

            // j가 i보다 앞선 상태에서,
            for (int i = 0; i < length - 1; i++) {
                for (int j = i + 1; j < length; j++) {
                    char[] swapped = swap(now.num, i, j); // 둘을 스왑시키고, 스왑 결과를 새 배열에 담아 받습니다.
                    int num = Integer.parseInt(String.valueOf(swapped)); // 스왑 결과 배열을 정수형으로 만들고,

                    if (swapped[0] == '0' || visit[num][now.cnt + 1]) // 스왑 결과 "맨 앞자리"가 0 이거나, 해당 카운트로 이미 큐에 담긴 이력이 있다면 패스!
                        continue;

                    visit[num][now.cnt + 1] = true;
                    queue.add(new Pair(num, now.cnt + 1));      // 아니면 큐에 넣기 전 방문 처리를 하고,큐에 넣습니다.
                }
            }
        }

        System.out.println(answer);
    }

    private static class Pair {
        int num, cnt;

        public Pair(int num, int cnt) {
            this.num = num;
            this.cnt = cnt;
        }
    }

    // StringBuilder를 사용해 입력받은 숫자를 sb 객체로 만들고,
    // sb 객체로 스왑을 하고,
    // toCharArray로 새로운 char 배열을 생성한 후, 그 배열을 리턴합니다.
    // 따라서, 스왑을 다시 한 번 불러 원상태로 복구해줄 필요가 없음.
    private static char[] swap(int num, int i, int j) {
        StringBuilder sb = new StringBuilder(String.valueOf(num));

        char tmp = sb.charAt(i);
        sb.setCharAt(i, sb.charAt(j));
        sb.setCharAt(j, tmp);

        return sb.toString().toCharArray();
    }
}

