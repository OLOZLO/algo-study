/**
 * [ 괄호 변환 ]
 * - 균형잡힌 괄호 문자열 : 괄호 개수가 동일
 * - 올바른 괄호 : 괄호 짝이 모두 맞을 경우
 * 
 * [ Return ]
 * 균형 잡힌 괄호 문자열로 변환한 결과 
 * 
 */
function solution(p) {
    return convertBracket(['',p]); // [u,v]
}

function convertBracket(uv){ // 변환 시작
    let answer = '';
    while(uv[1]){ // 현재 검사할 문자열이 공백이 될 때까지, v 검사
        uv = balancedBracket(uv[1]); // 1. 균형잡힌 괄호 문자열 찾기
        if(collectBracket(uv[0])){ // 2. 올바른 괄호인지 검사
            answer += uv[0];
            continue;
        } 
        else{
            answer += newBracket(uv); // 3. 올바른 괄호 아니면 고치러 갈거임
            break;
        }
    }
    return answer;
}
function balancedBracket(p){
    let left = 0, right = 0;
    for (let i = 0; i < p.length; i++) { // '('와 ')'의 개수 동일할 때 까지 탐색
        if(p[i]=='(') left++;
        else right++;
        if(left == right) 
            return [p.substring(0,i+1), p.substring(i+1)]; // u와 v 분리해서 반환
    }
    return [];
}

function collectBracket(u){
    let right = [];
    for (let i = u.length-1; i >= 0; i--) { // ')'면, 별도 배열에 저장. 그러다가 '('만나면 ')' 제거
        if(u[i] == ')') 
            right.push(')');
        else{
            if(right.length == 0) return false; // '('인데 저장된 ')'가 없으면 짝 안 맞는거임
            right.pop();
        }
    }
    return right.length != 0? false : true; // '('는 끝났는데 ')'가 남아있으면 짝 안 맞는거임
}

function newBracket(uv){
    let newStr = '(' + convertBracket(uv) + ')'; // v도 괄호 검사해야함. -> 올바른 괄호로 만들어질 때 까지

    for (let i = 1; i < uv[0].length-1; i++) { // u는 처음, 마지막꺼 빼고 반대괄호로 바꿔줌
        if(uv[0][i]=='(') newStr+=')';
        else newStr +='(';
    }
    return newStr;
}

// console.log(solution("(()())()"));