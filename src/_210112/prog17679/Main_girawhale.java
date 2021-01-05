package _210112.prog17679;

public class Main_girawhale {
    public int solution(int m, int n, String[] board) {
        char[][] blocks = new char[m][];
        for (int i = 0; i < m; i++)
            blocks[i] = board[i].toCharArray();

        int answer = 0;

        while (true) {
            char[][] nextBlocks = new char[m][];
            for (int i = 0; i < m; i++)
                nextBlocks[i] = blocks[i].clone();

            boolean ck = false;

            for (int i = 0; i < m - 1; i++) {
                for (int j = 0; j < n - 1; j++) {
                    if (blocks[i][j] == '.')
                        continue;

                    if (blocks[i][j] == blocks[i + 1][j] && blocks[i][j] == blocks[i][j + 1] && blocks[i][j] == blocks[i + 1][j + 1]) {
                        nextBlocks[i][j] = nextBlocks[i + 1][j] = nextBlocks[i][j + 1] = nextBlocks[i + 1][j + 1] = '.';
                        ck = true;
                    }
                }
            }
            if (!ck)
                break;

            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    if (blocks[i][j] != nextBlocks[i][j])
                        answer++;


            for (int i = 0; i < n; i++) {
                int blank = m - 1;

                while (true) {
                    while (blank >= 0 && nextBlocks[blank][i] != '.')
                        blank--;

                    int block = blank;
                    while (block >= 0 && nextBlocks[block][i] == '.')
                        block--;

                    if (block < 0)
                        break;

                    nextBlocks[blank][i] = nextBlocks[block][i];
                    nextBlocks[block][i] = '.';
                }
            }
            blocks = nextBlocks;

        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(new Main_girawhale().solution(4, 5, new String[]{"CCBDE", "AAADE", "AAABF", "CCBBF"}));
    }
}
