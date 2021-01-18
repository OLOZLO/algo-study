package _210119.prog17683;

import java.util.Arrays;

public class Main_1n9yun {
    class Music{
        int idx;
        String name;
        String info;

        public Music(int idx, String name, String info) {
            this.idx = idx;
            this.name = name;
            this.info = info;
        }
    }
    public String solution(String m, String[] musicinfos) {

        Music[] musics = new Music[musicinfos.length];

        for(int i=0;i<musicinfos.length;i++){
            String[] split = musicinfos[i].split(",");

//            입력의 index, 음악 이름, 재생 시간에 따른 전체 멜로디
            musics[i] = new Music(i, split[2], getFullMusic(split[0], split[1], convertEachNoteToSingleLetter(split[3])));
        }

//        첫 번째로 일치한 결과가 답이 되도록 정렬해놓고 시작할 것임
//        길이가 길다, 먼저 입력됐다 순
        Arrays.sort(musics, (o1, o2) -> {
            if(o1.info.length() < o2.info.length()) return 1;
            else if(o1.info.length() == o2.info.length()){
                if(o1.idx > o2.idx) return 1;
            }
            return -1;
        });

//        찾고자 하는 멜로디도 #을 제거
        m = convertEachNoteToSingleLetter(m);

//        이제 KMP로 내가 들은 멜로디를 찾을거임
//        내가 들은 멜로디의 fail함수 구하기
        int[] fail = new int[m.length()];
        for(int i=1, j=0;i<m.length();i++) {
            while(j > 0 && m.charAt(i) != m.charAt(j)) {
                j = fail[j-1];
            }
            if(m.charAt(i) == m.charAt(j)) {
                fail[i] = ++j;
            }
        }

//        KMP
        for(int idx=0;idx<musics.length;idx++){
            String fullMusic = musics[idx].info;

            for(int i=0,j=0;i<fullMusic.length();i++) {
                while(j>0 && fullMusic.charAt(i) != m.charAt(j)) {
                    j = fail[j-1];
                }
                if(fullMusic.charAt(i) == m.charAt(j)) {
//                    찾아지면 바로 리턴
                    if(j == m.length()-1) return musics[idx].name;
                    else j++;
                }
            }
        }

//        못찾으면 None 리턴
        return "(None)";
    }

//    악보로부터 실제 재생 시간에 따라 연주된 음을 구한다.
    String getFullMusic(String from, String to, String info){
        String[] split = from.split(":");
        int start = stoi(split[0]) * 60 + stoi(split[1]);

        split = to.split(":");
        int end = stoi(split[0]) * 60 + stoi(split[1]);

        int diff = end - start;
        int times = diff / info.length();
        int additional = diff % info.length();

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<times;i++) sb.append(info);
        return sb.append(info.substring(0, additional)).toString();
    }

//    음 리스트에서 #이 붙은 음에 6을 더해서 한 문자로 바꾼다.
//    F까지 있으니까 6을 더한거임
    String convertEachNoteToSingleLetter(String music){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<music.length();i++){
            if(music.charAt(i) == '#') sb.setCharAt(sb.length() - 1, (char)(sb.charAt(sb.length() - 1) + 6));
            else sb.append(music.charAt(i));
        }
        return sb.toString();
    }
    int stoi(String s) { return Integer.parseInt(s); }
}
