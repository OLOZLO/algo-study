/* 
    push : 맨 뒤에 추가
    unshift : 맨 앞에 추가

    pop : 맨 뒤에 제거
    shift : 맨 앞에 제거
*/ 
// 클래스 선언은 함수와 같이 호이스팅 발생 X, 먼저 클래스를 선언해 줘야함
class Queue{
    constructor(){
        this.arr = [];
    }
    push(item) {
        this.arr.push(item);
    }
    pop(){
        return this.arr.shift();
    }
    isEmpty(){
        return this.arr.length == 0? true: false;
    }
    length(){
        return this.arr.length;
    }
}

function solution(m, n, board) {
    var answer = 0;

    for (let i = 0; i < m; i++) {
        board[i] = board[i].split("");
    }
    // 제거할 블록 LIST 저장
    const qu = new Queue();
    let di = [0,1,1];
    let dj = [1,1,0];
    while(true){
        for (let i = 0; i < m-1; i++) {
            for (let j = 0; j < n-1; j++) {
                let check = [];
                let name = board[i][j];
                if(name == '.') continue;
                check.push([i,j]);
                for (let d = 0; d < 3; d++) {
                    let ii = i + di[d];
                    let jj = j + dj[d];

                    if(name !== board[ii][jj]) break;
                    check.push([ii,jj]);
                }
                if(check.length == 4) {
                    qu.push(check);
                }
            }
        }
        
        if(qu.isEmpty()) break;
        // 블록 제거
        let len = qu.length();
        for (let i = 0; i < len; i++) {
            let blockArr = qu.pop();
            for (let d = 0; d < 4; d++) {
                let point = blockArr[d];
                if(board[point[0]][point[1]] !== "."){
                    board[point[0]][point[1]] = ".";
                    answer++;
                }
            }
        }
        
        // 블록 떨어지기
        // 바닥부터 위로 올라감
        for (let j = 0; j < n; j++) {
            let cnt = 0;
            for (let i = m-1; i >= 0; i--) {
                if(board[i][j]==='.')
                    cnt++;
                else{
                    if(cnt == 0) continue;
                    board[i+cnt][j] = board[i][j];
                    board[i][j] = '.';
                }
            }
        }
    };   
    console.log(answer);
    return answer;
}
solution(4,5,["CCBDE", "AAADE", "AAABF", "CCBBF"])
solution(6,6,["TTTANT", "RRFACC", "RRRFCC", "TRRRAA", "TTMMMF", "TMMTTJ"])
solution(6,6, ["AABBEE","AAAEEE","VAAEEV","AABBEE","AACCEE","VVCCEE" ]) // 32

// arr = [][] 선언 불가 (배열의 길이 동적으로 조절 가능)
// Array(1) => 길이가 1인 배열 생성
// Array(1,2) => 요소가 (1,2)인 배열 생성
// const visited = Array.from(Array(n), () => Array(m).fill(false));