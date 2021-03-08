/**
 * [ 문자열 압축 ]
 * - 1개 이상 단위로 문자열을 잘라 압축
 * 
 * [ Return ]
 * 압축 후 가장 짧은 길이
 */

function solution(s) {
    let answer = s.length;

    const N = Math.floor(s.length / 2) + 1; // 문자열 압축 단위의 최대치는 문자열의 절반.
    for (let i = 1; i < N; i++) { // 문자열 압축 단위별로 검사
        // 이전에 자른 문자열(preStr)을 저장해서 현재 자른 문자열(str)이랑 비교할거야.
        // preStr가 몇 번이나 연속으로 나오는지 체크할거임.(cnt)
        let preStr = null; 
        let total = 0; // 압축한 문자열 전체 길이
        let cnt = 0; // 자른 문자열 등장 횟수

        for (let j = 0; j < s.length; j += i) { // 문자열 탐색 시작.
            if(!preStr) { // 이전에 자른 문자열이 없으면 저장하고 continue
                preStr = s.substr(j, i);
                total += i;
                cnt = 1;
                continue;
            }
            
            let str = s.substr(j, i); // 문자열을 잘라서
            if(preStr==str) { // 이전에 자른 문자열이랑 동일하면
                cnt++; // 등장횟수 증가
                continue;
            }
            // 이전에 자른 문자열이랑 틀리면, 
            // total에는 현재 문자열 길이 + cnt(등장횟수)의 길이만큼 더해줄거임
            total += str.length + (cnt>1?(cnt+'').length:0); 
            preStr = str;
            cnt = 1;
        } 
        total += (cnt==1?0:(cnt+'').length); // 마지막 끝난 압축 문자도 체크해줘야함
        answer = Math.min(answer, total); 
    }
    return answer;
}

// console.log(solution("aabbaccc"));
console.log(solution("aaaaaaaaaab"));