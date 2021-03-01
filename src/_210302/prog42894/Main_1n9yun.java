package _210302.prog42894;

import java.util.*;

public class Main_1n9yun {
    class Item{
        int r, c;
        public Item(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    class Block{
//        블록을 구성하는 좌표
        ArrayList<Item> points;
//        이 좌표를 기준으로 상대좌표 처리하기 위함
        Item base;
//        base 좌표에 대한 빈칸 좌표의 상대 좌표
        int[] blank;
        public Block(ArrayList<Item> points, Item base, int[] blank) {
            this.points = points;
            this.base = base;
            this.blank = blank;
        }
    }
//    각 블록 타입 별 빈 칸을 미리 저장
    HashMap<Integer, int[]> blankType = new HashMap<>();
    int[][] delta = {{-1,0}, {1,0}, {0,-1}, {0,1}};
    public int solution(int[][] board) {
        init();

        final int n = board.length;
        ArrayList<Block> blocks = new ArrayList<>();

        boolean[][] check = new boolean[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(check[i][j] || board[i][j] == 0) continue;
                ArrayList<Item> points = new ArrayList<>();
                Item base = new Item(n+1, n+1);

                Queue<Item> q = new LinkedList<>();
                check[i][j] = true;
                q.add(new Item(i,j));

                int number = board[i][j];
                while(!q.isEmpty()){
                    Item item = q.poll();

//                    base 좌표는 블록이 구성하는 각 좌표의 모든 행과 열 중 각각의 최소 값으로 구성된다.
                    base.r = Math.min(base.r, item.r);
                    base.c = Math.min(base.c, item.c);
                    points.add(item);

                    for(int[] dir : delta){
                        int nRow = item.r + dir[0];
                        int nCol = item.c + dir[1];
                        if(0<=nRow && nRow<n && 0<=nCol && nCol<n && !check[nRow][nCol] && board[nRow][nCol] == number){
                            check[nRow][nCol] = true;
                            q.add(new Item(nRow, nCol));
                        }
                    }
                }

                int type = 0;
//                각 블록을 구성하는 좌표를 상대좌표화하여 타입을 알아낸다.
                for(Item item : points){
                    type |= 1<<((item.r - base.r) * 3 + (item.c - base.c));
                }
                blocks.add(new Block(points, base, blankType.get(type)));
            }
        }

        int answer = 0;
//        제거할 블록이 없을 때
        while(!blocks.isEmpty()){
            Iterator<Block> it = blocks.iterator();

//            제거할 수 없는 경우 break 하기 위함
            boolean removedSomething = false;
            while (it.hasNext()) {
                Block block = it.next();
                boolean possible = true;
                for (int blank : block.blank) {
//                    base 좌표를 이용하여 빈 칸의 절대좌표를 구함
                    int i = block.base.r + (blank / 3);
                    int j = block.base.c + (blank % 3);
                    
//                    위로 올라가면서 막혀있는지 확인
                    for(int r=i;r>=0;r--){
                        if(board[r][j] != 0) {
                            possible = false;
                            break;
                        }
                    }
                    if(!possible) break;
                }
//                다 안막혀 있으면 없애버리기
                if (possible) {
                    for (Item point : block.points) board[point.r][point.c] = 0;
                    it.remove();
                    removedSomething = true;
                    answer++;
                }
            }
            if (!removedSomething) break;
        }
        return answer;
    }
    public void init(){
        blankType.put(getTypeKey(0, 3, 4, 6), new int[]{1,7});
        blankType.put(getTypeKey(0, 1, 2, 4), new int[]{3,5});
        blankType.put(getTypeKey(1, 3, 4, 7), new int[]{0,6});
        blankType.put(getTypeKey(1, 3, 4, 5), new int[]{0,2});

        blankType.put(getTypeKey(0, 1, 3, 6), new int[]{4,7});
        blankType.put(getTypeKey(0, 1, 2, 5), new int[]{3,4});
        blankType.put(getTypeKey(1, 4, 6, 7), new int[]{0,3});
        blankType.put(getTypeKey(0, 3, 4, 5), new int[]{1,2});

        blankType.put(getTypeKey(0, 1, 4, 7), new int[]{3,6});
        blankType.put(getTypeKey(2, 3, 4, 5), new int[]{0,1});
        blankType.put(getTypeKey(0, 3, 6, 7), new int[]{1,4});
        blankType.put(getTypeKey(0, 1, 2, 3), new int[]{4,5});
    }
    public int getTypeKey(int... bits){
        int result = 0;
        for(int bit : bits) result |= 1<<bit;
        return result;
    }
}
