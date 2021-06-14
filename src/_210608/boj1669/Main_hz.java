package _210608.boj1669;

import java.util.Scanner;

public class Main_hz {

    /**
     * [실1] 멍멍이 쓰다듬기
     *
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(Y-X)..? 잘 모르겠습니다...
     * 3. 풀이
     *  : 첫째날과 마지막날의 키는 1cm여야 하고, 그 사이의 날은 전, 후날과 1cm 차이가 날 수 있다.
     *  날짜에 따른 키의 변화가 ^ 모양이여야 된다 생각했고, 총 N일이 걸린다 가정했을 때
     *  (1일+N일) + (2일+N-1일) + (3일+N-2일) + ... = (1+1) + (2+2) + (3+3) + ... = 키의 차이 라는 식을 세웠다.
     *  또한 i^2 들의 합으로 키의 차이가 나오지 않는 경우 부족한 키가 i+1보다 작을 경우는 하루, 클 경우는 이틀을 소요해서
     *  키를 키우는데 필요한 최소 일수를 구하였다.
     *
     * */

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int X = sc.nextInt();
        int Y = sc.nextInt();

        int result;

        // 원숭이와 멍멍이의 키가 같을 경우 키를 늘릴 필요가 없음
        if (X == Y) result = 0;
        else {
            // 총 늘려야 하는 키의 값
            int diff = Y-X;
            
            // i == 늘리는 키의 양. 1cm부터 시작하여 1cm 씩 늘려줌
            for (int i = 1; ; i++) {
                // 마지막 날의 키는 1cm가 되어야 하기 때문에
                // 키를 늘려주는 부분, 줄여주는 부분을 고려해서 i*2 값을 차이에서 빼줌
                if (diff >= i*2) {
                    diff -= (i*2);
                } else {
                    // 더 이상 빼줄 수 없는 경우
                    result = (i-1)*2;

                    if (diff != 0) {    // 늘려야 하는 키가 남아있을 경우
                        // 그 차이가 i보다 작으면 남은 키만큼 늘리는 하루를 추가하면 됨.
                        if (diff <= i) result += 1;
                        // 그렇지 않을 경우 하루로는 남은 키들을 늘릴 수 없음. 따라서 이틀을 소요하여 남은 키를 늘림
                        else result += 2;
                    }
                    break;
                }
            }
        }

        System.out.println(result);

    }
}
