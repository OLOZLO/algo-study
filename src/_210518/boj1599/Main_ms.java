package _210518.boj1599;

import java.util.*;

/*
 *   1. 결과 : 맞았습니다.
 *   2. 시간복잡도 : O(N)
 *       - 이유 : 단어 최대 개수 N * 각 단어의 최대 길이 50 => O(N*50) => O(N)
 *   3. 접근 방식
 *       - 20개의 문자를 0부터 19까지 매칭시킨 새로운 배열로 정렬을 진행했다.
 */

public class Main_ms {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.nextLine();
        ArrayList<Word> list = new ArrayList<>();
        Word[] words = new Word[N];

        for (int i = 0; i < N; i++) {
            String s = sc.nextLine();
            String[] splited = new String[50];
            int target = 0;

            for (int j = 0; j < s.length(); j++) {
                // "ng"를 걸러내기 위해 입력받은 문자열의 각 문자를 순회하며, 문자를 문자열로 변환해 배열에 담아준다.
                if (j < s.length() - 1 && s.charAt(j) == 'n' && s.charAt(j + 1) == 'g') {
                    splited[target++] = "ng";
                    j++;
                } else {
                    splited[target++] = String.valueOf(s.charAt(j));
                }
            }

            int[] converted = new int[target];

            // 문자열 배열을 순회하며, 매칭되는 숫자로 변환해 그 값을 저장한다.
            // a b k ... y
            // 0 1 2 ... 19
            for (int j = 0; j < target; j++) {
                converted[j] = convert(splited[j]);
            }

            words[i] = new Word(s, converted);
            list.add(words[i]);
        }


        // 문제 규칙에 맞게 정렬 기준을 정했다.
        Collections.sort(list, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                int idx = 0;

                while (idx < o1.converted.length && idx < o2.converted.length) {
                    if (o1.converted[idx] != o2.converted[idx]) {
                        return o1.converted[idx] - o2.converted[idx];
                    }

                    idx++;
                }
                return o1.converted.length - o2.converted.length;
            }
        });

        for (Word w : list) System.out.println(w.w);
    }

    static int convert(String s) {
        switch (s) {
            case "a": return 0;
            case "b": return 1;
            case "k": return 2;
            case "d": return 3;
            case "e": return 4;
            case "g": return 5;
            case "h": return 6;
            case "i": return 7;
            case "l": return 8;
            case "m": return 9;
            case "n": return 10;
            case "ng": return 11;
            case "o": return 12;
            case "p": return 13;
            case "r": return 14;
            case "s": return 15;
            case "t": return 16;
            case "u": return 17;
            case "w": return 18;
            case "y": return 19;
        }

        return 0;
    }

    static class Word {
        String w;
        int[] converted;

        public Word(String w, int[] converted) {
            this.w = w;
            this.converted = converted;
        }
    }
}
