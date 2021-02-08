package _210209.prog17685;

import java.util.Arrays;

public class Main_1n9yun {
    public int solution(String[] words) {
        Arrays.sort(words);

        int answer = 0;
        for(int i=0;i<words.length;i++){
            int temp = 0;
//            한번 달랐으면 영원히 다른거임
            boolean leftDiff = false;
            boolean rightDiff = false;
            for(int j=0;j<words[i].length();j++){
//                양 옆 중 가장 멀리까지 같았던 j
                if(i > 0 && !leftDiff) {
                    if(words[i-1].length() > j){
                        if(words[i-1].charAt(j) == words[i].charAt(j)) temp = Math.max(temp, j+1);
                        else leftDiff = true;
                    }
                }
                if(i < words.length - 1 && !rightDiff){
                    if(words[i+1].length() > j){
                        if(words[i].charAt(j) == words[i+1].charAt(j)) temp = Math.max(temp, j+1);
                        else rightDiff = true;
                    }
                }
            }
            temp = Math.min(temp + 1, words[i].length());
            answer += temp;
        }
        return answer;
    }

//    class Trie {
//        char c;
//        int count;
//        Trie[] next;
//
//        public Trie(char c, int count) {
//            this.c = c;
//            this.count = count;
//            this.next = new Trie[26];
//        }
//
//        public void increase(){
//            this.count++;
//        }
//    }
//
//    public int solution(String[] words) {
//        Trie[] trie = new Trie[26];
//
//        for(String word : words){
//            Trie[] temp = trie;
//            for(int i=0;i<word.length();i++){
//                char c = word.charAt(i);
//                int cIdx = c - 'a';
//
//                if(temp[cIdx] == null){
//                    temp[cIdx] = new Trie(c, 1);
//                }else {
//                    temp[cIdx].increase();
//                }
//                temp = temp[cIdx].next;
//            }
//            temp = trie;
//        }
//
//        int answer = 0;
//        for(String word : words){
//            Trie[] temp = trie;
//            for(int i=0;i<word.length();i++){
//                int cIdx = word.charAt(i) - 'a';
//                answer++;
//                if(temp[cIdx].count == 1) break;
//                else temp = temp[cIdx].next;
//            }
//            temp = trie;
//        }
//        return answer;
//    }

//    class Trie {
//        int count;
//        HashMap<Character, Trie> next;
//
//        public Trie(int count) {
//            this.count = count;
//            this.next = new HashMap<>();
//        }
//
//        public void increase() {
//            this.count++;
//        }
//    }
//
//
//    public int solution(String[] words) {
//        Trie trie = new Trie(0);
//
//        for(String word : words){
//            Trie temp = trie;
//            for(int i=0;i<word.length();i++){
//                char c = word.charAt(i);
//
//                if(!temp.next.containsKey(c)){
//                    temp.next.put(c, new Trie(1));
//                }else {
//                    temp.next.get(c).increase();
//                }
//                temp = temp.next.get(c);
//            }
//            temp = trie;
//        }
//
//        int answer = 0;
//        for(String word : words){
//            Trie temp = trie;
//            for(int i=0;i<word.length();i++){
//                char c = word.charAt(i);
//                answer++;
//                if(temp.next.get(c).count == 1) break;
//                else temp = temp.next.get(c);
//            }
//            temp = trie;
//        }
//        return answer;
//    }
}
