package _210126.prog17686;

import java.util.Arrays;

public class Main_Taekyung2 {
    public String[] solution(String[] files) {
        Arrays.sort(files, (o1, o2) -> {
            if (!getHead(o1).equals(getHead(o2))) {
                return getHead(o1).compareTo(getHead(o2));
            }
            return getNumber(o1) - getNumber(o2);
        });
        return files;
    }


    public String getHead(String file) {
        return file.split("\\d+")[0].toLowerCase();
    }

    public int getNumber(String file) {
        String numberPart = file.split("\\D+")[1];
        if (numberPart.length() > 5) {
            numberPart = numberPart.substring(0, 5);
        }
        return Integer.parseInt(numberPart);
    }
}