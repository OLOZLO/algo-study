package _210126.prog17687;

public class Main_girawhale {
    class Solution {
        public String solution(int n, int t, int m, int p) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= t * m; i++) // m명이 존재하고 t번을 말해야하기 때문에 최대 t*m번 말해야하므로 그만큼 길이를 늘림
                sb.append(Integer.toString(i, n)); // Integer.toString(i, n)을 통해 i를 n진수의 String으로 변경
//                sb.append(toString(i, n)); // ver2


            StringBuilder ans = new StringBuilder();
            for (int i = p - 1; ans.length() < t; i += m) // m번 간격으로 t번 말해야하기 때문에 m단위로 건너 뛰는데 t번 limit
                ans.append(sb.charAt(i));

            return ans.toString().toUpperCase(); // toString은 소문자를 반환하기 때문에 대문자로 변경
        }
    }

    //ver2. toString(int i, int radix) 재귀로 직접 만들어보기
    char[] radix = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    String toString(int i, int n) {
        return (i >= n ? toString(i / n, n) : "") + radix[i % n];
    }
}
