/*
[ 방금그곡 - 조건에 맞는 음악 제목 구하기 ]

[ 입력 ]
- m (1 ~ 1439) : 네오가 기억한 멜로디  
- musicinfos (~100) : 방송된 곡의 정보를 담고 있는 배열 
    => ( 시작시각, 끝난시각, 음악제목(1~64), 악보정보(1~1439) )
    => 시간 : 24시간 HH:MN 형식
    => 악보음 : C, C#, D, D#, E, F, F#, G, G#, A, A#, B (12개)

[ 상세 조건 ]
- 각 음은 1분에 1개씩 재생
- 음악은 처음부터 재생 (00:00 넘어서 재생 X)
    IF. 음악 길이 < 재생된 시간 일 때, 재생 시간까지 반복 재생
    ELSE IF. 음악 길이 > 재생된 시간 일 때, 재생 시간만큼만 재생

[ result ]
- 조건과 일치하는 "음악 제목" 반환 
    - 악보에서 일치하는 부분 다음 음이 #인 경우, 불일치 (ex : 일치한 악보의 마지막 부분이 c로 끝났는데 다음 음이 #이다? c가 아닌 c#이므로 불일치) 
    IF. 여러 개면? MAX(재생된 시간) {
        IF. 재생된 시간도 같으면? MIN(입력순서)
    }
- 조건과 일치하는 음악이 없으면 "(None)" 반환

[ 풀이 ]
(1) 악보가 재생시간보다 짧을 경우, 이어붙이기 (반복)
(2) 재생시간만큼 악보 자르기
(3) 자른 악보에 멜로디(m)가 있는지 판단 
    IF. 있다 {
        음악을 answer에 저장하기 전,
        IF. 일치한 악보의 (마지막 부분 + 1) 음이 # 이면, 재탐색
        ElSE. {
            IF. answer에 저장된 음악이 있으면 재생 시간 비교{
                IF. 재생된 시간이 동일하면 MIN(입력순서)
                ELSE. 재생 시간이 긴 음악 저장
                => 즉, answer가 빈 경우랑 answer에 있는 음악보다 재생 시간이 긴 경우만 저장
            }
        }
    }
(4) musicinfos 전부 순회한 후,
    IF. answer에 값이 ''이면, (None) 반환
    ELSE. answer[2] (음악제목) 반환
*/

function solution(m, musicinfos) {
    var answer = '';
    for (let i = 0, tc = musicinfos.length; i < tc; i++) {
        const musicinfo = musicinfos[i].split(',');
        
        const startTime = musicinfo[0].split(':');
        const endTime = musicinfo[1].split(':');

        const playTime = (Number(endTime[0]*60) + Number(endTime[1]))-(Number(startTime[0]*60) + Number(startTime[1]));
        
        // (1) 악보가 재생시간보다 짧을 경우, 이어붙이기 (반복)
        // 재생시간 * 2 길이만큼 늘리자 (전부 #이 붙었다는 전제하에 -> 추후에 자르는 과정 거침)
        let music = musicinfo[3];
        while(playTime * 2 > music.length){
            music += musicinfo[3];
        }
        
        // (2) 재생시간만큼 악보 자르기
        let time = 0;
        musicinfo[3] = '';
        for(let m of music){
            if(m!='#') {
                time++;
            }
            musicinfo[3] += m;
            if(time == playTime) break;
        }
        // 재생시간까지 자르면 마지막에 음의 '#' 이 잘릴 수 있음. 확인 후, # 추가
        if(music.charAt(musicinfo[3].length) == '#') musicinfo[3]+="#";

        // (3) 수정한 악보에 멜로디(m)가 있는지 판단 -> 처음부터 끝까지 탐색
        // 찾은 인덱스의 다음 인덱스부터 재탐색
        let idx = 0;
        while(true){
            if(idx >= musicinfo[3].length) break;
            let findIdx = musicinfo[3].indexOf(m,idx);
            // 없으면, break
            if(findIdx == -1) break;
            // 있는데, 일치한 악보의 (마지막 부분 + 1) 음이 # 면 continue (a와 a# 주의)
            if(musicinfo[3].charAt(findIdx+m.length) == "#"){
                idx = findIdx+1;
                continue;
            }
            // answer에 저장된 음악이 없거나, answer에 있는 음악보다 재생 시간이 긴 경우만 저장 
            if(!answer || answer[4] < playTime){
                answer = musicinfo;
                answer[4] = playTime; // 재생시간 따로 저장
            }
            break;
        }
    }
    // (4) answer값 존재하면 음악제목 (answer[2]) 반환
    if(!answer)
        return "(None)";

    console.log(answer[2]);
    return answer[2];
}

// solution("ABCDEFG",["12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF"]);
// solution("CC#BCC#BCC#BCC#B", ["03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"]);
// solution("ABC", ["12:00,12:34,HELLO,C#DEFGABC#DEFGABABC", "13:00,13:05,WORLD,ABCDEF"]);
// solution("ABC", ["00:00,00:06,HI,ABC#ABC"]);
// solution("A#",["13:00,13:02,HAPPY,C#A#"]);
solution("CCB",["03:00,03:10,FOO,CCB#CCB", "04:00,04:18,BAR,CCB"]);