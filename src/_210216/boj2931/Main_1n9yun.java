package _210216.boj2931;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main_1n9yun {
    static int[][] delta = {{1,0},{0,1},{-1,0},{0,-1}};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int r = sc.nextInt();
        int c = sc.nextInt();
        int[][] map = new int[r+1][c+1];

//        블록별 통로 저장
//        0 ~ 3비트는 통로, 4비트는 블록이 있는 자리인지 체크
        Map<Character, Integer> parts = new HashMap<>();
        parts.put('|', 1<<0 | 1<<2);
        parts.put('-', 1<<1 | 1<<3);
        parts.put('+', (1<<4) - 1);
        parts.put('1', 1<<0 | 1<<1);
        parts.put('2', 1<<1 | 1<<2);
        parts.put('3', 1<<2 | 1<<3);
        parts.put('4', 1<<3 | 1<<0);

        for(int i=1;i<=r;i++){
            String input = sc.next();
            for(int j=1;j<=c;j++){
                char part = input.charAt(j - 1);
                if(part == '.') {
                    ;
                } else if(part == 'M' || part == 'Z'){
//                    시작/도착점은 블록이 있다고
                    map[i][j] = 1<<4;
                }else {
//                    입력으로 들어온 블록에 해당하는 정보 입력
                    map[i][j] = parts.get(part);
//                    블록이 있어요
                    map[i][j] |= 1<<4;

                    for (int dir = 0; dir < delta.length; dir++) {
//                        통로가 있는 방향에 있는 칸에서 반대방향으로 체크
                        if ((map[i][j] & (1 << dir)) == 0) continue;
                        int nRow = i + delta[dir][0];
                        int nCol = j + delta[dir][1];
                        if (1 <= nRow && nRow <= r && 1 <= nCol && nCol <= c) {
                            map[nRow][nCol] |= 1<<((dir + 2) % delta.length);
                        }
                    }
                }
            }
        }
        for(int i=1;i<=r;i++){
            for(int j=1;j<=c;j++){
                if(map[i][j] == 0) continue;
//                길은 있는데 블록이 없어 == 누구 부순 칸이야
                if((map[i][j] & (1<<4)) == 0){
//                    그 칸에 블록 다끼워넣어봐
                    for(char key : parts.keySet()){
//                        아주 딱들어막는게 답이야
                        if(map[i][j] == parts.get(key)){
                            System.out.println(i + " " + j + " " + key);
                            return;
                        }
                    }
                }
            }
        }
    }
}
