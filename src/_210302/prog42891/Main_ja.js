/**
 * [ 무지의 먹방 라이브 ]
 * 먹어야 할 N개의 음식
 * 섭취 시, 일정 시간 소요
 * 
 * - 1번부터 1초 동안 섭취
 * - 마지막 음식 섭치후, 1번 음식이 다시 옴
 * - 다음 음식 : 남은 음식 중, 다음으로 섭취해야 할 가장 가까운 번호의 음식
 * 
 * K초 후, 잠시 방송 중단
 * 그 후, 몇 번 음식부터 섭취해야하는지.
 * 
 * 더 섭취해야 할 음식이 없다면 -1 반환
 * 
 * 효율성
 * time < 200,000
 * N < 100,000,000
 * k < 2 x 10^13
 */
function solution(food_times, k) {
    let answer = -1;
    let N = food_times.length; 

    const foodList = {}; 
    for (let i = 0; i < N; i++) {  // 섭취시간대 별로 food 분류 { time1 : [food1, food2]}
        if(!foodList.hasOwnProperty(food_times[i]))
            foodList[food_times[i]] = [i];
        else
            foodList[food_times[i]].push(i);
    }
    const sortTime = Object.keys(foodList); // 섭취시간 오름차순 -> 객체 순서는 key 값으로 오름차순 (삽입순 x)

    // (다음 섭취시간 - 이전섭취시간) * 남은 음식수 만큼 k 감소할거임 -> 한 회전에 남은 음식 수 만큼의 시간이 걸리니까.
    let preTime = 0;
    for (let i = 0, len = sortTime.length; i < len; i++) {
        let time = sortTime[i]-preTime;
        preTime = sortTime[i];
        if(k==0 || k < N*time){ // k==0이거나, 남은 시간내에 다음 음식을 다 못먹을 경우
            if(k>=N) k %= N; // 가능한 회전은 다 돌고 남은 값만 계산할거임 
            for (let j = 0; j < food_times.length; j++) { 
                if(food_times[j]>=sortTime[i] && 0==k--){ // 해당 섭취시간에 먹을 수 있는 음식만 check
                    answer = j+1;
                    break;
                }
            }
            break;
        }
        k -= N*time;
        N -= foodList[sortTime[i]].length;
    }
    return answer;
}

// console.log(solution([3, 1, 2], 5))