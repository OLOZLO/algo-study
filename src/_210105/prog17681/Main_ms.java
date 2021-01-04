package _210105.prog17681;

class Main_ms {
    public static String[] solution(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[arr1.length];

        for (int i = 0; i < arr1.length; i++) {
            int num = arr1[i] | arr2[i];
            answer[i] = Integer.toBinaryString(num);

            answer[i] = String.format("%" + n + "s", answer[i]);
            answer[i] = answer[i].replace("1", "#");
            answer[i] = answer[i].replace("0", " ");

//            while (answer[i].length() != n)
//                answer[i] = " " + answer[i];
        }

        return answer;
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