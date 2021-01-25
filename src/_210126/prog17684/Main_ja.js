/**
 * [ 압축 - 무손실 압축 알고리즘(LZW) ]
 * 
 * LZW(Lempel-Ziv-Welch) 압축
 * (1) a-z(1-26)까지 사전 초기화
 * (2) 사전에서 현재 입력과 일치하는 가장 긴 문자열 w
 * (3) w에 해당하는 사전 색인 번호 출력, w는 입력에서 제거
 * (4) 입력에서 처리되지 않은 글자(c), w+c에 해당하는 단어를 사전에 등록
 * (5) (2)번으로
 * 
 * [ 상세 조건 ]
 * - 대문자만 처리
 * - 색인번호는 1부터
 * 
 * [ 풀이 ]
 * (1) a-z까지 사전 초기화
 * (2) FOR. msg 길이 만큼 반복
 *     IF. w+c가 사전에 있다면{
 *         w = w+c  => 현재 입력을 w+c로 저장
 *         c = msg[i+2]; => 다음 글자를 다다음 글자로 저장
 *     }
 *     ELSE. {
 *         w+c 사전 추가
 *         answer에 w 색인 추가(출력)
 *         w = msg[i+1]; => 현재 입력을 다음 글자로 저장
 *         c = msg[i+2]; => 다음 글자를 다다음 글자로 저장
 *     }
 * (3) answer 출력 
 */

function solution(msg) {
    let dictionary = {};
    for (let i = 0, alphabet = "A"; i < 26; i++) {
        dictionary[String.fromCharCode(alphabet.charCodeAt()+i)] = i+1;
    }

    let w = msg[0], c = msg[1];
    let number = 27;
    var answer = [];
    
    for (let i = 0; i < msg.length; i++) {
        if((w+c) in dictionary){ // w+c가 사전에 있으면 => dictionary.hasOwnProperty(w+c) 이렇게 사용 가능
            w = w + c; // w+c를 현재 입력(w)으로 저장
            c = msg[i+2]; // c의 다음 글자(msg[i+2])를 다음 글자(c)로 저장  [EX] 'KAKAO'에서 w = 'A', c = 'K' 일 때, 'AK'가 사전에 있으면 w='AK', c='A' (c의 다음 글자)
        }else{
            dictionary[w+c] = number++; // 색인 저장
            answer.push(dictionary[w]); // 색인 출력
            w = msg[i+1]; // 다음 글자를 현재 입력(w)로 저장
            c = msg[i+2]; // 다다음 글자를 다음 글자(c)로 저장
        }
    }
    return answer;
}

// console.log(solution('KAKAO'));
// console.log(solution('TOBEORNOTTOBEORTOBEORNOT'));
// console.log(solution("THATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITISTHATTHATISISTHATTHATISNOTISNOTISTHATITITIS"));
