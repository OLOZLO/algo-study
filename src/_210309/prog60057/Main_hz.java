package _210309.prog60057;

public class Main_hz {

    public static void main(String[] args) {
        System.out.println(solution("aabbaccc"));
        System.out.println(solution("ababcdcdababcdcd"));
        System.out.println(solution("abcabcdede"));
        System.out.println(solution("abcabcabcabcdededededede"));
        System.out.println(solution("xababcdcdababcdcd"));
    }

    public static int solution(String s) {
        int answer = 0;
        String answer_str = s;

        for (int i = 1; i <= s.length()/2; i++) {   // i는 몇 개로 자를 건지 (1개 ~ 전체 길이의 절반)
            StringBuilder sb = new StringBuilder(); // i개로 자른 애들 압축해볼꺼임
            int cnt = 1;

            for (int j = 1; i*j <= s.length(); j++) { 
                // 다음으로 잘릴 문자랑 동일할 경우 카운팅
                if (i*(j+1) <= s.length()
                        && s.substring(i*(j-1), i*j).equals(s.substring(i*j, i*(j+1)))) {
                    cnt++;
                } else {    // 그렇지 않을 경우 sb에 저장
                    if (cnt == 1) {
                        sb.append(s.substring(i*(j-1), i*j));
                    } else {
                        sb.append(cnt);
                        sb.append(s.substring(i*(j-1), i*j));
                    }
                    cnt = 1;
                }
                // i개로 자를 수 없는 문자들이 남았을 경우 다 더해줍니다.
                if (i*(j+1) > s.length() && i*j < s.length()) sb.append(s.substring(i*j, s.length()));
            }
            // 길이가 더 짧을 경우 정답으로 만들어줍니다.
            if (sb.toString().length() < answer_str.length()) answer_str = sb.toString();
        }

        answer = answer_str.length();
        return answer;
    }

}
