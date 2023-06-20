package ua.mykolamurza.pottullo.config;

public class Vars {
    // YML keys
    public static final String WORLD_KEY = ".world";
    public static final String OWNER_KEY = ".owner";
    public static final String RESIDENTS_KEY = ".residents";
    public static final String FROM_X_KEY = ".from.x";
    public static final String TO_X_KEY = ".to.x";
    public static final String FROM_Y_KEY = ".from.y";
    public static final String TO_Y_KEY = ".to.y";
    public static final String FROM_Z_KEY = ".from.z";
    public static final String TO_Z_KEY = ".to.z";

    private static int privatizationDistance = 7;

    public static int getSystemPD() {
        return privatizationDistance;
    }

    public static void setSystemPD(int newDistance) {
        privatizationDistance = newDistance;
    }
}
