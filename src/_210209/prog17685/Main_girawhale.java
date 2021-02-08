package _210209.prog17685;

import java.util.Arrays;

public class Main_girawhale {
    public int solution(String[] words) {
        int ans = 0;

        Arrays.sort(words); // 일단 정렬 aa, aab, aaa => aa, aaa, aab
        for (int i = 0; i < words.length; i++) {
            // 양 끝에는 앞뒤가 없어서 0으로
            int prev = i == 0 ? 0 : typingCnt(words[i], words[i - 1]);
            int next = i == words.length - 1 ? 0 : typingCnt(words[i], words[i + 1]);

            ans += Math.max(prev, next); // 앞뒤 다 비교해서 더 많이 타이핑 해야되는만큼 타이핑
        }
        return ans;
    }

    public int typingCnt(String base, String compare) { // 나, 비교군
        int len = Math.min(base.length(), compare.length());
        for (int i = 0; i < len; i++) {
            if (base.charAt(i) != compare.charAt(i))
                return i + 1; // i번째에서 다르니까 i + 1번 타이핑하면 달라진다! i + 1 반환
        }
        return base.length() > compare.length() ? len + 1 : len; // 내가 더 길면 비교하는 애 길이보다 1개 더 입력해야됨. 아니면 내거 다 입력.
    }
}
