package _210126.prog17686;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main_girawhale {
    class File implements Comparable<File> {
        String fileName, head; // 파일명, head
        int num, inNum; // 파일에 들어있던 숫자, 입력순서

        public File(String fileName, String head, String num, int inNum) {
            this.fileName = fileName;
            this.head = head.toLowerCase(); // lowerCase로 모두 맞춰줌
            this.num = Integer.parseInt(num);
            this.inNum = inNum;
        }

        @Override
        public int compareTo(File f) { // head 오름차순, num 오름차순, 입력순으로 정렬
            if (!this.head.equals(f.head))
                return this.head.compareTo(f.head);

            return this.num != f.num ?
                    Integer.compare(this.num, f.num) :
                    Integer.compare(this.inNum, f.inNum);
        }
    }

    public String[] solution(String[] files) {
        List<File> fileList = new ArrayList<>();

        // 참고 : https://girawhale.tistory.com/77 좋아요와 구독 눌러주세요^-^
        // (숫자가 아닌 것 : head)(숫자 : num)(뒤에 모든 문자)로 Grouping
        Pattern pattern = Pattern.compile("(\\D+)([0-9]+)(.*)");
        for (int i = 0; i < files.length; i++) {
            Matcher matcher = pattern.matcher(files[i]);
            matcher.find();

            fileList.add(new File(files[i], matcher.group(1), matcher.group(2), i)); // 파일명, head, num, 입력순서
        }
        Collections.sort(fileList);

        return fileList.stream().map(f -> f.fileName).toArray(String[]::new); //List to String Array
    }
}