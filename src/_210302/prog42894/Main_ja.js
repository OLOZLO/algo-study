/**
 * [ 블록게임 ]
 * 3 종류의 블록 -> 회전해서 총 12가지 모양
 * 검은 블록을 떨어뜨려 없앨 수 있는 블록 개수의 최댓값
 */

function solution(board) {
    const N = board.length;
    const availableCol = new Array(N).fill(true);
    const visited = new Array(201).fill(false); 
    visited[0]=true;
    let answer = 0;
    const dx = [[0,-2],[0,-1],[0,0],[0,1],[0,2]
             ,[1,-2],[1,-1],[1,0],[1,1],[1,2]
             ,[2,-2],[2,-1],[2,0],[2,1],[2,2]]; // 요골로 블록 모양 알아낼거임!
    const checkBlock = [[3,8,12,13],[3,8,13,14],[3,6,7,8],[3,8,9,10],[3,7,8,9]]; // 지울 수 있는 블록 -> L,L반대,ㄴ,ㄴ반대, ㅗ
    const fillBlock = [[2,7],[4,9],[1,2],[4,5],[2,4]]; // checkBlock이랑 쌍임! 해당 블록에서 채워야하는 블록의 위치임!
    const retryBlock = {}; // 블록 채워야하는 공간에 아직 방문 안한 블록이 있을 경우, 나중에 다시 방문해야해! 블록의 시작점(첫방문시점)을 저장할거임! 

    for (let i = 0; i < 5; i++) { // 비트마스킹함! -> 지울 수 있는 블록모양
       let bit = 1;
       for (let j = 0; j < 5; j++) {
            bit = bit|1<<checkBlock[i][j];
       }
       checkBlock[i] = bit;
    }
    // 맵 전체를 순회할거임!
    for (let i = 0; i < N; i++) {
        for (let j = 0; j < N; j++) {
            const name = board[i][j];
            if(visited[name]) continue; 

            let point = [i,j];
            if(retryBlock.hasOwnProperty(name)){ // 재방문해야하는 블록이면, 블록 시작점으로 올라가서 확인해볼거임! -> dx로 모양 알아낼려면 시작점으로 돌아가야함...
                point = retryBlock[name];
            }
            // 해당 블록이 어떤 모양인지 알아볼거야.
            let blockShape = 1;
            const colList = new Set(); // 해당 블록의 열 list을 모아둔거야. 검정블록 내려올수있는지 확인하기 위해 사용
            for (let d = 0; d < dx.length; d++) {
                let ii = point[0] + dx[d][0];
                let jj = point[1] + dx[d][1];
                if(ii<0||jj<0||ii>=N||jj>=N||board[ii][jj]!=name) continue;
                blockShape = blockShape|1<<(d+1);
                if(dx[d][1]!=0) colList.add(jj);
            }
            let isFill = false; // 검정블록으로 채울수있는지 확인
            let retry = false; // 재방문해야하는지 확인
            let blockIdx = checkBlock.indexOf(blockShape); // 해당 블록이 지울수 있는 블록에 속하는지 찾을거임
            if(blockIdx>-1){ // 지울 수 있는 블록이면? (1),(2)번 체크할거야.
                isFill = true;
                
                for (const fill of fillBlock[blockIdx]) { // 해당 블록에서 채워야 하는 공간을 하나씩 꺼내서
                    let filli = point[0] + dx[fill-1][0];
                    let fillj = point[1] + dx[fill-1][1];
                    if(!availableCol[fillj]){ // (1) 해당 열이 안 막혀있는지
                        isFill = false;
                        break;
                    }
                    if(!visited[board[filli][fillj]]) { // (2) 채워야하는 자리에 아직 방문 안한 블록이 있는지 -> 있으면 재방문
                        retry = true;
                        if(!retryBlock.hasOwnProperty(name)){
                            retryBlock[name] = [point[0],point[1]];
                        }
                        break;
                    }
                }
            }
            if(retry) continue; // 나중에 재방문하자.
            if(!isFill){ // 만약에, 지울수 없는 블록이면 해당 열 전부 사용불가로!
                availableCol[point[1]] = false;
                for (const col of colList) {
                    availableCol[col] = false;
                }
            }else{ // 지울 수 있는 블록이네!
                delete retryBlock[name]; // 재방문 기록 있으면 지우고
                answer++;
            }
            visited[name] = true; 
        }
    }
    return answer;
}

console.log(solution([
     [0,0,0,0,0,0,3,0,0,0]
    ,[0,0,0,0,0,0,3,0,0,0]
    ,[0,0,0,0,1,3,3,0,0,0]
    ,[0,0,0,0,1,0,0,0,0,0]
    ,[0,0,0,6,1,1,0,0,0,0]
    ,[0,0,6,6,6,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0]
]));

console.log(solution([
    [0, 2, 0, 0], 
    [1, 2, 0, 4], 
    [1, 2, 2, 4], 
    [1, 1, 4, 4]]));    //3 
// console.log(solution([[0, 0, 0, 0, 0], [1, 0, 0, 2, 0], [1, 2, 2, 2, 0], [1, 1, 0, 0, 0], [0, 0, 0, 0, 0]]));    //2