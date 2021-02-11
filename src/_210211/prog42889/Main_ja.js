/**
 * [ 실패율 ]
 * 스테이지에 도달, 클리어 X 플레이어의 수 / 스테이지에 도달한 플레이어 수
 * 
 * N : 전체 스테이지의 수
 * stages : 사용자의 현재 스테이지 번호
 * N+1 : N까지 깬 사람
 * 
 * [ return ]
 * 실패율이 높은 스테이지부터 내림차순
 * -> 같으면 작은 번호부터
 * -> 스테이지에 도달한 플레이어가 없으면 0
 */

function solution(N, stages) {
    const completedStage = new Array(N+2).fill(0);
    // stage별로, 완료된 유저 count
    for (let i = 0, len = stages.length; i < len; i++) {
        completedStage[stages[i]-1]++;
    }

    // 실패율 계산 및 정렬
    const failure = [];
    let user = stages.length;
    
    for(let i = 0; i < N; i++){
        failure.push([completedStage[i] / user,i]);
        user-=completedStage[i];
    }
    failure.sort((stage1, stage2) => stage2[0] - stage1[0] );
    
    const answer = [];
    for (let i = 0; i < N; i++) {
        answer.push(failure[i][1]+1);
    }
    return answer;
}

console.log(solution(5,[2,1,2,6,2,4,3,3]));
console.log(solution(4,[4,4,4,4,4]));