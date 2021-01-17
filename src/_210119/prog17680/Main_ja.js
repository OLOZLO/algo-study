/*
[ 캐시 - 캐시 크기에 따른 실행시간 측정 프로그램 ]

[ 입력 ]
- cacheSize (0~30) : 캐시 크기
- cities (~100,000) : 도시이름 배열
    => 영문자로 구성 (공백, 숫자, 특수문자 등 X)
    => 대소문자 구분 X
    => 이름은 최대 20자

[ 상세 조건 ]
- 캐시 교체 알고리즘 : LRU(Least Recently Used) 
    => 가장 오랫동안 참조되지 않은 페이지 교체
    => 과거의 데이터를 바탕으로 가장 오랜 기간 사용되지 않은 페이지 교체
- cache hit 일 경우, 실행시간 == 1 (페이지가 캐시에 있는 경우)
- cache miss 일 경우, 실행시간 == 5 (페이지가 캐시에 없는 경우)

[ Result ]
- 입력된 도시이름 배열을 순서대로 처리할 때, 총 실행시간 출력


[ 풀이 ]
(1) cacheSize 크기의 배열(cache) 생성
(2) IF. 도시 이름이 cache 배열에 없으면?
        answer(실행시간) +=5;
    ELSE. 있다면?
        answer+=1;
(3) 우선 순위 변경 (숫자 높을수록 우선순위 상위)
    * 캐시크기 최대 30, 도시이름 최대 100,000 이니까 단순하게 가도 될듯
    - 객체로 저장 
        => 도시이름(key) : 우선순위(value) -> 우선순위 낮은 것 부터 delete
*/


function solution(cacheSize, cities) {
    var answer = 0;
    let cache = {};
    if(cacheSize == 0)
        return cities.length * 5;
    for (let i = 0; i < cities.length; i++) {
        let city = cities[i].toUpperCase();

        // 캐시에 도시이름(key) 속성이 있으면 hit (+1)
        if(cache.hasOwnProperty(city)){
            answer++;
        }else{
            // 캐시가 꽉 찼을 경우
            if(Object.keys(cache).length == cacheSize){
                let min = i;
                let minKey = '';
                // 우선순위 낮은 key 찾기
                for (const key in cache) {
                    if(cache[key] < min){
                        min = cache[key];
                        minKey = key;
                    }
                }
                // 삭제
                delete cache[minKey];
            }
            // miss (+5)
            answer+=5;
        }
        // 캐시에 { 도시이름 : 우선순위 } 객체 저장
        cache[city] = i;
    }    
    return answer;
}

solution(3,["Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"]);
solution(2,["Jeju", "Pangyo", "NewYork", "newyork"]);