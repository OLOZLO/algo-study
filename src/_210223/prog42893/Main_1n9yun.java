package _210223.prog42893;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main_1n9yun {
    class Page{
        int index, basic;
        ArrayList<String> externalLink;

        public Page(int index, int basic, ArrayList<String> externalLink) {
            this.index = index;
            this.basic = basic;
            this.externalLink = externalLink;
        }
    }
    public int solution(String word, String[] pages) {
        word = word.toLowerCase();
        Map<String, Page> pageMap = new HashMap<>();

        int index = 0;
        for(String page : pages){
            String[] tags = page.split("<");
            String url = "";
            int basic = 0;
            
//            중복되는 외부링크는 없나봐 Set으로 해도 List로 해도 통과되네
            ArrayList<String> externalLink = new ArrayList<>();

//            꼭 head안에 있을 필요는 없나봐? 아니면 meta가 head안에만 쓸수있는건가?
//            boolean inHead = false;
            for(String tag : tags){
                String[] line = tag.split(">");

                if(!tag.startsWith("/")){
                    String[] properties = line[0].split(" ");
//                    if(properties[0].equals("head")) inHead = true;
                    for(String property : properties){
                        if(properties[0].equals("meta") && property.startsWith("content")){
//                        if(inHead && properties[0].equals("meta") && property.startsWith("content")){
                            url = property.split("\"")[1];
                        }
                        if(properties[0].equals("a") && property.startsWith("href")){
                            externalLink.add(property.split("\"")[1]);
                        }
                    }
                }
//                else{
//                    if(line[0].contains("head")) inHead = false;
//                }

                if(line.length == 1) continue;
//                line[1] 에서 검색해야 해 이제
                String text = line[1].toLowerCase();
                for(int i=0;i<text.length();i++){
                    int idx = text.indexOf(word, i);
                    if(idx == -1) break;
                    i = idx;

//                    찾은 word의 앞 뒤가 다른 알파벳이 붙어 한 단어가 아니었을 경우를 제외하기 위해
                    if(
                            (idx - 1 < 0 || !('a' <= text.charAt(idx - 1) && text.charAt(idx - 1) <= 'z')) &&
                            (idx + word.length() >= text.length() || !('a' <= text.charAt(idx + word.length()) && text.charAt(idx + word.length()) <= 'z'))
                    ){
                        basic++;
                    }
                }
            }
            pageMap.put(url, new Page(index++, basic, externalLink));
        }

        double[] point = new double[pageMap.size()];
        for(String url : pageMap.keySet()){
            Page page = pageMap.get(url);

            point[page.index] += page.basic;
//            0으로 나눠지는 경우에 대한 정보가 없는데.. 그냥 써야되나봐 infinity로 자동으로 가장 큰 점수..
            double linkPoint = (double)page.basic / page.externalLink.size();

//            중복되는 링크가 없으니까 이거도 필요없지.. 문제엔 설명없음
//            Set<String> used = new HashSet<>();
            for(String link : page.externalLink){
                Page externalPage = pageMap.get(link);

                if(externalPage == null) continue;
//                if(externalPage == null || used.contains(link)) continue;

                point[externalPage.index] += linkPoint;
//                used.add(link);
            }
        }

        double max = -1;
        int answer = -1;
        for(int i=0;i<point.length;i++){
            if(max < point[i]) {
                max = point[i];
                answer = i;
            }
        }
        return answer;
    }
}
