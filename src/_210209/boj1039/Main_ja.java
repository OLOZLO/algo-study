package _210209.boj1039;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_ja {
	/**
	 * [ 교환 - 나올 수 있는 수의 최댓값 ]
	 * 
	 * N : (1~1,000,000)
	 * M : N의 자릿수
	 * K : 연산 수 (~10)
	 * 
	 * [ 연산 ]
	 * 1<=i<j<=M
	 * i번째 숫자와 j번째 숫자 change
	 * 단, 0으로 시작하면 안됨 ==> return -1
	 * 
	 * K번 연산할 수 없으면(M==1), return -1
	 * @throws IOException 
	 * 
	 */

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		String N = in.next();
		int K = in.nextInt();
		
		int M = N.length();
		int start = Integer.parseInt(N);
		
		if(M <= 1) { // 일의 자리면, 연산 불가능
			System.out.println(-1);
			System.exit(0);
		}
		
		Queue<Node> qu = new LinkedList<>();
		qu.add(new Node(start,K));
		
		int answer = Integer.MIN_VALUE;
		boolean[][] check = new boolean[K][1000001];
		while(!qu.isEmpty()) {
			Node node = qu.poll();
			// K번까지 연산
			if(node.cnt<=0) {
				answer = Math.max(answer, node.result);
				continue;
			}
			for (int i = 0; i < M-1; i++) {
				for (int j = i+1; j < M; j++) {
					// i번째 숫자를 기준으로 j번쨰 숫자랑 자리 바꿈
					int result = changeNumber(node.result, M, i, j);
					// -1(0으로 시작하는 경우) || cnt-1번째 연산에서 동일한 숫자가 있는 경우 pass
					if(result == -1 || check[node.cnt-1][result]) continue;
					
					qu.add(new Node(result, node.cnt-1));
					check[node.cnt-1][result] = true;
				}
			}
		}
		System.out.println(answer==Integer.MIN_VALUE?-1:answer);
		
	}
	static int changeNumber(int result, int M, int i, int j) {
		String str = String.valueOf(result);
		int a = str.charAt(i) - '0';
		int b = str.charAt(j) - '0';
		if(a==b) return result;
		int powA = (int)Math.pow(10,M-i-1);
		int powB = (int)Math.pow(10,M-j-1);
		
		result -= (a*powA + b*powB);
		result += (a*powB + b*powA);
		return i==0&&b==0?-1:result;
	}
	
	static class Node{
		int result;
		int cnt;
		public Node(int result, int cnt) {
			this.result = result;
			this.cnt = cnt;
		}
	}
}

