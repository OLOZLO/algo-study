
function solution(str1, str2) {
    str1 = str1.toUpperCase();
    str2 = str2.toUpperCase();
    
    let union = [], common = [];
    for(let i=0, len=str1.length; i<len; i++){
        let c1 = str1[i], c2 = str1[i+1];
        if(isChar(c1) && isChar(c2)) {
            union.push(c1+c2);
        }
    } 

    let len1 = union.length;
    for(let i=0, len2=str2.length-1; i<len2; i++){ 
        let c1 = str2[i], c2 = str2[i+1];
        if(!isChar(c1) || !isChar(c2)) continue;
        let c = c1+c2;
        
        let j = 0;
        for (j = 0; j < len1; j++) {
            if(common[j]) continue;
            if(c===union[j]){
                common[j] = c;
                break;
            }           
        }
        if(j==len1)
            union.push(c);
        
    }
    let commonLen = 0;
    for (const c of common) {
        if(c) commonLen++;
    }
    var answer = commonLen==0&&union.length==0?1:commonLen/union.length;
    return Math.floor(answer*65536);
}

function isChar(c){
    if((c>='A'&&c<='Z') || (c>='a'&&c<='z')) return true;
    return false;
}

console.log(solution('FRANCE','french'));
console.log(solution('handshake','shake hands'));
console.log(solution('aa1+aa2','AAAA12'));
console.log(solution('E=M*C^2','e=m*c^2'));