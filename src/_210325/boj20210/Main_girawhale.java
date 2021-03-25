package _210325.boj20210;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        IntStream.range(0, N).mapToObj(i -> new File(sc.next()))
                .sorted()
                .forEach(f -> System.out.println(f.filename));
    }

    static class File implements Comparable<File> {
        String filename;
        List<Group> list;

        File(String str) {
            filename = str;
            list = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\D|\\d+");
            Matcher matcher = pattern.matcher(str);

            while (matcher.find())
                list.add(new Group(matcher.group()));
        }

        @Override
        public int compareTo(File o) {
            int thisSize = this.list.size(), oSize = o.list.size();

            for (int i = 0; i < Math.min(thisSize, oSize); i++) {
                Group g1 = this.list.get(i), g2 = o.list.get(i);

                if (g1.isDigit != g2.isDigit)
                    return g1.isDigit ? -1 : 1;

                if (g1.isDigit) {
                    if (g1.str.length() != g2.str.length())
                        return g1.str.length() - g2.str.length();

                    for (int j = 0; j < g1.str.length(); j++) {
                        if (g1.str.charAt(j) != g2.str.charAt(j))
                            return Character.compare(g1.str.charAt(j), g2.str.charAt(j));
                    }

                    if (g1.zeroCnt != g2.zeroCnt)
                        return Integer.compare(g1.zeroCnt, g2.zeroCnt);

                } else {
                    if (Character.toLowerCase(g1.ch) == Character.toLowerCase(g2.ch)) {
                        if (g1.ch != g2.ch) return Character.compare(g1.ch, g2.ch);
                    } else
                        return Character.compare(Character.toLowerCase(g1.ch), Character.toLowerCase(g2.ch));
                }

            }

            return Integer.compare(thisSize, oSize);
        }

        class Group {
            char ch;
            boolean isDigit;
            String str;
            int zeroCnt;

            Group(String str) {
                this.str = str;
                ch = str.charAt(0);
                isDigit = Character.isDigit(ch);

                if (isDigit) {
                    while (zeroCnt < str.length() && str.charAt(zeroCnt) == '0')
                        zeroCnt++;
                }
            }

        }
    }

}
