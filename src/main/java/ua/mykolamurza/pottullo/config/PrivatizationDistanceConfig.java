package ua.mykolamurza.pottullo.config;

public class PrivatizationDistanceConfig {
    private static int privatizationDistance = 7;
    private static int microPrivatizationDistance = 2;

    public static int getPrivatizationDistance(boolean isMicro) {
        return isMicro ? getMicroPrivatizationDistance() : getPrivatizationDistance();
    }

    private static int getPrivatizationDistance() {
        return privatizationDistance;
    }

    public static void setPrivatizationDistance(int newDistance) {
        privatizationDistance = newDistance;
    }

    private static int getMicroPrivatizationDistance() {
        return microPrivatizationDistance;
    }

    public static void setMicroPrivatizationDistance(int microPrivatizationDistance) {
        PrivatizationDistanceConfig.microPrivatizationDistance = microPrivatizationDistance;
    }
}
