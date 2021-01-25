package _210126.prog17687;

public class Main_ms {
    public static String solution(int n, int t, int m, int p) {
        String answer = "";
        int num = 0;
        int count = 1;
        boolean exit = false;

        while (!exit) {
            String converted = convert(num++, n); // 숫자를 0부터 하나씩 증가시키며 n 진법으로 변환하여 표기합니다.

            // 숫자를 진법으로 변환했더니 "110" 이라면, 1 1 0 각각을 다 돕니다.
            for (char c : converted.toCharArray()) {

                // 주어진 t개를 찾을 때까지 무한 루프를 돕니다.
                if (t == 0) {
                    exit = true;
                    break;
                }

                // https://ddb8036631.github.io/알고리즘%20풀이/프로그래머스_카카오2018_n진수게임/
                // 왜 아래 조건일 때 해당 문자가 튜브꺼가 되는지 블로그에 자세하게 써놨습니다.
                if (count % m == p || (count % m == 0 && m == p)) {
                    answer += c;
                    t--;
                }

                count++;
            }
        }

        return answer;
    }

    // 10진수를 n진수로 표기하고, 그 문자열을 리턴하는 함수입니다.
    public static String convert(int num, int radix) {
        if (num == 0) return "0";

        String converted = "";

        // 몫을 기수로 나눠 나머지를 문자열 앞에다가 계속 붙였습니다.
        // 기수가 10을 넘어가면, 10 이상의 나머지가 등장하므로, 10부터 15까지를 각각 A B C D E F 로 치환해서 저장했습니다.
        while (num > 0) {
            int quotient = num / radix;
            int remainder = num % radix;

            switch (remainder) {
                case 10:
                    converted = 'A' + converted; break;
                case 11:
                    converted = 'B' + converted; break;
                case 12:
                    converted = 'C' + converted; break;
                case 13:
                    converted = 'D' + converted; break;
                case 14:
                    converted = 'E' + converted; break;
                case 15:
                    converted = 'F' + converted; break;
                default:
                    converted = remainder + converted;
            }

            num = quotient;
        }

        return converted;
    }

    public static void main(String[] args) {
        solution(2, 4, 2, 1);
//        solution(16, 16, 2, 1);
//        solution(16, 16, 2, 2);
    }
}
