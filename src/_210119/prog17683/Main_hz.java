package _210119.prog17683;

public class Main_hz {
	
	public static void main(String[] args) {
		System.out.println(solution("ABCDEFG",new String[] {"12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF"}));
		System.out.println(solution("CC#BCC#BCC#BCC#B",new String[] {"03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"}));
		System.out.println(solution("ABC",new String[] {"12:00,12:14,HELLO,C#DEFGAB", "13:00,13:05,WORLD,ABCDEF"}));
		System.out.println(solution("A#",new String[] {"13:00,13:02,HAPPY,C#A#"}));
		System.out.println(solution("CDEFGAC",new String[] {"12:00,12:06,HELLO,CDEFGA"}));
		System.out.println(solution("CCB",new String[] {"03:00,03:10,FOO,CCB#CCB", "04:00,04:08,BAR,ABC"}));
	}
	
	public static String solution(String m, String[] musicinfos) {
        String answer = "";
        int ptime = 0;
        
        // # 붙은 애들 걸리적거려서 소문자로 바꿔줍니다
        m = m.replaceAll("C#", "c").replaceAll("D#", "d").replaceAll("F#", "f").replaceAll("G#", "g").replaceAll("A#", "a");
        
        for (int i = 0; i < musicinfos.length; i++) {
        	String[] info = musicinfos[i].split(",");
        	// 무식하게 해당 노래가 몇분동안 재생되는지 끝나는 시간-시작시간을 분단위로 바꿔서 계산했습니다
        	int time = Integer.parseInt(info[1].substring(0, 2))*60 + Integer.parseInt(info[1].substring(3, 5));
        	time -= (Integer.parseInt(info[0].substring(0, 2))*60 + Integer.parseInt(info[0].substring(3, 5)));
        	
        	// 라디오에서 나온 아이들도 # 붙은 음정을 소문자로 바꿔줍니다
        	info[3] = info[3].replaceAll("C#", "c").replaceAll("D#", "d").replaceAll("F#", "f").replaceAll("G#", "g").replaceAll("A#", "a");
        	
        	String melodi = "";	// 인덱스 계산하기 귀찮아서 재생시간동안 나오는 음악들을 미리 구했습니다.
        	for (int j = 0; j < time/info[3].length(); j++) {
        		melodi += info[3];
        	}
        	melodi += info[3].substring(0, time%info[3].length());
        	
        	for (int t = 0; t < melodi.length()-m.length()+1; t++) {	// t는 라디오에서 나온 노래들의 인덱스
        		for (int j = 0; j < m.length(); j++) {	// j는 내가 기억하고 있는 멜로디의 인덱스 
        			if (m.charAt(j) != melodi.charAt(t+j))
        				break;
        			
        			// 일치하는 음악이 여러개일때를 위해 (ptime은 이전에 일치하는 음악이 있었다면 그 음악의 재생시간)
        			if (j == m.length()-1 && ptime < time) {
        				ptime = time;
        				answer = info[2];
        			}
        		}
        	}
        }
        
        return ptime == 0 ? "(None)" : answer;
    }

}
