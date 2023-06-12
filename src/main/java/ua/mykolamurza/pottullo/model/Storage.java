package ua.mykolamurza.pottullo.model;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Storage {
    private static final HashMap<String, PrivatizationZone> ZONES = new HashMap<>();

    public static void add(Player player, PrivatizationZone zone) {
        ZONES.put(player.getName(), zone);
    }

    public static PrivatizationZone get(Player player) {
        return ZONES.get(player.getName());
    }

    public static void delete(Player player) {
        ZONES.remove(player.getName());
    }

    public static boolean contains(Player player) {
        return ZONES.containsKey(player.getName());
    }
}
