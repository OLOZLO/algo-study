package _210216.prog42889;

import java.util.*;

public class Main_hz {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(5, new int[]{2, 1, 2, 6, 2, 4, 3, 3})));
        System.out.println(Arrays.toString(solution(4, new int[]{4, 4, 4, 4, 4})));
    }

    static class Each {
        int idx;
        float frate;

        Each(int idx, float frate) {
            this.idx = idx;
            this.frate = frate;
        }
    }

    public static int[] solution(int N, int[] stages) {
        int[] answer = new int[N];
        int[][] user = new int[N+2][2]; // [N][0] : N번 스테이지까지 클리어한 플레이어의 수
                                        // [N][1] : N번 스테이지에 아직 도전중인 플레이어의 수

        for (int i = 0; i < stages.length; i++) {
            user[stages[i]][1]++;

            for (int j = 1; j <= stages[i]; j++) {
                user[j][0]++;
            }
        }

        List<Each> list = new ArrayList<>();    // 스테이지 번호와, 해당 스테이지의 실패율을 저장하는 리스트
        for (int i = 1; i <= N; i++) {
            if (user[i][0] != 0)    // 스테이지에 도달한 유저가 있는 경우 실패율 저장
                list.add(new Each(i, ((float)user[i][1]/(float)user[i][0])));
            else    // 없는 경우 실패율 = 0
                list.add(new Each(i, 0));
        }

        Collections.sort(list, new Comparator<Each>() { // 실패율 내림차순으로 정렬
            @Override
            public int compare(Each o1, Each o2) {
                if (o1.frate == o2.frate) return Integer.compare(o1.idx, o2.idx);
                else return -Float.compare(o1.frate, o2.frate);
            }
        });

        for (int i = 0; i < list.size(); i++)   // 정답 배열에 실패율이 높은 순으로 해당 스테이지의 번호 저장
            answer[i] = list.get(i).idx;

        return answer;
    }
}
