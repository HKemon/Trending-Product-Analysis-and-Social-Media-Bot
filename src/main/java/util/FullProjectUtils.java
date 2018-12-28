package util;

public class FullProjectUtils {
    public static void customSleep(int x){
        try {
            Thread.sleep(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void customSleep(int max, int min){
        int range = (max - min) + 1;
        try {
            Thread.sleep((int)(Math.random() * range) + min);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
