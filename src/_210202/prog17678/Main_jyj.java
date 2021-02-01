package _210202.prog17678;

import java.util.*;

public class Main_jyj {
    public String solution(int n, int t, int m, String[] timetable) {
        String answer = "";
        LinkedList<Integer> list = new LinkedList<Integer>();
    
        int lastBus = t * (n - 1) + 60*9; //마지막 버스 시간
        int possible = m; // 몇명이 탈 수 있나
        int nowbus = 0; // 현재 버스 번호
        int BoardingTime = 60 * 9; // 버스탑승시간
        int nowPer = 0; // 남아 있는 크루
        int nowTime = 0; // 마지막 크루가 버스 탄 시간
        int bye = 0; // 제일 늦게 도착하는 시간
    
        // 막차 이후에 오는 크루 제거
        for (int i = 0 ; i < timetable.length; i++) {
          String[] s = timetable[i].split(":");
          int tmp = Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1]);
          if (tmp <= lastBus) list.add(tmp);
        }
          list.sort(new Comparator<Integer>() {
          @Override
          public int compare(Integer arg0, Integer arg1) {
            if (arg0 > arg1) return 1;
            if (arg0 < arg1) return -1;
            return 0;
          }
        });
    
    
    
        // 버스에 태울 수 있다면
        loop1:
        for (int i = 0; i < n; i++) {
          BoardingTime = 60*9 + i * t;
          loop2:
          for (int j =0; j < m; j++) {
            // 탈 크루가 없다.
            if (list.isEmpty()) {
              nowbus = i+1;
              possible = j;
              break loop1;
            }
    
            // 탈 크루가 있다면 버스에 태운다.
            nowTime = list.getFirst();
            if (BoardingTime >= nowTime) {
              nowTime = list.poll();
              System.out.println(nowTime);
            }
          }
          possible = m;
          nowbus = i+1;
        }
        nowPer = list.size();
        System.out.println(list.size());
          
          
        // 대기자가 남아있는지 판단
        if (nowPer > 0) {
          //대기자가 있다면 마지막 탄 사람 -1분
          bye = nowTime - 1;
        } else { 
          // 대기자가 없고, 버스가 남아있나?
          if (n - nowbus > 0) {
            // 버스가 남아있다면 마지막 출발시간
            bye = lastBus;
          } 
          else { // 버스가 없다면, 빈자리는 있나?
            if( m - possible > 0) {
              // 빈자리가 있다면 마지막 출발시간 
              bye = lastBus;
            } else { //빈자리 없다면 마지막 탄 사람 -1분
              bye = nowTime - 1;
            }
          }
        }
        int hour = (bye / 60);
        int min = (bye % 60);
        answer = (hour > 9 ? hour : "0"+hour) + ":" + (min > 9 ? min : "0"+min);
        return answer;
  }

}
