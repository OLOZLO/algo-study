/**
 * [ 자동완성 - 학습된 단어를 순서대로 찾을 때, 입력해야 할 총 문자수 ]
 * - 2 <= N <= 100,000 
 *  => 중복 없는 단어 N개
 *  => 소문자
 * 
 * 2 <= L <= 1,000,000
 *  => 단어들의 길이의 총합
 */

 // 저는... 0-i글자만큼 잘라서.. 비교해서... cnt 했습니다...
function solution(words) {
    let answer = 0;
    let arr = {};
    // 문자열 최대 길이만큼 반복
    for (let i = 1; i <= 1000000; i++) {
        // 남은 단어가 없으면 break; => 검색한 단어는 지워줄 것!
        if(words.length <= 1) break;

        // j번째 단어의 (0,i) 길이만큼 잘라서 객체에 저장
        // [EX] word ['aaa','aab', 'bbb']
        // { a:[2,1], b:[1,2] } => 문자열:[cnt,최초의idx]
        for(let j = words.length-1; j>=0; j--){
            // 해당 단어 길이만큼 탐색 다했으면, 지우고 continue
            let size = words[j].length+1;
            if(size <= i) {
                words.splice(j,1);
                continue;
            }

            // 'aaa' -> a
            const word = words[j].substring(0,i);
            if(!arr[word]) // 객체에 없으면 추가
                arr[word] = [1,j];
            else // 있으면 cnt++
                arr[word][0]++;
            answer++;
        }
        // 만약 자른 문자열과 연관된 단어가 1개 밖에 없으면 삭제 (더이상 검색할 필요 X)
        const keys = Object.keys(arr); // TIP. for in 반복문으로 하면, 동기식으로 작동 X.. for문 밖에있는 코드가 먼저 실행되기도함..
        for (let j = 0; j < keys.length; j++){
            if(arr[keys[j]][0] == 1){
                words.splice(arr[keys[j]][1],1);
            }
        }
        arr = {};
    }
    
    return answer;
}

// console.log(solution(["go","gone","guild"]));
// console.log(solution(["abc","def","ghi","jklm"]));
// console.log(solution(["word","war","warrior","world"]));

console.log(solution(["aaaa","aaaaaaaaaaaaabbbbb","v"]));
