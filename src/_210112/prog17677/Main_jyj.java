package _210112.prog17677;
import java.util.ArrayList;

public class Main_jyj {

    public int solution(String str1, String str2) {
        str1 = str1.toLowerCase();
		str2 = str2.toLowerCase();

		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> union = new ArrayList<String>();
		ArrayList<String> intersection = new ArrayList<String>();
        
        int answer = 0;

		for (int i = 0; i < str1.length() - 1; i++) {

			char first_char = str1.charAt(i);
			char second_char = str1.charAt(i + 1);

			if (first_char >= 'a' && first_char <= 'z' && second_char >= 'a' && second_char <= 'z') {
				list1.add(str1.substring(i, i + 2));
			}

		}

		for (int i = 0; i < str2.length() - 1; i++) {

			char first_char = str2.charAt(i);
			char second_char = str2.charAt(i + 1);

			if (first_char >= 'a' && first_char <= 'z' && second_char >= 'a' && second_char <= 'z') {
				list2.add(str2.substring(i, i + 2));
			}

		}

		for (String str : list1) {
			if (list2.remove(str)) {
				intersection.add(str);
			}
			union.add(str);
		}
		
		for (String str : list2) {
			union.add(str);
		}
		
		double jakard = 0;
		
		if(union.size() == 0) {
			jakard = 1;
		}
		else {
			jakard = (double)intersection.size() / (double) union.size();
		}
		
		answer = (int)(jakard * 65536);
        return answer;
    }

}
