package _210119.boj1800;

import java.util.*;

// 잊혀져있던 이분탐색을 일깨움
public class Main_1n9yun {
    static class Item {
        int to, cost, cnt;

        public Item(int to, int cost, int cnt) {
            this.to = to;
            this.cost = cost;
            this.cnt = cnt;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int p = sc.nextInt();
        int k = sc.nextInt();

//        인접 리스트
        List<Item>[] adjList = new ArrayList[n+1];
        for(int i=0;i<adjList.length;i++) adjList[i] = new ArrayList<>();

        for(int i=0;i<p;i++){
            int from = sc.nextInt();
            int to = sc.nextInt();
            int cost = sc.nextInt();

//            양방향 연결
            adjList[from].add(new Item(to, cost, 0));
            adjList[to].add(new Item(from, cost, 0));
        }

//        원하는 답을 찾기 위해 이분탐색을 할거임.
//        왜? 어떤 값에서 불가능하면 그 아래는 다 불가능하며 가능하면 그 아래로도 가능한 지 확인해야 함 (범위를 좁혀나가야 함)
//        답의 범위는 최소 0에서 최대 cost 이지만 귀찮으니까 그냥 MAX 1000000으로 잡음
        int left = 0;
        int right = 1000000;

//        처음엔 최소를 구해야한다는 생각에 MAX_VALUE로 초기화 했지만 초기화 안해도 됨(그냥 되는 경우 대입해버리면 됨)
        int ans = Integer.MAX_VALUE;
        while(left <= right){
            int mid = (left + right) / 2;

//            다익스트라를 할건데
//            왜? 당연히 DFS해서 모든 경로를 탐색하고 비싼 간선을 최소 개수로 가는 경로를 구해줘도 된다. 근데 싼 간선들 위주로 먼저 연결해주면(우선순위를 주면) 더 적게 탐색하지 않을까?
//            우선순위는 내가 정한 답 (mid) 이하의 cost.
//            둘 다 초과라면 비싼 간선을 적게 사용한 경로 먼저 (없어도 관계없지만 이러면 조금이라도 더 빨리 끝나지 않을까?)
            PriorityQueue<Item> pq = new PriorityQueue<>((o1, o2) -> {
                if(o1.cost > mid && o2.cost <= mid) return 1;
                else if(o1.cost > mid && o2.cost > mid) {
                    if(o1.cnt > o2.cnt) return 1;
                }
                return -1;
            });

//            이미 방문했더라도 비싼 간선을 적게 사용한 경로를 채택하도록 할 것임
//            해당 정점에 도착하기까지 비싼 간선을 가장 적게 사용한 경로
            int[] check = new int[n+1];
            Arrays.fill(check, Integer.MAX_VALUE);
            check[1] = 0;

            for(int i=0;i<adjList[1].size();i++) {
                Item item = adjList[1].get(i);

//                1번에서 시작되는 간선들, cnt는 비싸면 1, 아니면 0으로 시작
                pq.add(new Item(item.to, item.cost, item.cost > mid ? 1 : 0));
            }

            while(!pq.isEmpty()){
                Item item = pq.poll();

//                너보다 싸게 온 사람이 있다임마 저리가라
                if(check[item.to] <= item.cnt) continue;
//                아이고 오셨습니까요
                check[item.to] = item.cnt;

                for(int i = 0; i<adjList[item.to].size(); i++){
                    Item next = adjList[item.to].get(i);

//                    지금 가려는 간선이 비싸면 cnt + 1, 아니면 그대로
                    pq.add(new Item(next.to, next.cost, next.cost > mid ? item.cnt + 1 : item.cnt));
                }
            }

//            다 돌고나서 mid보다 비싼 간선이 정확히 k여야 답이 될 수 있다.
//            근데 k보다 많이 써서 도착할 수 밖에 없었다? 기준을 더 높여야 함
//            k 이하로 왔다? 그러면 일단 정확히 k개를 사용해서 올 수 있는 경우가 있다는거임
//            기준(mid)를 좀 더 낮춰보고 안되면 알아서 끝나고 이전에 왔을 때의 결과가 답이 될 것
            if(check[n] > k){
                left = mid + 1;
            }else{
                right = mid - 1;
                ans = mid;
            }
        }
        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }
}
