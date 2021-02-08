package _210209.prog17685;

public class Main_ms {
    public static int solution(String[] words) {
        Trie trie = new Trie();
        int answer = 0;

        for (String word : words)
            trie.insert(word); // words 안의 문자열들 다 트라이에다가 추가!

        for (String word : words)
            answer += trie.find(word); // 각 단어들을 트라이에서 찾자! -> 여기서 "입력해야 할 문자의 수"를 구함.

        System.out.println(answer);

        return answer;
    }

    public static int ctoi(char ch) {
        return ch - 'a';
    }

    public static class Trie {
        TrieNode root = new TrieNode();

        // 트라이에 문자열을 "추가"하는 함수.
        public void insert(String str) {
            TrieNode current = root; // 커런트를 루트로 먼저 두고,

            // 입력받은 문자열을 구성하는 문자들을 하나씩 순회!
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                int idx = ctoi(ch);     // 문자를 숫자로 바꿔서(a-z -> 0-25) 배열 인덱스로 사용할 것.

                if (current.children[idx] == null) {
                    current.children[idx] = new TrieNode(); // 인덱스 해당하는 노드 객체가 생성 안되어있으면, 만들어 준다.
                }

                current.children[idx].value = ch;   // value에 문자 넣고,
                current.children[idx].cnt++;        // cnt를 증가시킨다! -> 트라이에 각 문자가 몇 번 사용되었는지 체크하기 위함. 이후에 find 메소드에서 이걸 갖고 자동 완성 흉내 낼 거임.

                current = current.children[idx];    // 커런트를 자식으로 바꿔 끼우고 다음 문자부터 위 과정 반복.
            }

            current.isTerminal = true; // 마지막 문자에 해당하는 노드는, 단말 표시르 해준다.
        }

        // 트라이에 문자열을 "찾는" 함수.
        public int find(String str) {
            TrieNode current = this.root;
            int cnt = 0; // 각 단어별로, 몇 개의 문자를 입력해야 되는지를 체크하기 위한 카운트 변수.

            for (int i = 0; i < str.length(); i++) {
                cnt++; // 문자 하나씩 넘어갈 때마다 cnt 증가 -> 하나씩 "입력"하는 걸로 침.

                char ch = str.charAt(i);
                int idx = ctoi(ch);

                if (current.children[idx].cnt == 1)
                    return cnt; // 현재 노드의 자식이 하나면? 계속 진행해서 만들어 지는 문자열은 "유일"할 것임. 따라서 뒤 문자들은 "입력" 안해도 되므로, 여기까지만 입력한 카운트를 리턴하자!

                current = current.children[idx]; // 커런트를 자식으로 바꿔 끼우고 다음 문자부터 위 과정 반복.
            }

            // 단말 노드까지 도달해야 하는 경우 = 모든 문자를 모두 "입력"해야 하는 경우
            if (current != null && current.isTerminal)
                return cnt; // 걍 지금까지 센 cnt( =str.length() ) 를 리턴하자..

            else return -1; // * if ~ else * 리턴 맞춰주기 위한 의미 없는 리턴임.
        }
    }

    // 트라이 노드 정의 클래스
    public static class TrieNode {
        TrieNode[] children = new TrieNode[26]; // 알파벳 소문자들로 이뤄짐 -> 0~25(26개)의 인덱스 사용
        int cnt = 0;                            // 트라이에 문자열을 추가할 때, 각 노드가 몇 번 추가되었는 지 셀 때 사용.
        boolean isTerminal = false;             // 노드가 단말인지를 판별하는 데에 사용.
        char value;                             // 실제 문자 값이 저장되는 변수.
    }

    public static void main(String[] args) {
        solution(new String[]{"go", "gone", "guild"});
//        solution(new String[]{"abc", "def", "ghi", "jklm"});
//        solution(new String[]{"word", "war", "warrior", "world"});
    }
}
