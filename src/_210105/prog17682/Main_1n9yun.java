package _210105.prog17682;

public class Main_1n9yun {
    public static void main(String[] args) {
        String[] dartResult = {"1S2D*3T", "1D2S#10S", "1D2S0T", "1S*2T*3S", "1D#2S*3S", "1T2D3D#", "1D2S3T*"};

        for(String s : dartResult) System.out.println(solution(s));
    }
    static int solution(String dartResult) {
        int answer = 0;
        int prev = 0;
        int current = 0;
        StringBuilder now = new StringBuilder();
        for(int i=0;i<dartResult.length();i++){
            char c = dartResult.charAt(i);

            if('0' <= c && c <= '9'){
                now.append(c);
            }else if(c == 'S' || c == 'D' || c == 'T'){
                answer += prev;
                prev = current;
                current = Integer.parseInt(now.toString());
                now = new StringBuilder();
                if(c == 'D') current *= current;
                else if(c == 'T') current *= current * current;
            }else if(c == '*' || c == '#'){
                if(c == '*'){
                    current += current;
                    prev += prev;
                }else{
                    current *= -1;
                }
            }
        }
        return answer + prev + current;
    }
}
