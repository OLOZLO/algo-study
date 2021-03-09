package _210309.prog60057;

// https://ddb8036631.github.io/알고리즘%20풀이/프로그래머스_L2_문자열-압축/
public class Main_ms {
    public static int solution(String s) {
        int answer = s.length();

        // [1, s의 길이] 범위 각각 잘라서 압축을 진행해보자.
        for (int delta = 1; delta < s.length(); delta++) {
            StringBuffer sb = new StringBuffer(); // 빈 StringBuilder 객체(="") 에 문자열을 갖다 붙일 예정.

            int start = 0, end = 0 + delta; // delta 크기만큼 건너뛰면서 문자열을 자름.

            while (start != end && end <= s.length()) {
                String splited = s.substring(start, end);

                // 처음이라 sb 문자열이 아직 비어있다면, 문제에서 반복되지 않아 한 번 나타난 경우는 1을 생략하므로, 잘린 문자열을 그냥 붙인다.
                if (sb.length() == 0) {
                    sb.append(splited);
                }

                // 가장 최근에 붙인 문자열을 잘린 문자열과 비교한다. 이 때, 둘이 같다면 압축을 진행해 개수를 증가시킵니다.
                else {
                    String prev = sb.substring(sb.length() - delta, sb.length()); // StringBuilder 맨 뒤에 붙은 문자열을 가져오자.

                    // 잘린 문자열(splited)와 이전 문자열(prev)가 같으면, 압축된 개수가 있는지 확인하고 압축을 진행하자.
                    // 예를 들어, sb = ~~~~~10ab 로 끝난다면, 맨 뒤에 계수 10을 뽑아내는 작업을 아래에서 진행한다.
                    if (prev.length() != 0 && prev.equals(splited)) {
                        int countIndexEnd = sb.length() - delta - 1;

                        if (countIndexEnd >= 0) {
                            // 반복되는 문자열의 앞에 계수가 있으면?
                            if (Character.isDigit(sb.charAt(countIndexEnd))) {
                                int countIndexStart = countIndexEnd - 1;

                                while (countIndexStart >= 0 && Character.isDigit(sb.charAt(countIndexStart)))
                                    countIndexStart--;
                                countIndexStart++;

                                // 위에서 countIndexStart와 countIndexEnd 를 구해서, 그 범위까지의 문자들을 이으면 계수가 나옴.
                                int count = Integer.parseInt(sb.substring(countIndexStart, countIndexEnd + 1));

                                // 위에서 구한 계수에 +1 시켜줌.
                                sb.replace(countIndexStart, countIndexEnd + 1, Integer.toString(count + 1));
                            }

                            // sb에 반복되는 문자열이 하나만 존재하고,
                            // 그 문자열의 앞에 계수가 없으면? 2를 적어줌.
                            else {
                                sb.insert(countIndexEnd + 1, "2");
                            }
                        }

                        // StringBuilder 객체에 처음 붙어진 문자열의 계수가 없으면 2를 적어줌.
                        else {
                            sb.insert(0, "2");
                        }
                    }

                    // 잘린 문자열(splited)와 이전 문자열(prev)가 다르면, 압축이 안되므로 맨 뒤에 새로 붙이자.
                    else {
                        sb.append(splited);
                    }
                }

                // start와 end 재조정하고 위를 반복.
                start = end;
                end = ((start + delta) > s.length()) ? s.length() : start + delta;
            }

            // StringBuilder에는 1~s.length()개 단위로 잘라 압축한 문자열이 담김. 그 길이의 최소를 answer에 담자.
            answer = Math.min(answer, sb.length());
        }

        return answer;
    }

    public static void main(String[] args) {
        solution("aabbaccc");
//        solution("ababcdcdababcdcd");
//        solution("abcabcdede");
//        solution("abcabcabcabcdededededede");
//        solution("xababcdcdababcdcd");
//        solution("xxxxxxxxxxyyy");
    }
}
