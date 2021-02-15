package _210216.prog42889;

import java.util.Arrays;

public class Main_girawhale {
    public int[] solution(int N, int[] stages) {
        Stage[] arr = new Stage[N];
        for (int i = 0; i < N; i++) arr[i] = new Stage(i + 1);

        for (int stage : stages) {
            arr[Math.min(stage, N) - 1].count++;
            if (stage != N + 1) arr[stage - 1].fail++;
        }
        for (int i = N - 1; i > 0; i--)
            arr[i - 1].count += arr[i].count; // 통과한 사람들 수는 나에서 멈춘사람 + 내 앞에서까지 멈춘사람. 누적하자

        return Arrays.stream(arr).sorted().mapToInt(s -> s.num).toArray(); // 실패율 순으로 정렬해서 다시 뽑아내줌.
    }

    class Stage implements Comparable<Stage> { // 오랜만에 class 만들어 봤음미다
        int num, count, fail;

        Stage(int num) {
            this.num = num;
        }

        double failRate() {
            return count == 0 ? 0 : (double) fail / count;
        }

        @Override
        public int compareTo(Stage o) {
            return this.failRate() != o.failRate() ?
                    Double.compare(o.failRate(), this.failRate())
                    : Integer.compare(this.num, o.num);
        }
    }
}
