package _210216.prog42889;

import java.util.ArrayList;
import java.util.List;

public class Main_Taekyung2 {
    // 실패율, 스테이지 넘버
    static class P {
        double fail;
        int idx;
        P(double fail, int idx) {
            this.fail = fail;
            this.idx = idx;
        }
    }
    public int[] solution(int N, int[] stages) {
        int[] cnt = new int[N + 2], sum = new int[N + 2];
        // 각 스테이지 몇 명 있는지
        for(int s : stages) cnt[s]++;
        // 다 깬 사람
        sum[N + 1] = cnt[N + 1];
        // 스테이지에 도달한 사람, 위에서 부터 더해서 내려온다
        for(int i = N; i >= 1; i--)
            sum[i] = cnt[i] + sum[i + 1];
        List<P> list = new ArrayList<>();
        for(int i = 1; i <= N; i++)
            if(sum[i] == 0) list.add(new P(0, i));
            else list.add(new P((double)cnt[i] / sum[i], i));
        // 시키는 대로 정렬
        list.sort((p1, p2) -> {
            if (p1.fail == p2.fail) return p1.idx - p2.idx;
            // 그냥 (int) p2.fail - p1.fail 하니까 틀림
            return Double.compare(p2.fail, p1.fail);
        });
        int[] answer = new int[N];
        for(int i = 0; i < N; i++) answer[i] = list.get(i).idx;
        return answer;
    }
}