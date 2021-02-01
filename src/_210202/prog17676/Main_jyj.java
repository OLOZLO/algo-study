package _210202.prog17676;

import java.util.*;

public class Main_jyj {
    public int solution(String[] lines) {
        Log[] logs = new Log[lines.length];
        int max =0;
       
        // 각 로그들에 대한 시작점과 끝점을 구하기 위한
        for (int i = 0; i < lines.length; i++) {
            String time = lines[i].split(" ")[1];
            long end = Integer.parseInt(time.split(":")[0]) * 60 * 60 * 1000;
            end += Integer.parseInt(time.split(":")[1]) * 60 * 1000;
            end += (int)(Double.parseDouble(time.split(":")[2]) * 1000);
            long during = (int)(Double.parseDouble(lines[i].split(" ")[2].replace("s", ""))*1000);
            long start = end - during + 1;
            Log log = new Log(start, end);
            logs[i] = log;
        }
        // 시작점 기준
        for(int i=0; i<logs.length; i++){
            long startSec = logs[i].start;
            long endSec = startSec +1000;
            int cnt=0;
            // 로그들이 범위 안에 들어오는지 판단
            for(int j=0; j<logs.length; j++){
                // 범위안에 들어오면 카운트 1 증가
                if(logs[j].start>=startSec && logs[j].start <endSec)
                    cnt++;
                else if(logs[j].end >= startSec && logs[j].end < endSec)
                    cnt++;
                else if(logs[j].start < startSec && logs[j].end >= endSec)
                    cnt++;
            }
            if(max < cnt)
                max = cnt;
        }
        // 끝점 기준
        for(int i=0; i<logs.length; i++){
            long startSec = logs[i].end;
            long endSec = startSec +1000;
            int cnt=0;
            // 로그들이 범위 안에 들어오는지 판단
            for(int j=0; j<logs.length; j++){
                // 범위안에 들어오면 카운트 1 증가
                if(logs[j].start>=startSec && logs[j].start <endSec)
                    cnt++;
                else if(logs[j].end >= startSec && logs[j].end < endSec)
                    cnt++;
                else if(logs[j].start < startSec && logs[j].end >= endSec)
                    cnt++;
            }
            if(max < cnt)
                max = cnt;
        }

        return max;
  }
    
    
    
    public static class Log {
        long start;
        long end;

        public Log(long start, long end) {
            this.start = start;
            this.end = end;

        }
    }
}
