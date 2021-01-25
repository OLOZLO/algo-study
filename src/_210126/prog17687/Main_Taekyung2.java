package _210126.prog17687;

public class Main_Taekyung2 {
    public String solution(int n, int t, int m, int p) {
        StringBuilder answer = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        int i = 1;
        sb.append(0);
        while (sb.length() < m * t) {
            sb.append(Integer.toString(i++, n));
        }
        for (int j = p - 1; j < m * (t - 1) + p; j += m) {
            answer.append(sb.charAt(j));
        }
        return answer.toString().toUpperCase();
    }
}