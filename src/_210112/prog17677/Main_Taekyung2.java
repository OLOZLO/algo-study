package _210112.prog17677;

import java.util.ArrayList;

public class Main_Taekyung2 {

public int solution(String str1, String str2) {
        str1 = str1.toUpperCase();
        str2 = str2.toUpperCase();
        int n1 = str1.length();
        int n2 = str2.length();
        ArrayList<String> s = new ArrayList<>();
        ArrayList<String> inter = new ArrayList<>();
        ArrayList<String> union = new ArrayList<>();

    for(int i = 0; i < n1 - 1; i++) {
            String tmp = str1.substring(i, i + 2);
            if(!Character.isAlphabetic(str1.charAt(i)) || !Character.isAlphabetic(str1.charAt(i + 1))) continue;
            s.add(tmp);
            union.add(tmp);
        }

        for(int i = 0; i < n2 - 1; i++) {
            String tmp = str2.substring(i, i + 2);
            if (!Character.isAlphabetic(str2.charAt(i)) || !Character.isAlphabetic(str2.charAt(i + 1))) continue;
            if(s.contains(tmp)) {
                s.remove(tmp);
                inter.add(tmp);
            }
            else {
                union.add(tmp);
            }
        }
        if(union.size() == 0) return 65536;
        return (int)((double) inter.size() / union.size() * 65536);
    }
}
