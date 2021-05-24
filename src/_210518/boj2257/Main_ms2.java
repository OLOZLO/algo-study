package _210518.boj2257;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/*
 *   1. 결과 : 맞았습니다.
 *   2. 시간복잡도 : O(N) (N: 화학식 길이)
 *       - 이유 : 순회 한 번으로 끝남. 스택의 크기는 커봤자 2로 유지됨.
 *   3. 접근 방식
 *       - 스택 한 개를 사용. 스택의 탑에는 해당 원자까지의 중간 누적값이 위치.
 *       - ( : 부분식을 새로 계산하기 위해, 0을 푸시함. 이후 초기 0값인 top에 계산을 실행하게 된다.
 *       - ) : 가장 최근까지 진행한 식의 최종 값을, 이전 값에 누적시킨다.
 *           - 예를 들어, "CO(O2)"는 "CO"까지 진행했을 시 top에 28이 위치함.
 *           - "CO(O"까지 진행했을 시 스택은 [28, 16]. top은 16이 위치.
 *           - "CO(O2"까지 진행했을 시 스택은 [28, 32]. top에 32가 위치.
 *           - "CO(O2)"까지 진행했을 시 스택은 [60]. top에 60이 위치. 즉, 가장 최근까지 진행한 식의 최종 값 32를 이전 값 28에 누적시킨다는 말.
 *       - 숫자 : 최근까지 누적된 값(top)에 원자의 개수를 적용시킨다.
 *           - 예를 들어, "CO2"를 O까지 진행했을 때 스택 탑에는 12+16=28이 자리한다. 이미, O를 하나 누적시켰으니 하나만 더 누적하면 됨.
 *       - 알파벳 : 최근까지 누적된 값(top)에 원자 값도 누적시킨다.
 *           - 예를 들어, "CO"는 C까지 진행했을 시 top에 12가 위치함. O까지 진행하면 top엔 26이 위치하게 됨.
 *   4. 후기
 *       - 중간식이 끝나면(계산이 끝나면) 스택의 크기는 1로 유지되서, 굳이 스택 2개를 쓸 필요가 없다는 걸 알게됨.
 *       - 원래 풀었던 방법으로 해결할 수 있는 지 더 찾아보겠음.
 */

public class Main_ms2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] arr = sc.nextLine().toCharArray();
        Deque<Integer> stack = new ArrayDeque<>();
        int before = 0; // before : 곱셈을 위해 이전 원자 질량 값을 기억하고 있어야 함.
        stack.push(0); // 초기에 0을 넣어줘, top을 사용해 식을 계산할 수 있도록 함.

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '(') { // ( 면 새로운 부분식 계산을 위해 0을 넣어주자. 식 계산을 진행하면서 이 초기값 0에 중간 질량 값들이 누적될 것임.
                stack.push(0);
            } else if (arr[i] == ')') { // ) 면 부분식 계산을 끝내고 본래의 누적값에 누적(?)시키자.
                before = stack.pop();
                stack.push(stack.pop() + before);

                // "H()3"의 경우, 빈 괄호니까 열린 괄호에서 0을 넣어 pop하면 0이 나와버리니까 잘못된 결과가 나옴. 1로 세팅해주면 3이라고 제대로 나옴.
                // 아래 주석은 위 예시와 같이 "괄호 안에 아무런 알파벳도 없는 경우도 있을 수 있다"는 문제의 조건을 고려해서 적용한건데, 없어도 맞네?
                // if (before == 0) before = 1;
            } else if (arr[i] >= '2' && arr[i] <= '9') {
                stack.push(stack.pop() + before * (arr[i] - '0' - 1)); // 숫자면 이미 하나는 더했으니 나머지 개수(arr[i]-'0'-1)만큼 곱해 누적시키자.
            } else { // 알파벳인 경우,
                // 그 다음 번에 숫자가 나올 수 있으므로, 원자 곱셈 계산을 위해 before라는 변수에 담아두고,
                if (arr[i] == 'H') before = 1;
                else if (arr[i] == 'C') before = 12;
                else before = 16;

                stack.push(stack.pop() + before); // 이 질량값도 누적시켜주자.
            }
        }

        System.out.println(stack.pop()); // 최종적으로 스택엔 1개의 요소(총 질량 값)만 남게됨.
    }
}
