package generator;

import java.util.Random;

public class TemperatureUtils {

    public static double t1(double temperatura, int temp2) {
        Random random = new Random();
        int broj = random.nextInt(temp2 * 10);
        return round(temperatura + (double) broj / 10, 1);
    }

    public static double t2(double donji) {
        Random random = new Random();
        int broj = random.nextInt(4);
        return round(donji + (double) broj / 10, 1);
    }

    public static double round(double value, int decimals) {
        double factor = Math.pow(10, decimals);
        return Math.round(value * factor) / factor;
    }

    public static String pisiNulu(int broj) {
        return broj < 10 ? "0" : "";
    }
}
