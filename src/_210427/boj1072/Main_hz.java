package _210427.boj1072;

import java.util.Scanner;

public class Main_hz {

    /*
    * 1. 결과 : 성공
    * 2. 시간복잡도 : O(logX)
    * 3. 풀이
    *    : 이분탐색으로 풀어야겠다는 느낌이 왔지만 end 값을 어떤 값으로 저장해야될 지 고민이었는데,
    *      송희가 X만큼 하면 될 것 같다는 느낌이 있다고 해서 그렇게 문제를 풀긴 했는데 왜 저는 느낌이 안왔는지 모르겠습니다.
    *      승률을 구하는 과정에서 (double)(Y/X)*100 의 연산을 수행했지만
    *      double의 소수점 오차로 인해 틀리게 되는 테스트케이스 (50 29)를 발견하고 BigDemical을 사용해보려 했지만
    *      귀찮아서 지금과 같이 (long)(Y*100)/X로 수정을 하게 되었습니다.
    * */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt();
        int Y = sc.nextInt();

        long origin = ((long)Y*100)/X;
        boolean changed = false;
        int start = 1;
        int end = X;

        long result = 0;


        while(start <= end) {
            int mid = (start + end) / 2;
            long Z = ((long)(Y+mid)*100)/(X+mid);
            System.out.println(Z);

            if (Z == origin) start = mid + 1;
            else {
                changed = true;
                result = mid;
                end = mid - 1;
            }
        }

        System.out.println(changed ? result : -1);
    }
}
