package _210202.boj17837;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * [새로운 게임2 - 게임 종료 턴 번호]
 * 
 * N*N 체스판 (4~12)
 * 말의 개수 : K (이동방향:상(3),하(4),좌(2),우(1)) (4~10)
 * 
 * - 체스판 : 흰색(0), 빨간색(1), 파란색(2)
 * - 턴 : 1~K번 말까지 순서대로 이동
 * 		-> 말 위에 다른 말을 올릴 수 있음 (함께 이동)
 * 
 * - 말이 4개 이상 쌓이는 순간 턴 종료
 * 
 * [ 흰색 ] 
 * (1) 이동방향으로 이동
 * (2) 칸에 말이 있는 경우, 그 말 위에 올려놓기
 * 
 * [ 빨간색 ]
 * (1) 이동방향으로 이동
 * (2) 이동하려는 말의 순서 역순으로 바꾸기
 * (3) 칸에 말이 있는 경우, 그 말 위에 올려놓기
 * 
 * [ 파란색 ] 
 * (1) 이동방향 반대로 바꾸기
 * (2) 이동하려는 칸이 파란색인 경우, 이동 X
 * 
 * [ 체스판을 벗어나는 경우 ]
 * - 파란색과 동일
 * 
 * --
 * [ return ]
 * 게임이 종료되는(4개 이상의 말이 쌓이는 경우) 턴 번호 출력
 * -> 1000보다 큰 경우 -1 출력 (== 절대로 게임이 종료되지 않는 경우)
 * 
 * 
 */
public class Main_ja {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		int K = in.nextInt();
		
		int[][] color = new int[N][N]; // 체스판 색 정보
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				color[i][j] = in.nextInt();
			}
		}
		
		String[][] board = new String[N][N]; // 모든 체스 말 위치 정보
		for (int i = 0; i < board.length; i++) {
			Arrays.fill(board[i], "");
		}
		
		int[][] chessPiece = new int[K][3]; // 체스 말 정보 (X,Y,방향)
		for (int i = 0; i < K; i++) {
			int[] point = new int[] {in.nextInt()-1,in.nextInt()-1};
			board[point[0]][point[1]] = (char)('a'+i)+"";	// 체스 말 이름 : a,b,c,d ...
			chessPiece[i] = new int[]{point[0],point[1],in.nextInt()};
		}
		
		int count = 0; // 판 횟수
		int[][] dx = {{},{0,1},{0,-1},{-1,0},{1,0}};
		int[] changeDirection = {0,2,1,4,3};
		
		while(count++ <= 1000) {
			for (int k = 0; k < K; k++) { // 1~K번째 말 순서대로 옮기기
				int[] move = chessPiece[k];
				
				int i = move[0]+dx[move[2]][0];
				int j = move[1]+dx[move[2]][1];
				
				// 현재 k번째 말이 위치한 보드에서 k번쨰 말이 몇번째에 있는지
				// [EX] (1,1)에 'defabc' 순으로 말이 존재할 때, a가 몇 번째에 있는지 정보 저장
				int idx = board[move[0]][move[1]].indexOf((char)('a'+k)+"");  
				
				// k번째 말이 움직일 때, 함께 움직일 말들
				// [EX] 'defabc' 에서 'a'말이 움직일 때, 'bc'도 함께 움직여야함
				String movePieceList = board[move[0]][move[1]].substring(idx); 
				
				// [ 판을 벗어나거나, 파란색 보드판인 경우 ]
				if(i<0||j<0||i>=N||j>=N||color[i][j]==2) {
					move[2] = changeDirection[move[2]]; // 방향 바꾸기
					i = move[0]+dx[move[2]][0];
					j = move[1]+dx[move[2]][1];
					if(i<0||j<0||i>=N||j>=N||color[i][j]==2) // 파란색이거나 판을 벗어나면 움직이지 않기
						continue;
				}
				
				// [ 빨간색 보드판인 경우 ]
				if(color[i][j] == 1) 
					movePieceList = new StringBuffer(board[move[0]][move[1]].substring(idx)).reverse().toString();
				
				// 공통 작업
				// (1) 말 움직이기
				board[i][j] += movePieceList; // 이동할 보드판
				board[move[0]][move[1]] = board[move[0]][move[1]].substring(0,idx); // 이동전 보드판
				
				if(board[i][j].length() >=4) { // 보드판에 말이 4개일 경우
					System.out.println(count);
					return;
				}
				
				// (2) 함께 움직이는 말이 있을 경우, 위치 정보 바꿔주기
				for (int l = 0; l < movePieceList.length(); l++) {
					chessPiece[movePieceList.charAt(l)-'a'][0] = i;
					chessPiece[movePieceList.charAt(l)-'a'][1] = j;
				}
			}
			
		}
		System.out.println(-1);
	}
	
}
