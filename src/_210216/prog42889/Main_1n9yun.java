package _210216.prog42889;

import java.util.stream.IntStream;

public class Main_1n9yun {
    public int[] solution(int N, int[] stages) {
        int[] completed = new int[N+2];
        int[] incompleted = new int[N+2];
        for(int stage : stages) {
//            stage까지 클리어한 사람 수
            completed[stage]++;
//            stage까지 왔으나 클리어하지 못한 사람 수
            incompleted[stage]++;
        }
//        각 스테이지 별 클리어한 사람 수
        for(int i=completed.length-1;i>0;i--) completed[i-1] += completed[i];

//        닫힌 범위의 int stream
        return IntStream.rangeClosed(1, N)
//                wrap
                .boxed()
//                object일 때만 sorting 가능(그래서 boxed)
                .sorted((o1, o2) -> -1 * Double.compare(
                        completed[o1] == 0 ? 0 : (double)incompleted[o1] / completed[o1],
                        completed[o2] == 0 ? 0 : (double)incompleted[o2] / completed[o2]
                        )
                )
//                각 요소를 int로 매핑 (Integer to int)
                .mapToInt(Integer::intValue)
//                배열로 리턴
                .toArray();
    }
}
