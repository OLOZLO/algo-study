package _210223.prog42893;

import java.util.ArrayList;
import java.util.Arrays;

public class Main_ms {
    public static int solution(String word, String[] pages) {
        int answer = 0;

        // infos 배열 각 요소는 Info 객체(page별 인덱스, url, 기본 점수, 외부 링크, 링크 점수, 매칭 점수)를 갖는다.
        Info[] infos = new Info[pages.length];
        for (int i = 0; i < infos.length; i++) {
            infos[i] = new Info(i);
        }

        for (int i = 0; i < pages.length; i++) {
            String page = pages[i];

            setUrl(infos, i, page);                 // url 부분을 찾아서 설정해줌.
            setExternalLinks(infos, i, page);       // 외부 링크 부분을 찾아서 설정해줌.
            setBasicScore(infos, i, page, word);    // 기본 점수를 계산해서 설정해줌.
        }

        // 이 이중 포문은 매칭 점수를 구하기 위함.
        for (int i = 0; i < infos.length; i++) {        // i를 고정시켜 놓고,
            double total = 0;

            for (int j = 0; j < infos.length; j++) {    // j가 쭉 돌면서 아래 과정 반복.
                if (i == j) continue;

                if (infos[j].externalLinks.contains(infos[i].url)) {    // j의 외부 링크로 i가 있는 지 판단하고, 둘이 연결되있으면 (기본점수 / 외부링크 수) 값을 누적시킴.
                    total += (double) infos[j].basicScore / infos[j].externalLinkCnt;
                }
            }

            infos[i].linkScore = total;                                         // 링크 점수 저장해주고,
            infos[i].matchingScore = infos[i].basicScore + infos[i].linkScore;  // 매칭점수 = 기본점수 + 링크 점수
        }

        Arrays.sort(infos);         // 정렬 하고,

        answer = infos[0].index;    // 맨 앞에꺼 리턴시킴.

        return answer;
    }

    // setUrl : url 뽑아냄.
    static void setUrl(Info[] infos, int index, String page) {
        String search = "<meta property=\"og:url\" content=\"";
        int url_start_idx = page.indexOf(search) + search.length();
        int url_end_idx = url_start_idx + page.substring(url_start_idx).indexOf("\"/>");

        String url = page.substring(url_start_idx, url_end_idx);

        infos[index].url = url;
    }

    // setExternalLinks : <a> 태그의 url 모두 뽑아냄.
    static void setExternalLinks(Info[] infos, int index, String page) {
        ArrayList<String> externalLinks = new ArrayList<>();
        String search = "<a href=\"";
        String tmp = page;

        while (tmp.indexOf(search) != -1) {
            int a_start_idx = tmp.indexOf(search) + search.length();
            int a_end_idx = a_start_idx + tmp.substring(a_start_idx).indexOf("\">");

            String externalLink = tmp.substring(a_start_idx, a_end_idx);
            externalLinks.add(externalLink);

            tmp = tmp.substring(a_end_idx);
        }
        infos[index].externalLinks = externalLinks;
        infos[index].externalLinkCnt = externalLinks.size();
    }

    // setBasicScore : <body> 태그 내부에, word와 일치하는 단어의 개수를 다 셈.
    static void setBasicScore(Info[] infos, int index, String page, String word) {
        String search = "<body>";
        int body_start_idx = page.indexOf(search) + search.length();
        int body_end_idx = body_start_idx + page.substring(body_start_idx).indexOf("</body>");

        String body = page.substring(body_start_idx, body_end_idx).toUpperCase();
        body = body.toUpperCase();
        word = word.toUpperCase();

        int cnt = 0;
        int find = body.indexOf(word);

        while (find != -1) {
            char prev = body.charAt(find - 1);
            char next = body.charAt(find + word.length());

            // 문자 앞과 뒤를 확인해서 알파벳이 아닌 글자로 이루어져있는지를 확인하고 카운트 증가시킴.
            if (!Character.isLetter(prev) && !Character.isLetter(next)) {
                cnt++;
            }

            find = body.indexOf(word, find + 1);
        }

        infos[index].basicScore = cnt;
    }

    static class Info implements Comparable<Info> {
        String url;
        ArrayList<String> externalLinks;
        int index, basicScore, externalLinkCnt;
        double linkScore, matchingScore;

        public Info(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(Info o) {
            return this.matchingScore == o.matchingScore ? this.index - o.index : Double.compare(o.matchingScore, this.matchingScore);
        }
    }

    public static void main(String[] args) {
//        solution("blind", new String[]{"<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://a.com\"/>\n</head>  \n<body>\nBlind Lorem Blind ipsum dolor Blind test sit amet, consectetur adipiscing elit. \n<a href=\"https://b.com\"> Link to b </a>\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://b.com\"/>\n</head>  \n<body>\nSuspendisse potenti. Vivamus venenatis tellus non turpis bibendum, \n<a href=\"https://a.com\"> Link to a </a>\nblind sed congue urna varius. Suspendisse feugiat nisl ligula, quis malesuada felis hendrerit ut.\n<a href=\"https://c.com\"> Link to c </a>\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://c.com\"/>\n</head>  \n<body>\nUt condimentum urna at felis sodales rutrum. Sed dapibus cursus diam, non interdum nulla tempor nec. Phasellus rutrum enim at orci consectetu blind\n<a href=\"https://a.com\"> Link to a </a>\n</body>\n</html>"});
        solution("Muzi", new String[]{"<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://careers.kakao.com/interview/list\"/>\n</head>  \n<body>\n<a href=\"https://programmers.co.kr/learn/courses/4673\"></a>#!MuziMuzi!)jayg07con&&\n\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://www.kakaocorp.com\"/>\n</head>  \n<body>\ncon%\tmuzI92apeach&2<a href=\"https://hashcode.co.kr/tos\"></a>\n\n\t^\n</body>\n</html>"});
    }
}
