package _210202.boj17837;

import java.util.Scanner;

public class Main_1n9yun {
    static class Mal{
        int r, c, dir;

        public Mal(int r, int c, int dir) {
            this.r = r;
            this.c = c;
            this.dir = dir;
        }
        public void move(int dir){
            this.r += delta[dir][0];
            this.c += delta[dir][1];
        }
        public void turnBack(){
//            1, 2 - 3, 4
            if(this.dir == 1) this.dir = 2;
            else if(this.dir == 2) this.dir = 1;
            else if(this.dir == 3) this.dir = 4;
            else if(this.dir == 4) this.dir = 3;
        }
    }
    static int[][] delta = {{0, 0}, {0,1},{0,-1},{-1,0},{1,0}};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();
        int[][] map = new int[n+1][n+1];
//        말들이 map위에서 어떤 모습인지
        StringBuilder[][] malMap = new StringBuilder[n+1][n+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                malMap[i][j] = new StringBuilder();
            }
        }

//        각 말들의 위치와 방향
        Mal[] mals = new Mal[k];

        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
//                0 흰, 1 빨, 2 파
                map[i][j] = sc.nextInt();
            }
        }

        for(int i=0;i<k;i++){
            int r = sc.nextInt();
            int c = sc.nextInt();
            int dir = sc.nextInt();

            mals[i] = new Mal(r, c, dir);
            malMap[r][c].append(i);
        }

        int answer = 0;
        out:
        while(answer <= 1000){
            answer++;

            for(int i=0;i<mals.length;i++){
//                i번 말이 있는 위치의 상태
                StringBuilder currentList = malMap[mals[i].r][mals[i].c];

//                그 상태에서 i번 말이 몇번 째 있는지
                int thisMalIndex = currentList.indexOf(Integer.toString(i));
//                이동하고 남는 말들
                StringBuilder malRemain = new StringBuilder(currentList.substring(0, thisMalIndex));
//                이동해야 하는 말들
                StringBuilder malMove = new StringBuilder(currentList.substring(thisMalIndex));

//                남는 말들로 바꿔주고
                malMap[mals[i].r][mals[i].c] = malRemain;
                
                int nRow = mals[i].r + delta[mals[i].dir][0];
                int nCol = mals[i].c + delta[mals[i].dir][1];

                boolean shouldMove = true;
                boolean outOfRange = !(1 <= nRow && nRow <= n && 1 <= nCol && nCol <= n);
                if(outOfRange || map[nRow][nCol] == 2){
//                    바깥, 파랑일 때
                    mals[i].turnBack();
                    nRow = mals[i].r + delta[mals[i].dir][0];
                    nCol = mals[i].c + delta[mals[i].dir][1];

                    outOfRange = !(1 <= nRow && nRow <= n && 1 <= nCol && nCol <= n);
                    if(outOfRange || map[nRow][nCol] == 2) {
                        shouldMove = false;
                        nRow = mals[i].r;
                        nCol = mals[i].c;
                    }
                }
//                움직이지 않는 경우는 뒤집으면 안돼
                if(!outOfRange && map[nRow][nCol] == 1 && shouldMove){
//                    빨
                    malMove.reverse();
                }
                malMap[nRow][nCol].append(malMove);

                if(shouldMove){
//                    움직이는 경우 malMove를 구성하는 각각 말들을 이동시켜줌
                    for(int malIdx=0;malIdx<malMove.length();malIdx++){
                        mals[malMove.charAt(malIdx) - '0'].move(mals[i].dir);
                    }
                }
                if(malMap[nRow][nCol].length() >= 4) break out;
            }
        }

        System.out.println(answer > 1000 ? -1 : answer);
    }
}
