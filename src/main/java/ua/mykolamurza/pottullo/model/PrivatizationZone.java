package ua.mykolamurza.pottullo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ua.mykolamurza.pottullo.config.Vars.PRIVATIZATION_DISTANCE;

public class PrivatizationZone {
    private final String world;
    private final String owner;
    private final int fromX;
    private final int toX;
    private final int fromY;
    private final int toY;
    private final int fromZ;
    private final int toZ;
    private List<String> residents;

    public PrivatizationZone(String world, String owner, int x, int y, int z) {
        this.world = world;
        this.owner = owner;
        this.fromX = (x - PRIVATIZATION_DISTANCE);
        this.toX = (x + PRIVATIZATION_DISTANCE);
        this.fromY = (y - PRIVATIZATION_DISTANCE);
        this.toY = (y + PRIVATIZATION_DISTANCE);
        this.fromZ = (z - PRIVATIZATION_DISTANCE);
        this.toZ = (z + PRIVATIZATION_DISTANCE);
    }

    public PrivatizationZone(String world, String owner, int fromX, int toX,
                             int fromY, int toY, int fromZ, int toZ) {
        this.world = world;
        this.owner = owner;
        this.fromX = fromX;
        this.toX = toX;
        this.fromY = fromY;
        this.toY = toY;
        this.fromZ = fromZ;
        this.toZ = toZ;
    }

    public PrivatizationZone(String world, String owner, int fromX, int toX, int fromY,
                             int toY, int fromZ, int toZ, List<String> residents) {
        this.world = world;
        this.owner = owner;
        this.fromX = fromX;
        this.toX = toX;
        this.fromY = fromY;
        this.toY = toY;
        this.fromZ = fromZ;
        this.toZ = toZ;
        this.residents = residents;
    }

    public String getWorld() {
        return world;
    }

    public String getOwner() {
        return owner;
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

    public void addResident(UUID playerId) {
        if (residents == null) {
            residents = new ArrayList<>();
        }
        residents.add(playerId.toString());
    }

    public void removeResident(UUID playerId) {
        if (residents == null) {
            residents = new ArrayList<>();
            return;
        }
        residents.removeIf(uuid -> uuid.equals(playerId.toString()));
    }

    public List<String> getResidents() {
        return residents != null ? residents : new ArrayList<>();
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
