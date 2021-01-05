package hash.prog42577;

import java.util.HashSet;
import java.util.Set;

public class Main_girawhale {
    public boolean solution(String[] phone_book) {
        Set<String> set = new HashSet<>();
        for (String phone : phone_book) {
            for (int i = 1; i < phone.length(); i++) {
                set.add(phone.substring(0, i));
            }
        }

        for (String phone : phone_book)
            if (set.contains(phone))
                return false;

        return true;
    }
}
