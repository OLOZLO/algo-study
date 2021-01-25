package _210126.prog17687;

public class Main_girawhale {
    StringBuilder sb = new StringBuilder();
    char[] radix = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    //ver2. toString(int i, int radix) 재귀로 직접 만들어보기
    void toString(int i, int n) {
        if(i >= n) toString(i / n, n);
        sb.append(radix[i % n]);
    }

    // test 결과 그냥 Integer.toString(i, radix)와 재귀가 거의 유사. time: 1 ≒ 2 < 3(얘는 한 2배정도 느림)
    // 그냥 있는거 쓰자!
    class Solution {
        public String solution(int n, int t, int m, int p) {
            for (int i = 0; i <= t * m; i++) {// m명이 존재하고 t번을 말해야하기 때문에 최대 t*m번 말해야하므로 그만큼 길이를 늘림
                sb.append(Integer.toString(i, n)); // Integer.toString(i, n)을 통해 i를 n진수의 String으로 변경
//                toString(i, n); // ver2

                /* ver 3 stack으로 n진수 구현하기
                Deque<Integer> stack = new ArrayDeque<>();
                stack.push(i);

                while (!stack.isEmpty()) {
                    int num = stack.pop();
                    if (num >= n) {
                        stack.push(num % n);
                        stack.push(num / n);
                    } else sb.append(radix[num % n]);
                }
                 */
            }

            StringBuilder ans = new StringBuilder();
            for (int i = p - 1; ans.length() < t; i += m) // m번 간격으로 t번 말해야하기 때문에 m단위로 건너 뛰는데 t번 limit
                ans.append(sb.charAt(i));

            return ans.toString().toUpperCase(); // toString은 소문자를 반환하기 때문에 대문자로 변경
        }
    }

}
