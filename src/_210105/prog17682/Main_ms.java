package _210105.prog17682;

public class Main_ms {
    public static int solution(String dartResult) {
        int answer = 0;
        int cnt = 0;
        int idx = 0;
        int[] subScore = new int[3];
        String tmp = "";
        int len = dartResult.length();

        while (idx < len) {
            char now = dartResult.charAt(idx++);

            switch (now) {
                case 'S':
                    subScore[cnt++] = (int) Math.pow(Integer.parseInt(tmp), 1);
                    tmp = "";
                    break;

                case 'D':
                    subScore[cnt++] = (int) Math.pow(Integer.parseInt(tmp), 2);
                    tmp = "";
                    break;

                case 'T':
                    subScore[cnt++] = (int) Math.pow(Integer.parseInt(tmp), 3);
                    tmp = "";
                    break;

                case '*':
                    subScore[cnt - 1] *= 2;
                    if (cnt > 1)
                        subScore[cnt - 2] *= 2;
                    break;

                case '#':
                    subScore[cnt - 1] *= -1;
                    break;

                default:
                    tmp += now;
            }
        }

        for (int i : subScore)
            answer += i;

        return answer;
    }

    public static void main(String[] args) {
        String dartResult = "1S2D*3T";
//        String dartResult = "1D2S#10S";
//        String dartResult = "1D2S0T";
//        String dartResult = "1S*2T*3S";
//        String dartResult = "1D#2S*3S";
//        String dartResult = "1T2D3D#";
//        String dartResult = "1D2S3T*";

        solution(dartResult);
    }
}
