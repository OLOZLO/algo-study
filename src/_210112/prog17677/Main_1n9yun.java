package _210112.prog17677;

import java.util.HashMap;
import java.util.Map;

public class Main_1n9yun {
    public int solution(String str1, String str2) {
        Map<String, Integer> sub1 = new HashMap<>();
        Map<String, Integer> sub2 = new HashMap<>();

        for(int i=0;i<str1.length()-1;i++){
            String sub = toValidItem(str1.substring(i, i+2));
            if(sub == null) continue;

            if(sub1.containsKey(sub)) sub1.put(sub, sub1.get(sub) + 1);
            else sub1.put(sub, 1);
        }
        for(int i=0;i<str2.length()-1;i++){
            String sub = toValidItem(str2.substring(i, i+2));
            if(sub == null) continue;

            if(sub2.containsKey(sub)) sub2.put(sub, sub2.get(sub) + 1);
            else sub2.put(sub, 1);
        }

        double inter = 0;
        double union = 0;

        for(String key : sub1.keySet()){
            if(!sub2.containsKey(key)){
                union += sub1.get(key);
            }else {
                inter += Math.min(sub1.get(key), sub2.get(key));
                union += Math.max(sub1.get(key), sub2.get(key));

                sub2.remove(key);
            }
        }
        for(String key : sub2.keySet()) union += sub2.get(key);

        return union == 0 ? 65536 : (int)(inter / union * 65536);
    }

    String toValidItem(String s){
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(!(('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z'))) return null;
        }
        return s.toLowerCase();
    }
}
