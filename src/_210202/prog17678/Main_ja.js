/**
 * [ 셔틀버스 - 셔틀로 갈 수 있는 도착 시각 중 제일 늦은 시각 ]
 * 
 * [ 셔틀 ]
 * - 9시 시작
 * - n회, t분 간격 
 * - 최대 m명 탑승 가능
 *    => 대기 순서대로 탑승
 * 
 * 0 ＜ n ≦ 10
 * 0 ＜ t ≦ 60
 * 0 ＜ m ≦ 45
 * 
 * - timetable : 하루 동안 크루가 대기열에 도착하는 시각(HH:MM)
 * 
 * [ 콘 ]
 * - 대기열 맨 뒤에서 기다림
 * - 23:59에 집에 돌아감. => 다음날 셔틀을 타는 일 X
 * 
 * [ result ]
 * - 콘이 셔틀로 갈 수 있는 도착 시각 중 제일 늦은 시각 구하기
 * - HH:MM 형식 (00:00 ~ 23:59)
 * 
 * [ 풀이 ]
 * (1) 크루들 대기 시각(timetable)을 오름차순 정렬
 * 
 * (2) 셔틀 운행 시간 배열 저장 (crewInBus)
 *      { time:"9:00" , cnt:0 }
 *     => cnt : 운행시간별 탑승 크루 수 체크
 * 
 * (3) 콘의 탑승시간은 마지막 운행 시간으로 초기화
 * 
 * (4) 크루들 버스 탑승
 *     현 운행시간별로, 탑승 가능한 크루 체크
 *
 * (5) IF. 마지막 탑승 시간에 m명이 앉아있는 경우 콘 자리 확보
 *     FOR (총 탑승한 인원 - m) ~ (총 탑승한 인원) 만큼 순회
 *          con의 탑승시각 = 마지막 탑승 시간의 m번째 크루 도착시각-1 (m번째 크루보다 1분 일찍 가야함)
 *          IF(마지막 탑승 시간의 m번째 크루 도착시각 != (m-1)번째 크루 도착시각) break;
 *      
 */ 

function solution(n, t, m, timetable) {
    // 크루들 대기시간 순으로 정렬
    timetable.sort((crew1,crew2)=>{
        let time1 = crew1.split(':');
        let time2 = crew2.split(':');

        if(time1[0] != time2[0]){
            return time1[0] - time2[0];
        }else{
            return time1[1] - time2[1];
        }
    });

    timetable.forEach((time,idx) => { // 접근하기 쉽게 크루들 도착시각을 분 단위로 바꿔서 저장
        let HHMM = time.split(':');
        timetable[idx] = Number(HHMM[0]*60) + Number(HHMM[1]);
    });

    let startTime = 9*60; //"9:00" => 첫 차 시각도 분 단위로.
    let crewInBus = [{"time" : startTime, "cnt" : 0}]; // 운행시간별 크루 탑승 인원 

    let con = startTime; // 콘이 탑승할 시간
    for (let i = 1; i < n; i++) {
        startTime+=t;
        let crew = {
            "time" : startTime,
            "cnt" : 0
        };
        crewInBus.push(crew);

        if(i==n-1) con = startTime;
    }
    
    let totalCrewInBus = 0; // 몇 명이나 탔는지 체크

    // 현 운행 시간에 어떤 크루가 탈 건지 탐색 
    for (let i = 0; i < n; i++) { // 운행시간
        for (let j = totalCrewInBus, len = timetable.length; j < len; j++) { // 크루 도착시간
            if(crewInBus[i]["cnt"] == m) break; // 꽉찼으면 못참
            if(timetable[j] > crewInBus[i]["time"]) continue; // 못타는 버스면 pass
            crewInBus[i]["cnt"]++;
            totalCrewInBus++;
        }
    }
    
    // 마지막 탑승 시간인 경우, 콘은 게으름을 버려서라도 반드시 타야함!! -> 근데 이뇨속 게으름을 전부 포기하지 않았움. 으휴
    // 콘 자리 찾으러 가자.
    if(crewInBus[n-1]["cnt"] == m ){ // 마지막 탑승 시간에 m명이 앉아 있는 경우만
        let lastCreu = totalCrewInBus-1;
        for (let i = lastCreu; i>totalCrewInBus-m-1; i--){ // 남은 크루 중, m명까지만 체크
            con = timetable[i]-1;
            if(timetable[i] != timetable[i-1])  break;
        }
    }
 
    let regExp = /^\d$/;
    let HH = Math.floor(con/60);
    let MM = con%60;
    let answer = HH.toString().replace(regExp,'0$&') + ":" + MM.toString().replace(regExp,'0$&');
    return answer;
}

console.log(solution(1,1,5,["08:00", "08:01", "08:02", "08:03"]));
console.log(solution(2,10,2,["09:10", "09:09", '08:00']));
console.log(solution(2,1,2,["09:00", "09:00", '09:00', "09:00"]));
console.log(solution(10, 60, 45, ["23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59"]));