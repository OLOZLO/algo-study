/**
 * [ 추석 트래픽 - 초당 최대 처리량 ]
 * - 1초 각 구간의 처리량 == 1초 각 구간의 로그 처리 개수
 * - 15일만 체크
 * 
 * N (1~2000) : 로그 개수
 * S : 응답완료시간
 * T : 처리시간 (0.001 ~ 3.000)
 * - "2016-09-15 20:59:57.421 0.351s"
 * -> (2016-09-15 20:59:57.421 - 0.351s + 0.001) ~ 2016-09-15 20:59:57.421
 */

function solution(lines) {
    const list = [];
    let point = 0;
    var answer = 1;
    lines.forEach(el => {
        const line = el.split(' ');

        let idx = 2;
        // (1) 초 단위로 바꿔서 저장
        let endTime = line[1].split(':').reduce((acc,value) =>
            acc + (value*(60**idx--)),0);
        
        let startTime = (endTime - (Number(line[2].replace(/[s]/,'')) - 0.001)).toFixed(3);
        
        let range = endTime - 3;
        // 추가할 log1과 저장된 log2가 있어
        // (2) log1.startTime이 log2.endTime+1에 포함되면 그 구간의 로그 처리갯수(cnt)++
        for (let i = point; i < list.length; i++) {
            let log = list[i];

            // 시간 줄이고 싶어서 추가한 구간
            // 추가할 log1의 3초전의 (log2+1)은 더 이상 확인할 필요없으니까 point로 체크
            if(range > log.endTime+1) {
                point++;
                continue;
            }
            if((log.endTime+1) > startTime){
                log.cnt++;
                answer = Math.max(answer,log.cnt);
            }
        }
        list.push({endTime, startTime, cnt:1});
    });
    console.log(list);
    return answer;
}

// console.log(solution([
//     "2016-09-15 01:00:04.001 2.0s",
//     "2016-09-15 01:00:07.000 2s"
//     ]));

console.log(solution( [
    "2016-09-15 01:00:04.002 2.0s",
    "2016-09-15 01:00:07.000 2s"
    ]))
// console.log(solution([
//     "2016-09-15 20:59:57.421 0.351s",
//     "2016-09-15 20:59:58.233 1.181s",
//     "2016-09-15 20:59:58.299 0.8s",
//     "2016-09-15 20:59:58.688 1.041s",
//     "2016-09-15 20:59:59.591 1.412s",
//     "2016-09-15 21:00:00.464 1.466s",
//     "2016-09-15 21:00:00.741 1.581s",
//     "2016-09-15 21:00:00.748 2.31s",
//     "2016-09-15 21:00:00.966 0.381s",
//     "2016-09-15 21:00:02.066 2.62s"
//     ]));