package _210309.prog60057;

public class Main_jyj {

	public int solution(String s) {

		int answer = s.length();
        //반복되는 길이의 패턴을 찾아서 "반복개수 패턴"
        //가장 짧은 압축방법 찾아서 길이 반환
        String temp;
        int compressLength;
        int repeatCount;
        for(int i = 1; i <= s.length() / 2; i++){//길이의 절반을 넘는 패턴은 1밖에 나올 수 없음
            temp = "";
            compressLength = 0;
            repeatCount = 0;
            for(int j = 0; j < s.length(); j += i){
                String current;
                if(j+i >= s.length()){
                    current = s.substring(j, s.length());
                } else {
                    current = s.substring(j, j+i);
                }

                if(temp.equals(current)){//이전 단위가 같은 것이 있는지 확인
                    repeatCount++;
                    if(repeatCount == 1) compressLength++;
                    if(repeatCount == 9) compressLength++;
                    if(repeatCount == 99) compressLength++;
                    if(repeatCount == 999) compressLength++;
                } else {
                    compressLength += current.length();
                    repeatCount = 0;
                }
                temp = current;
            }
            if(answer > compressLength) answer = compressLength;
        }
        return answer;
	}
}
