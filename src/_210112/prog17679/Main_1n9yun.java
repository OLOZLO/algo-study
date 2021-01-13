package _210112.prog17679;

public class Main_1n9yun {
    static int[][] delta = {{0,1},{1,0},{1,1}};

    public static void main(String[] args) {
        String[] board = {"CCBDE", "AAADE", "AAABF", "CCBBF"};
        System.out.println(solution(4, 5, board));
//        String[] board = {"TTTANT", "RRFACC", "RRRFCC", "TRRRAA", "TTMMMF", "TMMTTJ"};
//        System.out.println(solution(6, 6, board));
    }

    public static int solution(int m, int n, String[] board) {
        int ans = 0;

        char[][] map = new char[m][];
        for(int i=0;i<board.length;i++){
            map[i] = board[i].toCharArray();
        }

        while(true){
            boolean[][] check = new boolean[m][n];
            boolean ended = true;
            for(int i=0;i<m-1;i++){
                for(int j=0;j<n-1;j++){
                    if(map[i][j] == ' ') continue;

                    boolean flag = true;
                    for(int[] dir : delta){
                        if(map[i][j] != map[i+dir[0]][j+dir[1]]){
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        ended = false;
                        check[i][j] = true;
                        for(int[] dir : delta){
                            check[i+dir[0]][j+dir[1]] = true;
                        }
                    }
                }
            }
            if(ended) break;

//            지우기
            for(int i=0;i<m;i++){
                for(int j=0;j<n;j++){
                    if(check[i][j]) {
                        map[i][j] = ' ';
                        ans++;
                    }
                }
            }
//            내리기
            for(int i=0;i<n;i++){
                int row = m - 1;
                while(map[row][i] != ' ' && row != 0) row--;
                if(row == 0) continue;
                int target = row - 1;
                while(map[target][i] == ' ' && target != 0) target--;

                while(row > target){
                    if(target == 0 && map[target][i] == ' ') break;
                    map[row--][i] = map[target][i];
                    map[target--][i] = ' ';

                    if(target < 0) break;
                    while(map[target][i] == ' ' && target != 0) target--;
                }
            }
        }

        return ans;
    }
}
