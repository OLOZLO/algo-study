package hash.prog42577;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main_girawhale {
    public boolean solution(String[] phone_book) {
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(phone_book));

        for (String phone : phone_book)
            for (int i = 1; i < phone.length(); i++)
                if(set.contains(phone.substring(0, i)))
                    return false;

        return true;
    }
}
