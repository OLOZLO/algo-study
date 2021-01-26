package _210126.prog17686;

import java.util.Arrays;

public class Main_1n9yun {
    static class Part {
        int idx;
        String head;
        String number;
        String original;

        public Part(int idx, String head, String number, String original) {
            this.idx = idx;
            this.head = head;
            this.number = number;
            this.original = original;
        }
    }
    public String[] solution(String[] files) {
        Part[] parts = new Part[files.length];

        for(int i = 0; i< parts.length; i++){
            String head = "";
            int numStart = 0;
            int numEnd = 0;
            for(int j=0;j<files[i].length();j++){
                char c = files[i].charAt(j);
//                숫자가 시작되는 곳, 끝나는 곳을 찾고 시작되는곳 앞부분을 head로 지정
                if(numStart == 0 && Character.isDigit(c)) {
                    head = files[i].substring(0, j).toLowerCase();
                    numStart = j;
                }else if(numStart != 0 && !Character.isDigit(c)){
                    numEnd = j;
                    break;
                }
            }
//            위에서 찾은 시작 ~ 끝으로 최대 길이가 5인 number part를 만듬 
            if(numEnd == 0) numEnd = numStart + Math.min(files[i].length() - numStart, 5);
            parts[i] = new Part(i, head, files[i].substring(numStart, numEnd), files[i]);
        }

        Arrays.sort(parts, (s1, s2) -> {
            int equality = s1.head.compareTo(s2.head);
            if(equality > 0) return 1;
            else if(equality == 0){
                equality = Integer.compare(Integer.parseInt(s1.number), Integer.parseInt(s2.number));
                if(equality > 0) return 1;
                else if(equality == 0){
                    if(s1.idx > s2.idx) return 1;
                }
            }
            return -1;
        });

        for(int i = 0; i< parts.length; i++) files[i] = parts[i].original;

        return files;
    }
}
