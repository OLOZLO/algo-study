package _210105.prog17681;

class Main_jyj {
public String[] solution(int n, int[] arr1, int[] arr2) {
        String[] result = new String[n];
        char[][] map = new char[n][n];
        
        
        for (int i = 0; i < n; i++) {
			String str = Integer.toBinaryString(arr1[i]);

			for (int j = 0; j < str.length(); j++) {

				if (str.charAt(str.length() - 1 - j) == '1') {
					map[i][n - 1 - j] = '#';
				}
				else {
					map[i][n - 1 - j] = ' ';
				}

			}
		}
        
        for (int i = 0; i < n; i++) {
			String str = Integer.toBinaryString(arr2[i]);

			for (int j = 0; j < str.length(); j++) {

				if (str.charAt(str.length() - 1 - j) == '1') {
					map[i][n - 1 - j] = '#';
				}
                else if(str.charAt(str.length() - 1 - j) == '0' && map[i][n - 1 - j] != '#'){
                	map[i][n - 1 - j] = ' ';
                }

			}
		}
		for (int i = 0; i < n; i++) {
			String ans = "";
			for (int j = 0; j < n; j++) {
				ans += map[i][j];
			}
            ans = ans.replaceAll("\u0000"," ");
			result[i] = ans;
		}
        
        return result;
    }

    public static void main(String[] args) {
//        int n = 5;
//        int[] arr1 = {9, 20, 28, 18, 11};
//        int[] arr2 = {30, 1, 21, 17, 28};

        int n = 6;
        int[] arr1 = {46, 33, 33, 22, 31, 50};
        int[] arr2 = {27, 56, 19, 14, 14, 10};

        solution(n, arr1, arr2);
    }
}