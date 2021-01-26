package _210126.boj11438;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// boj 11437도 동일합니당
public class Main_1n9yun {
    static List<Integer>[] adjList;
    static int[][] mark;
    static int[] depth;
    static int stoi(String s) { return Integer.parseInt(s); }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = stoi(br.readLine());
        adjList = new ArrayList[n+1];
        for(int i=0;i< adjList.length;i++) adjList[i] = new ArrayList<>();

//        2^20이면 100만이 넘음 최대 깊이가 5, 10만이라 생각하면 더 줄여도됨 여유롭게 구해놓는거
        mark = new int[n+1][20];
        depth = new int[n+1];

        for(int i=0;i<n-1;i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = stoi(st.nextToken());
            int to = stoi(st.nextToken());

//            양방향 연결
            adjList[from].add(to);
            adjList[to].add(from);
        }

//        각 노드의 깊이와 부모를 기록
        marking(1, 1, 0);

        for(int j=1;j<20;j++){
            for(int i=1;i<=n;i++) {
//                i : 정점
//                j : 2^j번 째 조상 노드
                mark[i][j] = mark[mark[i][j - 1]][j - 1];
            }
        }

        int m = stoi(br.readLine());
        for(int i=0;i<m;i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int left = stoi(st.nextToken());
            int right = stoi(st.nextToken());

//            right의 깊이가 더 깊도록
            if(depth[left] > depth[right]){
                int temp = left;
                left = right;
                right = temp;
            }

            for(int d=19;d>=0;d--){
//                현재 깊이 차이를 넘지 않는 2^d 위의 부모를 계속 갱신하는데
//                결국에 깊이가 같아지는 이유는
//                d가 19 ~ 0 으로 내려가기 때문이다.
//                d가 1이 작아진다는 것은 현재 체크한 높이의 절반 높이이다.
//                d가 17에서 조건을 만족하여 right가 갱신되었다면
//                깊이가 같아지는 d는 무조건 17보다 아래이다.
//                따라서 깊이 차이가 점점 줄어들다가 결국 깊이가 같아지는 것이다.
                if(depth[right] - depth[left] >= (1 << d))
                    right = mark[right][d];
            }
            if(left != right){
                for(int d=19;d>=0;d--){
//                    동시에 2^d 위의 부모를 확인하며 최소 공통 조상 찾기
//                    위에서 깊이를 같게 만드는 방법과 같이 가장 먼 조상부터 확인하며
//                    절반씩 점프하면서 공통 조상이 나타나지 않을 때까지 내려온다.
//                    공통조상이 나타나지 않았을 때 (right)
//                    갱신한 다음 (left)
//                    그 갱신된 위치에서의 2^d 높이를 따지는 것이므로 점점 범위가 줄어든다.
//                    그러다가 첫 번째 공통 조상 직전에서 멈추게 될 것이다
//                    left, right의 이분 탐색인 것이다.
                    if(mark[left][d] != mark[right][d]){
                        left = mark[left][d];
                        right = mark[right][d];
                    }
                }
                bw.write(mark[left][0] + "\n");
            }else bw.write(left + "\n");
        }
        bw.flush();
    }

    static void marking(int parent, int node, int d){
//        현재 노드의 parent와 깊이 d를 기록
        depth[node] = d;
        mark[node][0] = parent;
        for(int next : adjList[node]){
            if(mark[next][0] != 0) continue;
            marking(node, next, d + 1);
        }
    }
}
