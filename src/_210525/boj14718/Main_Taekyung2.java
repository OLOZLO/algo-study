package _210525.boj14718;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main_Taekyung2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        int ret = Integer.MAX_VALUE;

        Soldier[] soldiers = new Soldier[N];
        for(int i = 0; i < N; i++)
            soldiers[i] = new Soldier(sc.nextInt(), sc.nextInt(), sc.nextInt());

        Arrays.sort(soldiers, Comparator.comparingInt(s -> s.intel));
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                int power = soldiers[i].power, speed = soldiers[j].speed;
                int cnt = 0;

                for(Soldier cur : soldiers) {
                    if (power >= cur.power && speed >= cur.speed)
                        cnt++;

                    if(cnt == K) {
                        ret = Math.min(ret, power + speed + cur.intel);
                        break;
                    }
                }
            }
        }
        System.out.println(ret);
    }

    static class Soldier {
        int power, speed, intel;

        Soldier(int power ,int speed, int intel) {
            this.power = power;
            this.speed = speed;
            this.intel = intel;
        }
    }
}
