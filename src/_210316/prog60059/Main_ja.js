/**
 * [ 자물쇠와 열쇠 ]
 * 자물쇠 : N*N (3~20)
 * 열쇠 : M*M (3~20)
 * 
 * - 자물쇠과 열쇠는 홈(0) + 돌기(1)로 구성
 * 
 * - 열쇠는 회전과 이동이 가능
 * - 열쇠의 돌기 부분을 자물쇠의 홈 부분에 딱 맞게 채우면, 자물쇠가 열림
 * 
 * - 열쇠는 자물쇠의 영역을 벗어나도, 영역 내의 부분만 정확히 열치하면 됨
 * 
 * [ Result ]
 * 열쇠로 자물쇠를 열 수 있는지 판별 true, false
 */

/**
 * [ 접근 방식 ]
 * 자물쇠를 가지고 열쇠와 일치하는지 판단.
 * 열쇠는 영역 밖을 벗어날 수 있는 부분이 나올 수 있어서, 자물쇠를 기준으로 함
 * 
 * 1. 자물쇠에서 1(돌기)로만 구성되어 있는 side를 잘라냄
 *    -> 자물쇠 크기를 줄일려고
 * 2. 자물쇠 값을 반전 1->0, 0->1
 * 3. 열쇠를 전체 순회할 때, 자물쇠를 회전해가면서 일치하는지 찾기
 */
function solution(key, lock) {
    // 자물쇠의 불필요한 side 잘라내기
    lock = sliceLock(lock);

    // 자물쇠 전부가 1로만 구성되어 있을 때
    if(lock == null) return true;

    const M = key.length;
    // 자른 자물쇠가 열쇠보다 클 때는 노 일치
    if(lock.length == 0 || lock.length > M || lock.length[0] > M ) return false;
    // 열쇠 전체 순회
    for (let rotate = 0; rotate < 4; rotate++) {
        for (let i = 0; i < M; i++) {
            for (let j = 0; j < M; j++) {
                // 열쇠[i,j]에서 시작했을 때 자물쇠와 일치하는지 판단 -> 하나라도 불일치하면 fasle
                let answer = matchKeyAndLock(key, lock, i, j);
                if(answer) return true;
            }
        }
        lock = turnLock(lock); // 자물쇠 회전 후 재검사
    }
    
    return false;
}

function sliceLock(lock){
    let N = lock.length;
    const row = [], col = []; 
    let cnt = 0;
    for (let i = 0; i < N; i++) { // 1만 존재하는 행, 열 찾기
        for (let j = 0; j < N; j++) {
            if (lock[i][j] == 0) {
                row[i] = true;
                col[j] = true;
            } else cnt++;
        }
    }
    if(cnt == N * N) return null;
    let toRow = null, fromRow = null, toCol = null, fromCol = null; 
    for (let i = 0; i < N; i++) { // 자를 행, 열의 시작점과 끝점 저장
        if(toRow == null && row[i]) toRow = i;
        if(fromRow == null && row[N-i-1]) fromRow = N-i-1;
        if(toCol == null && col[i]) toCol = i;
        if(fromCol == null && col[N-i-1]) fromCol = N-i-1;
    }

    const newLock = Array.from(Array(fromRow-toRow+1), () => Array());
    for (let i = toRow; i <= fromRow; i++) { // 자른 자물쇠를 새로운 배열에 저장
        for (let j = toCol; j <= fromCol; j++) {
            newLock[i-toRow][j-toCol] = lock[i][j]==0?1:0; // 열쇠와 매칭하기 위해 반대로 저장
        }
    }
    return newLock;
}

function matchKeyAndLock(key, lock, ki, kj){
    const M = key.length;
    let check = true;
    for (let i = 0, row = lock.length; i < row; i++) { // 자물쇠 순회하면서
        for (let j = 0, col = lock[0].length; j < col; j++) {
            if(ki+i >= M || kj+j >= M || lock[i][j] != key[ki+i][kj+j]) check = false; // 열쇠와 동일한지 판단
        }
        if(!check) break;
    }
    if(check) return true;
    return false;
}

function turnLock(lock){
    const row = lock.length;
    const col = lock[0].length;
    const copy = Array.from(Array(col), () => Array(row).fill(0));
    for (let i = 0; i < col; i++) {
        for (let j = 0; j < row; j++) {
            copy[i][j] = lock[row-1-j][i];
        }
    }
    return copy;
}

console.log(solution([[0, 0, 0], [1, 0, 0], [0, 1, 1]], [[1, 1, 1], [1, 1, 0], [1, 0, 1]]));
console.log(solution(
    [[0, 0, 0], 
    [1, 0, 0], 
    [0, 1, 1]],

    [[1, 0, 1], 
    [1, 1, 0], 
    [1, 1, 0]],
))


// function matchKeyAndLock(key, lock, ki, kj){
//     const M = key.length;
//     let row = lock.length;
//     let col = lock[0].length;
//     for (let turn = 0; turn < 4; turn++) {
//         let check = true;
//         for (let i = 0; i < row; i++) {
//             for (let j = 0; j < col; j++) {
//                 if(ki+i>=M || kj+j>=M || turnLock(turn, lock, [row,col], [i, j]) != key[ki+i][kj+j]) 
//                     check = false; 
//             }
//             if(!check) break;
//         }
//         if(check) return true;
//         let temp = row;
//         row = col;
//         col = temp;
//     }
//     return false;
// }
// function turnLock(turn, lock, range, point){
//     const row = range[0], col = range[1];
//     const i = point[0], j = point[1];
//     switch (turn) {
//         case 0: // 0
//             return lock[i][j];
//         case 1: // 90
//             return lock[col-1-j][i];
//         case 2: // 180
//             return lock[row-1-i][col-1-j];
//         case 3: // 270
//             return lock[j][row-1-i];
//     }
// }