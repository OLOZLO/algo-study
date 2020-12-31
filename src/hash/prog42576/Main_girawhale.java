package hash.prog42576;

import java.util.HashMap;
import java.util.Map;

public class Main_girawhale {
    public String solution(String[] participant, String[] completion) {
        Map<String, Integer> map = new HashMap<>();
        for (String p : participant)
            map.put(p, map.getOrDefault(p, 0) + 1);

        for (String c : completion)
            map.put(c, map.get(c) - 1);

        for (String key : map.keySet())
            if (map.get(key) != 0)
                return key;
        return null;
    }
}
