const solution = (msg) => {
    var answer = [];
    let map = new Map();
    
    let count = 1;
    for(let idx = 65; idx <= 90; idx++) {
        map.set(String.fromCharCode(idx), count++);
    }

    let start = 0;    
    while(start < msg.length) {
        let end = start;
        
        while(end + 1 <= msg.length && map.has(msg.substring(start, end + 1))) {
            end++;
        }

        answer.push(map.get(msg.substring(start, end)));

        if(end < msg.length) {
            map.set(msg.substring(start, end+1), count++);
        }

        start = end;
    }

    return answer;
};

solution("KAKAO");
// solution("TOBEORNOTTOBEORTOBEORNOT");
// solution("ABABABABABABABAB");
