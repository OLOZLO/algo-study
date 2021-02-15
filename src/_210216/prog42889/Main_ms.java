package _210216.prog42889;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main_ms {
    public static int[] solution(int N, int[] stages) {
        int[] answer = new int[N];
        List<Stage> list = new ArrayList<>();

        for (int stage = 1; stage <= N; stage++) {
            int notClearCnt = 0;    // 스테이지에 도달했으나 아직 클리어하지 못한 플레이어의 수
            int reachedCnt = 0;     // 스테이지에 도달한 플레이어 수

            // 스테이지 별로 stages 배열을 한 번씩 돌며 위의 두 변수를 카운팅합니다.
            for (int i = 0; i < stages.length; i++) {
                if (stages[i] >= stage)
                    reachedCnt++;
                if (stages[i] == stage)
                    notClearCnt++;
            }

            if (reachedCnt == 0)    // 분모가 0이면, 실패율을 0으로 설정.
                list.add(new Stage(stage, 0));
            else                    // 분모 0 아니면, 직접 계산해서 설정.
                list.add(new Stage(stage, (double) notClearCnt / reachedCnt));
        }

        Collections.sort(list); // 문제 정렬 기준대로 정렬.

        for (int i = 0; i < list.size(); i++) {
            answer[i] = list.get(i).stageIdx; // 스테이지 번호만 뽑아서 배열에 저장 후 리턴.
        }

        return answer;
    }

    private static class Stage implements Comparable<Stage> {
        int stageIdx;
        double failPercent;

        public Stage(int stageIdx, double failPercent) {
            this.stageIdx = stageIdx;
            this.failPercent = failPercent;
        }

        @Override
        public int compareTo(Stage o) {
            if (this.failPercent == o.failPercent) { // 실패율이 같다면 스테이지 번호 오름차순으로.
                return this.stageIdx - o.stageIdx;
            } else {                                 // 실패율이 다르다면, 실패율 내림차순으로.
                return Double.compare(o.failPercent, this.failPercent);
            }
        }
    }

    public static void main(String[] args) {
        solution(5, new int[]{2, 1, 2, 6, 2, 4, 3, 3});
//        solution(4, new int[]{4, 4, 4, 4, 4});
    }
}
