package _210223.prog42893;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main_girawhale {
    //test
    Map<String, Set<WebPage>> refer = new HashMap<>();

    Pattern urlPattern = Pattern.compile("<head>[\\s\\S]*<meta[^>]*content=\"(?<url>\\S*)\"");
    Pattern linkPattern = Pattern.compile("<a href=\"(?<link>\\S*)\"", Pattern.MULTILINE);
    String word;

    public int solution(String word, String[] pages) {
        this.word = word.toLowerCase();

        List<WebPage> list = IntStream.range(0, pages.length)
                .mapToObj(i -> new WebPage(i, pages[i].toLowerCase())).sorted().collect(Collectors.toList());

        return list.get(0).idx;
    }

    class WebPage implements Comparable<WebPage> {
        int idx, score, linkCnt;
        String url;
        double linkScore;

        WebPage(int idx, String page) {
            this.idx = idx;
            Matcher urlMatcher = urlPattern.matcher(page);
            Matcher linkMatcher = linkPattern.matcher(page);

            urlMatcher.find();
            url = urlMatcher.group("url");

            while (linkMatcher.find()) {
                linkCnt++;
                refer.computeIfAbsent(linkMatcher.group("link"), s -> new HashSet<>()).add(this);
            }

            for (String s : page.split("[^a-z]+")) {
                if (word.equals(s)) score++;
            }

            linkScore = (double) score / linkCnt;
        }

        double getScore() {
            return score + (refer.containsKey(this.url) ? refer.get(this.url).stream().mapToDouble(o -> o.linkScore).sum() : 0);
        }

        @Override
        public int compareTo(WebPage o) {
            return o.getScore() != this.getScore() ? Double.compare(o.getScore(), this.getScore()) : Integer.compare(this.idx, o.idx);
        }
    }
}
