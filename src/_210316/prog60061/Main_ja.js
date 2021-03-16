/**
 * [ 기둥과 보 설치 ]
 * 기둥(0) : 바닥 위, 보의 한쪽 끝 부분 위, 다른 기둥 위
 * 보(1) : 한쪽 끝 부분이 기둥 위, 양쪽 끝 부분이 다른 보와 동시에 연결
 * 
 * [ Return ]
 * 최종 구조물 상태
 * [x,y,a] -> 좌표, 구조물 종류(0 -> 기둥, 1 -> 보)
 * 
 *  build_frame : [x,y,a,b] -> 좌표, 구조물 종류, 삭제(0)/설치(1) 
*/
const COLUMN = 0, BEAM = 1;
function solution(n, build_frame) {
    n++;
    // 기둥, 보
    const column = Array.from(Array(n), () => Array(n).fill(false));
    const beam = Array.from(Array(n), () => Array(n).fill(false));
    
    build_frame.forEach(frame => {
        let action = frame[3];
        frame.pop(); // frame 그대로 저장할라고 마지막 요소만 제거해줌.
        switch (action) {
            case 1: // 설치
                insertFrame(column,beam,frame);
                break;
            case 0: // 삭제
                deleteFrame(column,beam,frame);
                break;
        }
    });

    const answer = [];
    // 설치된 구조물만 answer에 옮기기
    for (let i = 0; i < n; i++) {
        for (let j = 0; j < n; j++) {
            if(column[i][j]) answer.push(column[i][j]);
            if(beam[i][j]) answer.push(beam[i][j]);
        }
    }
    return answer;
}

function checkColumn(column,beam,i,j){
     // 바닥 위, 보의 한쪽 끝 부분 위, 다른 기둥 위
    return j==0 || beam[i][j] || (i>0 && beam[i-1][j]) || column[i][j-1]
}
function checkBeam(column,beam,i,j){
    // 기둥 위에 있는게 아니면, 양 끝에 다른 보가 있어야 함
    const n = column.length;
    return (j>0 && (column[i][j-1] || column[i+1][j-1])) || ( i>0 && i<n && beam[i-1][j] && beam[i+1][j])
}
function checkFrame(column,beam){
    let n = column.length;
    // 전체 순회해서 제대로 연결 안 된 frame 발견하면 false 반환
    for (let i = 0; i < n; i++) {
        for (let j = 0; j < n; j++) {
            if(column[i][j] && !checkColumn(column, beam, i, j)) return false;
            if(beam[i][j] && !checkBeam(column, beam, i, j)) return false;
        }
    }
    return true;
}

function insertFrame(column,beam,frame){
    const i = frame[0];
    const j = frame[1];
    switch (frame[2]) { // 구조물 종류
        case COLUMN: // 기둥
            if(checkColumn(column,beam,i,j)) 
                column[i][j] = frame;
            break;
        case BEAM: // 보
            if(checkBeam(column,beam,i,j))
                beam[i][j] = frame;
            break;
    }
}

function deleteFrame(column,beam,frame){
    const i = frame[0];
    const j = frame[1];
    const tempFrame = frame[2] == COLUMN ? column[i] : beam[i]; // 삭제할 frame 저장
    
    const temp = tempFrame[j]; 
    tempFrame[j] = false; // 일단 삭제해보고
    if(!checkFrame(column,beam)) // 하나라도 제대로 연결 안된 frame 있으면 
        tempFrame[j] = temp; // 원상복귀
}

// console.log(solution(5,[[1,0,0,1],[1,1,1,1],[2,1,0,1],[2,2,1,1],[5,0,0,1],[5,1,0,1],[4,2,1,1],[3,2,1,1]]));

// console.log(solution(5, [[0,0,0,1],[2,0,0,1],[4,0,0,1],[0,1,1,1],[1,1,1,1],[2,1,1,1],[3,1,1,1],[2,0,0,0],[1,1,1,0],[2,2,0,1]]));
