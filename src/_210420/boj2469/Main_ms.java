package _210420.boj2469;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

/*
    https://ddb8036631.github.io/알고리즘%20풀이/백준_2469_사다리-타기/

    1. 결과 : 성공
    2. 시간복잡도 : O(N * K/2* 스왑비용) ? 맞는지는 모르겠습니다.
        - N(줄) 최대 1000개
            - 모든 줄에서 swap이 최대로 발생함.(가로막대는 최소 한 칸 건너뛰어 설치 가능하므로, 최악의 경우 한 줄에 K/2 개의 가로 막대가 놓일 수 있음.)
            - 따라서, k/2번의 스왑하는 비용이 발생하지 않을까 싶습니다.
    3. 후기
        - 아이디어는 블로그에 적어놨습니다.
        - 처음에 시뮬 문제라고 진짜 시뮬을 해서 시간 초과가 발생했습니다.
        - 근데, 제 시간 안에 풀 수 있는 아이디어를 찾아보니 개인적으로 생각해내기 어려웠을 것 같았습니다.
*/

public class Main_ms {
    static int K, N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        K = Integer.parseInt(br.readLine());
        N = Integer.parseInt(br.readLine());
        StringBuilder topDown = new StringBuilder();               // topDown : 사다리 위에서 아래로 이동하며, 이동된 위치를 나타냅니.
        StringBuilder bottomUp = new StringBuilder(br.readLine()); // bottomUp : 사다리 아래서 위로 이동하며, 이동된 위치를 나타냅니다.
        Deque<String> deque = new LinkedList<>();                  // bottomUp 을 완성시키기 위해 필요합니다.
        boolean isFound = false;

        for (int i = 0; i < K; i++) {
            topDown.append((char) (i + 65)); // 먼저 topDown에 초기 위치를 담습니다.
        }

        for (int i = 0; i < N; i++) {
            String command = br.readLine(); // 각 줄 정보를 받습니다.

            if (command.charAt(0) == '?') { // 맨 앞이 ? 이면 전체가 ??....? 로 이뤄졌으므로, ?줄을 찾았음을 표기합니다.
                isFound = true;
                continue;
            }

            if (isFound) {  // ? 줄을 찾은 후라면,
                deque.add(command); // 스택에 담아둡니다. 이후에 아래 코드에서 이 스택을 사용해 bottomUp을 이동시킵니다.
            } else {        // ? 줄을 찾기 전이라면,
                topDown = swap(topDown, command); // topDown을 사다리 줄 정보에 따라 위치를 이동시킵니다.
            }
        }

        // 스택에 담긴 줄 정보를 이용해 bottomUp 을 이동시킵니다.
        while (!deque.isEmpty()) {
            bottomUp = swap(bottomUp, deque.pollLast());
        }

        StringBuilder answer = new StringBuilder();

        // 완성된 최종 문자열 topDown을 문자열 bottomUp으로 만드는 과정입니다.
        // 같은 위치에서 두 문자가 같다면 자리 변경이 필요 없으므로 * 가 필요하고,
        // 두 문자가 다르다면 자리 변경이 필요하므로 - 를 설정해 이동할 수 있는 막대기를 설치해줍니다.
        for (int i = 0; i < topDown.length() - 1; i++) {
            if (topDown.charAt(i) == bottomUp.charAt(i)) answer.append('*');
            else {
                answer.append('-');
                topDown = swap(topDown, i);
            }
        }

        // 만약 위 과정을 거쳤는데도 같아지지 않는다면, 어떻게 설치해도 최종 순서를 만들 수 없으므로 x를 k-1번 출력합니다.
        // 같다면 위에서 만든 사다리 줄 정보를 출력합니다.
        System.out.println(topDown.toString().equals(bottomUp.toString()) ? answer : "x".repeat(K - 1));
    }

    static StringBuilder swap(StringBuilder sb, String command) {
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == '-') {
                sb = swap(sb, i);
            }
        }

        return sb;
    }

    static StringBuilder swap(StringBuilder sb, int x) {
        char tmp = sb.charAt(x);
        sb.setCharAt(x, sb.charAt(x + 1));
        sb.setCharAt(x + 1, tmp);

        return sb;
    }
}
