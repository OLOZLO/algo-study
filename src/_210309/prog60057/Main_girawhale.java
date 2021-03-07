package _210309.prog60057;

public class Main_girawhale {
    public int solution(String str) {
        int min = str.length();

        for (int s = 1; s <= str.length() / 2; s++) { // 최대 크기 어차피 절반넘게 해도 크기 안줄으니까 절반까지만
            String prev;
            int idx = 0, cnt = 0, len = 0;

            for (; idx + s <= str.length(); cnt = 0) {
                prev = str.substring(idx, idx + s);
                while (idx + s <= str.length() && prev.equals(str.substring(idx, idx + s))) { // 길이만큼 잘라서 앞에거랑 똑같으면
                    cnt++; // cnt 늘려주고
                    idx += s; // 인덱스 증가
                }
                len += s + (cnt == 1 ? 0 : Math.log10(cnt) + 1); // 1이면 제외하고 1넘을때 길이 더해줌
            }
            min = Math.min(len + str.length() % s, min); // 동일한 길이만큼 자르고 남은 길이만큼 더해준 애랑 비교
        }
        return min;
    }
}
