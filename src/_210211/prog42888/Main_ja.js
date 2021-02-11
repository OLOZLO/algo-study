/**
 * [ 오픈채팅방 ]
 * 닉네임을 변경할 때, 기존에 채팅방에 출력되어 있던 메시지의 닉네임도 전부 변경
 */

function solution(record) {
    const log = []; // 들어왔는지, 나갔는지에 대한 데이터 저장 [ ["Enter | Leave", "아이디"] ]
    const nick = {}; // 아이디별 닉네임 저장 { "아이디", "닉네임" }
    for (let i = 0, len = record.length; i < len; i++) { // record 분리
        let re = record[i].split(" ");
        if(re[0] == 'Enter' || re[0] == 'Leave'){
            log.push([re[0],re[1]]);
        }
        if(re[0] == 'Enter' || re[0] == 'Change'){
            nick[re[1]] = re[2];
        }
    }
    const answer = [];
    for (let i = 0, len = log.length; i < len; i++) {
        if(log[i][0]=='Enter')
            answer.push(nick[log[i][1]]+"님이 들어왔습니다.")      
        else if(log[i][0]=='Leave')
            answer.push(nick[log[i][1]]+"님이 나갔습니다.")      
    
    }
    return answer;
}

console.log(solution(["Enter uid1234 Muzi", "Enter uid4567 Prodo","Leave uid1234","Enter uid1234 Prodo","Change uid4567 Ryan"]));
