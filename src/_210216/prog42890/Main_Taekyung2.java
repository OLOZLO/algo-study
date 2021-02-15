package _210216.prog42890;

import java.util.*;

public class Main_Taekyung2 {
    // 예전에 풀었길래 그대로 했음
    // 무성이가 주석 잘 달아놨음
    static ArrayList<Integer> ans = new ArrayList<>();
    public int solution(String[][] relation) {
        int n = relation.length, m = relation[0].length;
        for(int i = 1; i < (1 << m); i++) {
            HashSet<String> s = new HashSet<>();
            for (String[] strings : relation) {
                String now = "";
                for (int k = 0; k < m; k++)
                    if ((i & (1 << k)) != 0) now += strings[k];
                s.add(now);
            }
            if(s.size() == n && chk(i)) ans.add(i);
        }
        return ans.size();
    }

    static boolean chk(int now) {
        for (Integer n : ans) if ((now & n) == n) return false;
        return true;
    }
}