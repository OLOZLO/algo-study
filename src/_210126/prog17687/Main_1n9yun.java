package _210126.prog17687;

public class Main_1n9yun {
    public String solution(int n, int t, int m, int p) {
        StringBuilder answer = new StringBuilder();
        StringBuilder str = new StringBuilder();

        int num = 0;
        for(int i=0;i<t;i++){
//            내 순서까지 str이 안만들어져있으면 n진법 변환 후 이어 붙인다.
            while(str.length()-1 < p-1) str.append(getnBase(num++, n));
//            내 차례에서 말하기
            answer.append(str.charAt(p-1));
//            다음 차례는 몇번째?
            p += m;
        }
        return answer.toString();
    }

    StringBuilder getnBase(int num, int n){
        StringBuilder sb = new StringBuilder();

        while(num / n >= 0){
            int res = num % n;
            if(res >= 10) sb.append((char)('A' + (res - 10)));
            else sb.append(res);

            num /= n;
            if(num == 0) break;
        }

        return sb.reverse();
    }
}
