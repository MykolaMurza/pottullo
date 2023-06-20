package ua.mykolamurza.pottullo.config;

public class PrivatizationDistanceConfig {
    private static int privatizationDistance = 7;

    public static int getSystemPD() {
        return privatizationDistance;
    }

    public static void setSystemPD(int newDistance) {
        privatizationDistance = newDistance;
    }
}
