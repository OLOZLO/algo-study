package _210126.prog17686;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main_girawhale {
    class File implements Comparable<File> {
        String fileName, head;
        int num, inNum;

        public File(String fileName, String head, String num, int inNum) {
            this.fileName = fileName;
            this.head = head.toLowerCase();
            this.num = Integer.parseInt(num);
            this.inNum = inNum;
        }

        @Override
        public int compareTo(File f) {
            if (!this.head.equals(f.head))
                return this.head.compareTo(f.head);

            return this.num != f.num ? Integer.compare(this.num, f.num) : Integer.compare(this.inNum, f.inNum);
        }
    }

    public String[] solution(String[] files) {
        List<File> fileList = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\D+)([0-9]+)(.*)");
        for (int i = 0; i < files.length; i++) {
            Matcher matcher = pattern.matcher(files[i]);
            matcher.find();

            fileList.add(new File(files[i], matcher.group(1), matcher.group(2), i));
        }
        Collections.sort(fileList);

        return fileList.stream().map(f -> f.fileName).toArray(String[]::new);
    }
}