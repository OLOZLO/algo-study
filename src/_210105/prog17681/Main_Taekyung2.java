package _210105.prog17681;

public class Main_Taekyung2 {
    public String[] solution(int n, int[] arr1, int[] arr2) {
        String[] arr = new String[n];
        for(int i = 0; i < n; i++) {
            String str1 = Integer.toBinaryString(arr1[i]);
            String str2 = Integer.toBinaryString(arr2[i]);
            while(str1.length() < n) str1 = "0" + str1;
            while(str2.length() < n) str2 = "0" + str2;
            arr[i] = "";
            for(int j = 0; j < n; j++) {
                if(str1.charAt(j) == '1' || str2.charAt(j) == '1')
                    arr[i] += "#";
                else if(str1.charAt(j) == '0' && str2.charAt(j) == '0')
                    arr[i] += " ";
            }
        }
        return arr;
    }
}