package _210518.boj1599;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(NlogN)
 *               문자열 replace = N
 *               String 정렬 = 50(길이) * NlogN
 *
 *               => O(NlogN)
 *
 * 3. 접근 방식
 *      k는 c의 위치에 있으므로 k를 c로 변경
 *
 *      ng는 따로 처리하기 위해 N으로 변경하고
 *      getChar 메소드를 통해 n보다 0.5 큰 값으로 변환해 n과 o사이에 있을 수 있도록 함
 *
 */
public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        // 정렬하기 편하도록 변경
        String[] arr = IntStream.range(0, N)
                .mapToObj(i -> sc.next().replace('k', 'c').replace("ng", "N"))
                .toArray(String[]::new);

        Arrays.sort(arr, (s1, s2) -> {
            int len1 = s1.length(), len2 = s2.length();

            for (int i = 0; i < Math.min(len1, len2); i++) {
                double ch1 = getChar(s1.charAt(i)), ch2 = getChar(s2.charAt(i));
                if (ch1 != ch2)
                    return Double.compare(ch1, ch2); // 아스키코드가 더 작은게 앞
            }

            return len1 - len2; // 끝까지 같으면 길이 짧은 애 먼저
        });

        Arrays.stream(arr)
                .map(s -> s.replace('c', 'k').replace("N", "ng")) // 원복
                .forEach(System.out::println);

    }

    static double getChar(char ch) {
        return ch == 'N' ? 'n' + 0.5 : ch; // 'N'은 ng이므로 둘 사이에 존재할 수 있게 0.5 값으로, 나머지는 그냥 씀
    }
}
