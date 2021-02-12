/**
 * [ 후보키 ]
 * 1. 유일성 : 유일하게 식별가능
 * 2. 최소성 : 속성 하나라도 제외하는 경우 유일성이 깨지는 것
 */
// var쓰면 안되는데.. 전역변수 만들기 귀찮아서..ㅎ
var answer = 0, column = 0, candidateKey = [];

function solution(relation) {
    column = relation[0].length;
    candidateKey = [];
    answer = 0;
    combination(relation);
    return answer;
}
// 0,1,2 / 01,02,12 / 012 , 1->n개 순으로 인덱스 조합 
function combination(relation){
    let data = [''];
    // 하나씩 key를 빼고 추가하면서 확인할거임
    while(data.length != 0){
        let key = data.shift();
        let start = key[key.length-1] || -1;
        start++;
        // 인덱스 순서는 상관없으니까 앞 인덱스는 확인 ㄴㄴ => start 변수
        for (let i = start, len = column; i < len; i++) {
            let nextKey = key+i;
            // 유일성
            if(isUniqueness(relation,nextKey)){
                // 최소성
                if(!isMinimality(nextKey)) continue;
                candidateKey.push(nextKey);
                answer++;
            }
            else
                data.push(nextKey);
        }
    }
}
function isMinimality(nextKey){
    // 확인할 키(nextKey)에 후보키가 속하는지 확인할 거임
    for (let i = 0, len1 = candidateKey.length; i < len1; i++) {
        let cnt = 0; // nextKey에 해당 후보키가 존재하는지 개수로 계산할거임!
        let key = candidateKey[i];
        for (let j = 0, len2 = key.length; j < len2; j++) {
            if(nextKey.includes(key[j])) cnt++;
            else break;
        }
        if(cnt == key.length) return false;
    }
    return true; 
}
function isUniqueness(relation, candidateKey){
    let data = new Set();
    // 튜플 하나씩 문자열로 이어가지고 set에 집어넣을거임
    // 중복있으면 원본이랑 size 다르니까 그걸로 판단 :>
    for (let i = 0, row = relation.length; i < row; i++) {
        let str = ''
        for (let j = 0, col = candidateKey.length; j < col; j++) {
            str+=relation[i][candidateKey[j]]+"/"; // '/'로 데이터 구분
        }
        data.add(str);
    }
    return data.size == relation.length ? true:false;
}

// console.log(solution([["100","ryan","music","2"],["200","apeach","math","2"],["300","tube","computer","3"],["400","con","computer","4"],["500","muzi","music","3"],["600","apeach","music","2"]]));
// console.log(solution([["1","1","1","1"],["1","2","2","2"],["1","1","2","3"],["1","2","3","4"]]));

// console.log(solution( [['a', 'aa'], ['aa', 'a'], ['a', 'a']]))

console.log(solution([
    ["b","2","a","a","b"],
    ["b","2","7","1","b"],
    ["1","0","a","a","8"],
    ["7","5","a","a","9"],
    ["3","0","a","f","9"]]))