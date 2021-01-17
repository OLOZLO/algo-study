package _210112.prog17677;

import java.util.HashMap;
import java.util.Map;

public class Main_girawhale {
    public static int MULTI = 65536;

    public int solution(String str1, String str2) {
        Map<String, Integer> map = new HashMap<>();

        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        for (int i = 0; i < str1.length() - 1; i++) {
            if (!Character.isAlphabetic(str1.charAt(i)) || !Character.isAlphabetic(str1.charAt(i + 1)))
                continue;

            String key = str1.substring(i, i + 2);
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        int interCnt = 0, unionCnt = 0;
        for (int i = 0; i < str2.length() - 1; i++) {
            if (!Character.isAlphabetic(str2.charAt(i)) || !Character.isAlphabetic(str2.charAt(i + 1)))
                continue;

            String key = str2.substring(i, i + 2);
            if (map.containsKey(key)) {
                if (map.put(key, map.get(key) - 1) == 1)
                    map.remove(key);
                interCnt++;
            }
            unionCnt++;

        }

        for (String key : map.keySet())
            unionCnt += map.get(key);

        return unionCnt == 0 ? MULTI : (int) (((double) interCnt / unionCnt) * MULTI);
    }

}
