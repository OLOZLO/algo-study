/**
 * [ 파일명 정렬 - 파일명에 포함된 숫자를 반영한 정렬 기능]
 * 
 * file name (~100) 
 * - 영문 대소문자, 숫자, 공백, 마침표("."), 빼기("-")
 * - 영문자로 시작, 숫자를 하나 이상 포함
 * 
 * - HEAD, NUMBER, numberLen 세 부분으로 구성
 *   [HEAD]
 *      - 숫자가 아닌 문자로 이루어짐
 *      - 최소 한 글자 이상
 *      [ 정렬 기준 ]
 *      -> 사전 순
 *      -> 대소문자 구분 X   
 * 
 *   [NUMBER]
 *      - 1~5글자(0~99999)
 *      - 앞쪽에 0이 올 수 있음 EX)00000, 0101
 *      [ 정렬 기준 ]
 *      -> HEAD가 같을 경우, NUMBER의 숫자 순으로 정렬
 *      -> 9 < 10 < 0011 < 012 < 13 < 014 순으로 정렬
 * 
 *   [numberLen]
 *      - 숫자가 있거나, 빈 문자열이거나
 *      [ 정렬 기준 ]
 *      -> HEAD, NUMBER 둘 다 같을 경우, 입력 순으로
 * 
 * - files (~1000)
 * - 완전히 동일한 파일명은 없음 (대소문자, 숫자 앞부분(0) 차이)
 * 
 * [ 풀이 ]
 * (1) Files 순회 - head와 number 찾아 새로운 배열에 객체로 저장
 *     ① head와 number의 경계선 찾기 (변수 boundary)
 *       [EX] img12.png => img와 12의 경계선(index)
 *       => String.property.search와 정규식(숫자 찾기) 사용
 *          TIP. search 함수는 indexOf 함수와 유사함 -> 조건에 맞는 첫 번째 문자열의 인덱스를 반환 [EX]'abcbb'에서 'b'를 찾으면, 1을 반환 (0부터 시작)
 *               다른 점은 search는 정규식 사용이 가능함.
 * 
 *     ② head 찾기
 *       => 원본 파일명.substring(0,boundary);
 *       => 대문자로 변환
 *     ③ number 찾기
 *       => 원본 파일명.substr(boundary,number 길이); 
 *       => number 길이 : head를 제외한 파일명에서 search와 정규식(문자 찾기) 사용해서 number의 끝 인덱스를 찾음
 *          [EX] "img12.png" => "12.png".search(문자 찾기) ==> 2 반환 
 *       TIP. substring과 substr의 차이
 *            substring(0,2)은 인덱스 0에서 인덱스 2까지 자르는 것
 *            substr(0,2)은 인덱스 0에서 2개를 자르는 것
 *     
 *     BUT!!!! 새롭게 짠 정규식은 한번에 head와 number를 찾을 수 있음!
 * 
 *     ④ 찾은 head와 number를 객체로 묶어서 배열에 저장 (reFiles)
 * 
 * (2) reFiles를 재정의한 sort로 정렬
 *       IF. head가 다르면, head 비교해서 정렬
 *       ELSE. number 비교해서 정렬
 * 
 * (3) 정렬된 reFiles의 순서대로 원본 파일들(files)에서 찾아 answer에 저장 (reFiles의 index 활용)
 */

function solution(files) {
    let regExp =/(?<head>\D+)(?<number>\d+)(.*)/;
    
    let reFiles = [];
    let idx = 0;
    files.forEach(file => {
        // (1) head와 number 분류
        let fileName = regExp.exec(file);
        let head = fileName.groups.head.toUpperCase();
        let number = fileName.groups.number;
        // 새로운 배열(reFiles)에 저장 
        reFiles.push({idx,head,number});  // TIP. 객체 저장시 key와 value에 할당한 변수명이 같을 경우, key 생략 가능 (ES2015) [ex] {index:index} => {index}
        idx++;
    });
    // (2) sort 정렬 기준 변경  TIP.객체의 value값 접근은, object.key 혹은 object[key]로 접근 가능
    reFiles.sort((f1,f2)=>{
        if(f1['head'] != f2['head']){
            return f1['head'].localeCompare(f2['head']);
        }else{
            return f1['number']-f2['number']; // 01과 1 때문에 localeCompare 사용할 수 없음
        }
    });
    // TIP. localeCompare()는 문자열 비교 함수
    //      BUT. 비교연산자 사용해서 return 1 or -1 하는 코드가 localeCompare()을 썼을 때보다 좀 더 빠름 => 10ms이상으로 차이나는데 왜인지는...:(
    
    var answer = [];
    reFiles.forEach(file => {   // (3) reFiles의 순서대로 원본 파일들(files)에서 찾아 answer에 저장
        answer.push(files[file.idx]);
    })
    return answer; 
}

// console.log(solution(["img12.png", "img10.png", "img02.png", "img1.png", "IMG01.GIF", "img2.JPG"]));
console.log(solution(["F-5 Freedom Fighter", "B-50 Superfortress", "A-10 Thunderbolt II", "F-14"]));


/** 원래는, 그룹화 안한 정규식 사용 */
/*
    let regExpNumber = /[0-9]/   // 숫자 확인 정규식
    let regExpChar = /[^0-9]/  // 문자 확인 정규식 (^기호를 넣으면 숫자가 아닌 것을 찾음)

    let reFiles = [];
    let idx = 0; // file 인덱스
    files.forEach(file => {
        let boundary = file.search(regExpNumber); // (1)-1 head와 number의 경계선
        let head = file.substring(0,boundary).toUpperCase(); // (1)-2 head 찾기
        let numberLen = file.substring(boundary).search(regExpChar); // (1)-3 number 길이 찾기
        let number = numberLen==-1?file.substring(boundary):file.substr(boundary,numberLen); // (1)-3 number 찾기 (주의)tail이 빈 값인 경우
        
        // 새로운 배열(reFiles)에 저장 
        reFiles.push({head,number});  // TIP. 객체 저장시 key와 value에 할당한 변수명이 같을 경우, key 생략 가능 (ES2015) [ex] {index:index} => {index}
    });
*/