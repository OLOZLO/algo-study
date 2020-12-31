package hash.prog42578;

import java.util.HashMap;
import java.util.Map;

public class Main_girawhale {
    public int solution(String[][] clothes) {
        Map<String, Integer> map = new HashMap<>();
        for (String[] c : clothes)
            map.put(c[1], map.getOrDefault(c[1], 0) + 1);

        int ans = 1;
        for (String key : map.keySet())
            ans *= map.get(key) + 1;

        return ans - 1;
    }
}
