package _210309.prog60058;

import java.util.ArrayDeque;

public class Main_1n9yun {
    public String solution(String p) {
        return solve(p);
    }

//    시키는대로 하기
    public String solve(String s){
        if(s.isEmpty()) return s;

        int open = 0;
        int close = 0;
        int idx = 0;
        ArrayDeque<Character> stack = new ArrayDeque<>();
        boolean flag = true;
        for(idx=0;idx<s.length();idx++){
            if(s.charAt(idx) == '(') {
                open++;
                stack.addLast(s.charAt(idx));
            } else if(s.charAt(idx) == ')') {
                close++;
                if(stack.isEmpty() || stack.getLast() != '(') flag = false;
                else if(stack.getLast() == '(') stack.removeLast();
            }
            if(open == close) break;
        }
        String u = s.substring(0, idx+1);
        String v = s.substring(idx+1);

        if(flag){
            return u + solve(v);
        }else{
            StringBuilder ret = new StringBuilder("(" + solve(v) + ")");
            for(int i=1;i<u.length()-1;i++) ret.append(u.charAt(i) == '(' ? ')' : '(');
            return ret.toString();
        }
    }
}
