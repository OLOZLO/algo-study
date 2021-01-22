/**
 * [ n진수 게임 - 십진수를 n진법으로 변환해서 자기 차례 숫자 전부 말하기]
 * 
 * n : 진법
 * t : 미리 구할 숫자의 갯수 (튜브 차례가 t번 온다는 의미)
 * m : 게임에 참가하는 인원 
 * p : 튜브의 순서
 * 
 * [ 풀이 ]
 * (1) cnt=0 부터 시작
 * (2) cnt++을 n진법으로 변환해서 하나의 변수(data)에 저장 
 *     => javascript의 10진수 n진법으로 변환 : 숫자.toString(진법)
 * (3) data의 길이가 t*m 길이만큼 되도록 (2)를 반복
 * (4) 튜브 차례에 맞는 숫자 하나씩 변수에(answer) 저장
 */


function solution(n, t, m, p) {

    let cnt = 0; // (1) 숫자 0부터 시작

    let data = '';
    while(data.length < t*m){
        data+=(cnt++).toString(n);  // n진법으로 변환해서 하나의 변수(data)에 저장 
    }
    // (4) 튜브 차례에 맞는 숫자 하나씩 변수에(answer) 저장
    var answer = '';
    for (let i = 0; i < t; i++) {
        answer +=data[(p-1)+(m*i)];
    }
    return answer.toUpperCase();
}

// console.log(solution(2,4,2,1));
// console.log(solution(16,16,2,1));
console.log(solution(16,16,2,2));
