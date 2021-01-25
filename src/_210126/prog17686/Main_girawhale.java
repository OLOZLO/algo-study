package _210126.prog17686;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main_girawhale {
    public String[] solution(String[] files) {
        // 참고 : https://girawhale.tistory.com/77 공감과 구독 눌러주세요^-^
        // (숫자가 아닌 것 : head)(숫자 : num)(뒤에 모든 문자)로 Grouping
        Pattern pattern = Pattern.compile("(?<head>\\D+)(?<num>\\d+)(.*)");

        Arrays.sort(files, (f1, f2) -> {
            Matcher m1 = pattern.matcher(f1.toLowerCase()); //String을 모두 소문자로 만들어 패턴 매칭
            Matcher m2 = pattern.matcher(f2.toLowerCase());

            m1.find(); // 가장 처음 패턴과 매칭되는 문자열 찾기
            m2.find();

            if (!m1.group("head").equals(m2.group("head"))) // (\\D+) : head 가 같은가여? 아니면 이걸로 오름차순!
                return m1.group(1).compareTo(m2.group(1)); // 이름도 가능하고 순서도 핵가넝

            int n1 = Integer.parseInt(m1.group("num")), n2 = Integer.parseInt(m2.group(2)); // (\\d+) : num 파싱
            return n1 != n2 ? Integer.compare(n1, n2) : 0; // 다르면 오름차순하고 아니면 바꾸지 마세연!
        });

        return files;
    }
}