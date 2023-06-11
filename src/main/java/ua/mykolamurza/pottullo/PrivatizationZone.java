package ua.mykolamurza.pottullo;

public class PrivatizationZone {
    public static final int PRIVATIZATION_DISTANCE = 7;
    private final int fromX;
    private final int toX;
    private final int fromY;
    private final int toY;
    private final int fromZ;
    private final int toZ;

    public PrivatizationZone(int x, int y, int z) {
        this.fromX = (x - PRIVATIZATION_DISTANCE);
        this.toX = (x + PRIVATIZATION_DISTANCE);
        this.fromY = (y - PRIVATIZATION_DISTANCE);
        this.toY = (y + PRIVATIZATION_DISTANCE);
        this.fromZ = (z - PRIVATIZATION_DISTANCE);
        this.toZ = (z + PRIVATIZATION_DISTANCE);
    }

    public int getFromX() {
        return fromX;
    }

    public int getToX() {
        return toX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToY() {
        return toY;
    }

    public int getFromZ() {
        return fromZ;
    }

    public int getToZ() {
        return toZ;
    }

    @Override
    public String toString() {
        return "PrivatizationZone{" +
                "fromX=" + fromX +
                ", toX=" + toX +
                ", fromY=" + fromY +
                ", toY=" + toY +
                ", fromZ=" + fromZ +
                ", toZ=" + toZ +
                '}';
    }
}
