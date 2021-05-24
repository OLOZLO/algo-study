package _210518.boj2257;

import java.util.*;

/*
 *   1. 결과 : 틀렸습니다.(9%)
 *   2. 시간복잡도 : O(N^2) (N: 화학식 길이)
 *       - 이유 : 화학식을 순회하며, 매번 스택을 접근한다. 스택에 최대 N개의 문자가 들어있을 수 있기에, O(N^2)라고 판단함.
 *   3. 접근 방식
 *       - 스택 두 개를 사용.
 *           - res : 중간중간 원자 값 계산이 끝난 값들을 저장하는 데 사용.
 *           - bracket : 원자 종류, 개수들을 보관하는 데 사용.
 *       - 괄호가 열리거나 닫힐 때가 원자 계산일 필요한 시점이라고 판단함. 괄호를 만나면 bracket에 저장되어 있는 원자들 꺼내 값을 계산해 res에 넣는다.
 *   4. 후기
 *       - 반례를 못찾겠음.
 *       - 코드가 너무 난잡하다. 계속 꼬리물면서 코드를 지웠다 새로 썼다 함.
 *       - 작성하면서도 헷갈려 다른 방법을 찾고 싶었는데 생각이 안남.
 */

public class Main_ms {
    static final int H = 1, C = 12, O = 16;
    static Deque<Integer> res = new ArrayDeque<>();       // res : 중간중간 원자 값 계산이 끝난 값들을 저장하는 데 사용.
    static Deque<Character> bracket = new ArrayDeque<>(); // bracket : 원자 종류, 개수들을 보관하는 데 사용.
    static int answer;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // H()()()3와 같이, 괄호안에 아무런 알파벳이 없을 수도 있다길래, () 를 아예 지워버리고 시작했다.
        // H(2)는 가능하다고 이해해서 이건 계산 되게끔 구현했다.
        char[] str = sc.nextLine().replaceAll("\\(\\)", "").toCharArray();

        for (int i = 0; i < str.length; i++) {
            // 1. 괄호면 bracket에 담긴 원자들을 계산하는 accumulate()를 호출한다.
            if (str[i] == '(' || str[i] == ')') {
                accumulate();
            }

            // 2. 숫자면 아래와 같이 두 상황 각각 다르게 동작.
            else if (str[i] >= '2' && str[i] <= '9') {
                // 2-1. (CO)2의 경우, 2를 만날 때 bracket이 비어있음. 이 때는 (CO) 결과가 res에 들어가있기 때문에,
                if (bracket.isEmpty()) {
                    int num = res.pop();             // res에 젤 최근 껄 빼서,
                    res.push(num * ctoi(str[i])); // 곱셈을 적용시켜서 다시 넣어준다.
                }

                // 2-2. bracket에 뭐라도 들어있는 경우,
                else bracket.push(str[i]); // 괄호 닫힐 때 한 번에 계산하기 위해 걍 넣어준다.
            }

            // 3. H, C, O도 마찬가지로 걍 넣어준다.
            else if (str[i] == 'H' || str[i] == 'C' || str[i] == 'O')
                bracket.push(str[i]);
        }

        // 스택 bracket이 아직 다 제거되지 못한 경우가 있을 수 있기에, accumulate()를 마지막으로 한 번 더 호출해준다.
        accumulate();
        while (!res.isEmpty()) answer += res.pop(); // res 다 꺼내 더한 후 출력한다.
        System.out.println(answer);
    }

    static void accumulate() {
        int delta = 1;
        int before = 0;
        ArrayList<Integer> tmp = new ArrayList<>();

        // 스택 bracket을 하나씩 pop해 아래 과정을 수행한다.
        // 숫자면, 다음 번 top에 위치한 원자에 곱셈해주기 위해 delta에 값을 저장해둔다.
        // 문자면, 매칭되는 원자의 질량을 before에 담아주고 delta와의 곱셈을 계산한다. 계산된 값을 tmp 리스트에 담아둔다.
        // tmp 리스트 하나씩 꺼내 누적시켜 최종 결과를 스택 res에 담아준다.
        while (!bracket.isEmpty()) {
            char ch = bracket.pop();

            if (ch >= '2' && ch <= '9') {
                delta = ctoi(ch);
            } else {
                if (ch == 'H') before = H;
                else if (ch == 'C') before = C;
                else before = O;

                tmp.add(before * delta);
                delta = 1;
            }
        }

        int sum = 0;
        for (int num : tmp) sum += num;

        if (sum != 0) res.push(sum);
    }

    static int ctoi(char c) { return c - '0'; }
}

// (H)(O)(2)
// (H)()()()(3)
