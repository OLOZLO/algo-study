package _210112.prog17679;

public class Main_ms {
    public static int solution(int m, int n, String[] board) {
        int answer = 0;

        char[][] map = new char[m][n];
        char[][] copymap = new char[m][n];

        for (int i = 0; i < m; i++)
            map[i] = board[i].toCharArray().clone();

        while (true) {
            for (int i = 0; i < m; i++)
                copymap[i] = map[i].clone();

            boolean exitflag = true;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    char c = copymap[i][j];

                    if (j + 1 < n && i + 1 < m) {
                        if (c != ' ' && copymap[i][j + 1] == c && copymap[i + 1][j] == c && copymap[i + 1][j + 1] == c) {
                            exitflag = false;
                            map[i][j] = map[i][j + 1] = map[i + 1][j] = map[i + 1][j + 1] = ' ';
                        }
                    }
                }
            }

            if (exitflag) break;

            for (int j = 0; j < n; j++) {
                for (int i = m - 1; i >= 0; i--) {
                    if (map[i][j] != ' ') continue;

                    int target = i;
                    while (target >= 0 && map[target][j] == ' ') target--;

                    if (target == -1) continue;

                    map[i][j] = map[target][j];
                    map[target][j] = ' ';
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == ' ') answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
//        int m = 6;
//        int n = 6;
//        String[] board = {"TTTANT", "RRFACC", "RRRFCC", "TRRRAA", "TTMMMF", "TMMTTJ"};

        int m = 4;
        int n = 5;
        String[] board = {"CCBDE", "AAADE", "AAABF", "CCBBF"};

        solution(m, n, board);

    }
}
