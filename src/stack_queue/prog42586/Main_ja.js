/**
 * [ 기능개발 - 배포 별로 몇 개의 기능이 배포되는가 ]
 * - 배포는 하루에 한 번만 가능
 * - 이전의 기능이 완성된 상태가 아니면, 배포 불가능
 */

function solution(progresses, speeds) {
    let workDay = [];
    for (let i = 0, len = progresses.length; i < len; i++) {
       let work = 100-progresses[i];
       workDay.push(Math.ceil(work/speeds[i]));
    }
    var answer = [];
    let day = workDay[0];
    answer.push(1);
    for (let i = 1, len = workDay.length; i < len; i++) {
        if(day >= workDay[i]){
            answer[answer.length-1]+=1;
        }else{
            answer.push(1);
            day = workDay[i];
        }
    }
    return answer;
}

console.log(solution([93,30,55],[1,30,5]));