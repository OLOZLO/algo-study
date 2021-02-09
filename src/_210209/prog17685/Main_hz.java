package _210209.prog17685;

import java.util.Arrays;

public class Main_hz {

    public static void main(String[] args) {
        System.out.println(solution(new String[]{"go", "gone", "guild"}));
        System.out.println(solution(new String[]{"abc", "def", "ghi", "jklm"}));
        System.out.println(solution(new String[]{"word", "war", "warrior", "world"}));
    }
    /*
    * 제 아이디어는 이거였습니다~ (3번째 테케 예시)
    * 1. 정렬 war warrior word world
    * 2. 첫 단어만 w w w w
    * 3. 첫번째꺼 검사 -> 같은거 존재 -> 한 글자씩 추가 wa wa wo wo
    * 4. 첫번째 다시 검사 -> 같은거 존재 -> 같은 것 한 글자씩 추가 war war wo wo
    * 5. 첫번째 다시 검사 -> 같은거 존재 -> 같은 거 한 글자씩 추가 war warr wo wo (이 때 첫번째 단어 원본 단어랑 같아서 추가 안함!)
    * 6. 두번째꺼 검사 -> 같은거 없어
    * 7. 세번째꺼 검사 -> 같은거 존재 -> 한 글자 추가 war warr wor wor
    * 8. 세번째 다시 -> 같은거 있어 -> 글자 추가 war warr word worl
    * 9. 세번째 다시 -> 같은거 없어
    * 10. 끝!
    * */

    public static int solution(String[] words) {
        int answer = 0;

        Arrays.sort(words); // 정렬하면 학습 단어 만들기 쉬우니까!
        String[] search = new String[words.length]; // 학습된 단어들 저장 배열

        for (int i = 0; i < words.length; i++) {    // 모든 단어들의 첫 글자만 저장해놓습니다. 
            search[i] = words[i].substring(0, 1);
        }

        for (int i = 0; i < words.length-1; i++) {  // 단어들이 정렬이 되어있는 상태이기 때문에 내 이후 단어들이랑만 비교할꺼야

            if (search[i].equals(search[i+1])) {    // 만약 내 이후에 나랑 같은 단어가 존재하면
                String s = search[i];

                for (int j = i; j < words.length; j++) {    // 나를 포함해서 같은 단어들 다 한 글자씩 추가!
                    if (s.equals(search[j])) {
                        if (!search[j].equals(words[j]))
                            search[j] = words[j].substring(0, s.length()+1);
                    } else  break;
                }
                i--;    // 한글자 늘렸는데도 뒤 단어랑 같나 확인하기 위해서! (현재 단어 다시 검사)
                continue;
            }
        }

        for (int i = 0; i < search.length; i++)
            answer += search[i].length();
        
        return answer;
    }
}
