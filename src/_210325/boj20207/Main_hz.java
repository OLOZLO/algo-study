package _210325.boj20207;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_hz {
    public static int stoi(String s) {return Integer.parseInt(s);}
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = stoi(br.readLine());

        StringTokenizer st = null;
        int[] calendar = new int[366];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int start = stoi(st.nextToken());
            int end = stoi(st.nextToken());

            for (int j = start; j <= end; j++)
                calendar[j]++;
        }

        boolean start = false;
        int w = 0, h = 0;
        int result = 0;
        for (int i = 0; i < calendar.length; i++) {
            if (calendar[i] > 0 && !start) {
                start = true;
                w = 1;
                h = Math.max(h, calendar[i]);
            } else if (start && calendar[i] == 0) {
                result += (w*h);
                start = false;
                w = 0; h = 0;
            } else if (start && calendar[i] > 0) {
                w += 1;
                h = Math.max(h, calendar[i]);
            }
        }


        System.out.println(result);

    }

}
