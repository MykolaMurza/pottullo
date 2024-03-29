package ua.mykolamurza.pottullo.configuration;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.mykolamurza.pottullo.configuration.Vars.*;

/**
 * @author Mykola Murza
 */
public class PrivateZoneConfig {
    private final Pottullo plugin;
    private File zonesFile;
    private FileConfiguration config;

    public PrivateZoneConfig(Pottullo plugin) {
        this.plugin = plugin;
        createPrivatizationZonesFile();
    }

    /**
     * Save private zone using Player and PrivatizationZone objects.
     * Information about zones store as record in YML file, where key is player's ID.
     *
     * @param player - owner of zone
     * @param zone   - information about zone
     * @return operation success status
     */
    public synchronized boolean savePrivatizationZone(Player player, PrivatizationZone zone) {
        String path = player.getUniqueId().toString();

        if (config.contains(path)) {
            player.sendMessage("You can only have one private zone!");
            return false;
        }

        if (doesCollideWithExistingPrivatizationZones(zone)) {
            player.sendMessage("You can't create a private zone that overlaps an existing zone.");
            return false;
        }

        config.set(path + WORLD_KEY, zone.getWorld());
        config.set(path + OWNER_KEY, zone.getOwner());
        config.set(path + FROM_X_KEY, zone.getFromX());
        config.set(path + TO_X_KEY, zone.getToX());
        config.set(path + FROM_Y_KEY, zone.getFromY());
        config.set(path + TO_Y_KEY, zone.getToY());
        config.set(path + FROM_Z_KEY, zone.getFromZ());
        config.set(path + TO_Z_KEY, zone.getToZ());
        config.set(path + IS_MICRO_KEY, zone.isMicro());
        config.set(path + RESIDENTS_KEY, new ArrayList<>());

        return saveConfig();
    }

    public synchronized void updateResidentsPrivatizationZone(Player player, PrivatizationZone zone) {
        String path = player.getUniqueId().toString();

        config.set(path + RESIDENTS_KEY, zone.getResidents());

        saveConfig();
    }

    public synchronized boolean removePrivatizationZone(Player player) {
        String path = player.getUniqueId().toString();
        if (config.contains(path)) {
            config.set(path, null);
            saveConfig();
            return true;
        }

        return false;
    }

    public PrivatizationZone getPrivatizationZone(Player player) {
        String path = player.getUniqueId().toString();

        if (!config.contains(path)) {
            return null;
        }

        return new PrivatizationZone(config.getString(path + WORLD_KEY), config.getString(path + OWNER_KEY),
                config.getInt(path + FROM_X_KEY), config.getInt(path + TO_X_KEY),
                config.getInt(path + FROM_Y_KEY), config.getInt(path + TO_Y_KEY),
                config.getInt(path + FROM_Z_KEY), config.getInt(path + TO_Z_KEY),
                config.getBoolean(path + IS_MICRO_KEY), config.getStringList(path + RESIDENTS_KEY)
        );
    }

    public PrivatizationZone getPrivatizationZoneAt(Location location) {
        ConfigurationSection zones = config.getConfigurationSection("");
        if (zones == null) return null;

        for (String zonePlayerKey : zones.getKeys(false)) {
            String world = config.getString(zonePlayerKey + WORLD_KEY);

            if (location.getWorld().getName().equals(world)) {
                final int locationX = (int) Math.floor(location.getX());
                final int fromX = config.getInt(zonePlayerKey + FROM_X_KEY);
                final int toX = config.getInt(zonePlayerKey + TO_X_KEY);
                if (!isCurrentZonePresentInRange(locationX, fromX, toX)) continue;

                final int locationY = (int) Math.floor(location.getY());
                final int fromY = config.getInt(zonePlayerKey + FROM_Y_KEY);
                final int toY = config.getInt(zonePlayerKey + TO_Y_KEY);
                if (!isCurrentZonePresentInRange(locationY, fromY, toY)) continue;

                final int locationZ = (int) Math.floor(location.getZ());
                final int fromZ = config.getInt(zonePlayerKey + FROM_Z_KEY);
                final int toZ = config.getInt(zonePlayerKey + TO_Z_KEY);
                if (!isCurrentZonePresentInRange(locationZ, fromZ, toZ)) continue;

                String owner = config.getString(zonePlayerKey + OWNER_KEY);
                boolean isMicro = config.getBoolean(zonePlayerKey + IS_MICRO_KEY);
                List<String> residents = config.getStringList(zonePlayerKey + RESIDENTS_KEY);

                return new PrivatizationZone(world, owner, fromX, toX, fromY, toY, fromZ, toZ, isMicro, residents);
            }
        }

        return null;
    }

    private void createPrivatizationZonesFile() {
        if (!plugin.getDataFolder().exists()) {
            if (!plugin.getDataFolder().mkdirs()) {
                plugin.getLogger().severe("Could not create the plugin data folder!");
                return;
            }
        }

        zonesFile = new File(plugin.getDataFolder(), "zones.yml");

        if (!zonesFile.exists()) {
            try {
                if (!zonesFile.createNewFile()) {
                    plugin.getLogger().severe("Could not create zones.yml!");
                    return;
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create zones.yml!");
                return;
            }
        }

        config = new YamlConfiguration();
        try {
            config.load(zonesFile);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().severe("Could not load zones.yml!");
        }
    }

    private boolean doesCollideWithExistingPrivatizationZones(PrivatizationZone newZone) {
        for (String key : config.getKeys(false)) {
            int existingFromX = config.getInt(key + FROM_X_KEY);
            int existingToX = config.getInt(key + TO_X_KEY);
            int existingFromY = config.getInt(key + FROM_Y_KEY);
            int existingToY = config.getInt(key + TO_Y_KEY);
            int existingFromZ = config.getInt(key + FROM_Z_KEY);
            int existingToZ = config.getInt(key + TO_Z_KEY);

            if (newZone.getToX() >= existingFromX && newZone.getFromX() <= existingToX
                    && newZone.getToY() >= existingFromY && newZone.getFromY() <= existingToY
                    && newZone.getToZ() >= existingFromZ && newZone.getFromZ() <= existingToZ) {
                return true; // collision detected
            }
        }
        return false; // no collisions
    }

    private synchronized boolean saveConfig() {
        try {
            config.save(zonesFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save private zones data to file!");
            return false;
        }

        return true;
    }

    private boolean isCurrentZonePresentInRange(int current, int min, int max) {
        return current >= Math.min(min, max) && current <= Math.max(min, max);
    }
}
