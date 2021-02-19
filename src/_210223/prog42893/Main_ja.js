/**
 * [ 매칭점수 ]
 * 한 웹 페이지 -> 기본점수, 외부 링크 수, 링크점수, 매칭점수
 * - 기본 점수 : 검색어가 등장하는 횟수(대소문자 x)
 * - 외부 링크 수 : 다른 외부 페이지로 연결된 링크의 개수
 * - 링크 점수 : 해당 페이지로 링크가 걸린 웹 페이지의 기본점수 / 외부 링크 수의 총합
 * - 매칭 점수 : 기본점수와 링크점수의 합
 * 
 * [ 조건 ]
 * - url : head태그 내의 meta 태그의 값 <meta content="url">
 * - 외부 링크 : a태그 <a href>
 * - 모든 url은 https:// 로만 시작
 * - word : 검색어
 *   -> 완벽하게 동일해야함.
 *   -> 알파벳을 제외한 모든 문자는 구분자
 * 
 * [ Result ]
 * 매칭 점수가 가장 높은 웹페이지의 index
 * 여러개라면, 번호가 작은 것
 * 
 */
function solution(word, pages) {
    const urlInfo = {}; // 링크별 점수 a.com : [기본점수, 외부링크수]
    const linkedMyUrl = {}; // 해당 url에 링크한 url들 a.com: [b.com, c.com]

    const regExpUrl = /<meta property="og:url" content="https:\/\/(?<curUrl>.\S*)"/; // 왜 .*는 안되게 테케 만들었는지 정말 화가난다! ㅎㅎ!
    const regExpLink = /<a href="https:\/\/(?<linkUrl>.\S*)"/g;

    // (1) url별 기본점수, 외부링크수, 내가 링크한 url들 저장
    for (let i = 0, len = pages.length; i < len; i++) {
        let { curUrl } = regExpUrl.exec(pages[i]).groups; // 현재 url
        let externalLink = 0; // 현재 url의 외부링크수 담을 변수
        for (const page of pages[i].matchAll(regExpLink)) { // 현재 url에서 링크한 url 추출
            let  { linkUrl } = page.groups;
            if(linkedMyUrl.hasOwnProperty(linkUrl)) // 현재 url이 링크한 url에 현재 url 추가 [EX] (현재url)a.com이 b.com를 링크했으면, b.com : [a.com]
                linkedMyUrl[linkUrl].push(curUrl); 
            else   
                linkedMyUrl[linkUrl] = [ curUrl ];
            externalLink++;
        }
        // (2) word 검색
        //     (2)-1. tag 만나면 양끝에 줄바꿈 넣어준 다음 => <tag>\n문장\n<tag>
        //     (2)-2. tag는 제거하고 => \n문장\n
        //     (2)-3. 문장을 알파벳을 제외한 모든 문자(구분자)들로 분리(split) => .build0aaa -> [build, aaa]
        let matchWord = pages[i].replace(/(>)([^<]*)/gm,'$1\n$2\n').replace(/<(.*)>/g,'').split(/[^A-Za-z]/i);
        
        // (2)-4. (2)-3에서 문장을 분리한 배열을 순회해서 word 검색할거임 == 기본점수
        let defalutScore = 0;
        for (const w of matchWord) {
            if(!w || w.length > word.length) continue;
            if(w.search(new RegExp(word,'i'))!=-1) defalutScore++; // 미리 구분자로 분리해서 완전히 일치한지만 체크하면 됨
        }
        urlInfo[curUrl] = {defalutScore, externalLink}; // 기본점수, 외부링크 수 저장
    }

    // (3) 각각의 url 링크점수 및 매칭점수 구하기
    let answer = 0, max = -1; 
    let urlName = Object.keys(urlInfo); 
    
    for (let i = 0, len = urlName.length; i < len; i++) { 
        let curUrl = urlName[i]; // 내 url
        let matchScore = urlInfo[curUrl].defalutScore; // 내 url의 기본점수로 매칭점수를 초기화하고
        if(linkedMyUrl.hasOwnProperty(curUrl)){ // 나를 링크한 url들을 꺼내서
            let urls = linkedMyUrl[curUrl]; 
            for (let j = 0; j < urls.length; j++) { // 나를 링크한 url의 기본점수, 외부링크수를 가져와서 내 매칭점수에 계산
                let fromUrl = urlInfo[urls[j]]
                matchScore += fromUrl.defalutScore / fromUrl.externalLink;
            }
        }
        if(max<matchScore){  
            max = matchScore;
            answer = i;
        }
    }
    return answer;
}

console.log(solution("blind", ["<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\"><head>  <meta charset=\"utf-8\">  <meta property=\"og:url\" content=\"https://a.com\"/></head>  <body>Blind0Blind0BLIND Lorem Blind ipsum dolor Blind test sit amet, consectetur adipiscing elit<a href=\"https://b.com\">Link to b </a></body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\"><head>  <meta charset=\"utf-8\">  <meta property=\"og:url\" content=\"https://b.com\"/></head><body>Suspendisse potenti. Vivamus venenatis tellus non turpis bibendum<a href=\"https://a.com\">Link to a </a>blind sed congue urna varius. Suspendisse feugiat nisl ligula, quis malesuada felis hendrerit ut.\n<a href=\"https://c.com\"> Link to c </a></body></html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://c.com\"/>\n</head>  \n<body>\nUt condimentum urna at felis sodales rutrum. Sed dapibus cursus diam, non interdum nulla tempor nec. Phasellus rutrum enim at orci consectetu blind\n<a href=\"https://a.com\"> Link to a </a>\n</body>\n</html>"]));
// console.log(solution("Muzi", ["<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://careers.kakao.com/interview/list\"/>\n</head>  \n<body>\n<a href=\"https://programmers.co.kr/learn/courses/4673\"></a>#!MuziMuzi!)\njayg07con&&\n\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://www.kakaocorp.com\"/>\n</head>  \n<body>\ncon%\tmuzI92apeach&2<a href=\"https://hashcode.co.kr/tos\">0MUZI0MUZI0MUZII</a>\n\n\t^\n</body>\n</html>"]));
