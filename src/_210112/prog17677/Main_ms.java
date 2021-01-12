package _210112.prog17677;

import java.util.ArrayList;
import java.util.List;

public class Main_ms {
    public static int solution(String str1, String str2) {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> intersection = new ArrayList<>();
        List<String> union = new ArrayList<>();

        str1 = str1.toUpperCase();
        str2 = str2.toUpperCase();

        for (int i = 0; i < str1.length() - 1; i++) {
            char c1 = str1.charAt(i);
            char c2 = str1.charAt(i + 1);

            if (c1 >= 'A' && c1 <= 'Z' && c2 >= 'A' && c2 <= 'Z') {
                list1.add(c1 + "" + c2);
            }
        }

        for (int i = 0; i < str2.length() - 1; i++) {
            char c1 = str2.charAt(i);
            char c2 = str2.charAt(i + 1);

            if (c1 >= 'A' && c1 <= 'Z' && c2 >= 'A' && c2 <= 'Z') {
                list2.add(c1 + "" + c2);
            }
        }

        for (String s : list1) {
            if (list2.remove(s))
                intersection.add(s);

            union.add(s);
        }

        for (String s : list2)
            union.add(s);

        double jakard = intersection.size() == 0 && union.size() == 0 ? 1 : (double) intersection.size() / union.size();

        return (int) (jakard * 65536);
    }

    public static void main(String[] args) {
//        String str1 = "FRANCE";
//        String str2 = "french";

//        String str1 = "handshake";
//        String str2 = "shake hands";

//        String str1 = "aa1+aa2";
//        String str2 = "AAAA12";

        String str1 = "E=M*C^2";
        String str2 = "e=m*c^2";

        solution(str1, str2);
    }
}
