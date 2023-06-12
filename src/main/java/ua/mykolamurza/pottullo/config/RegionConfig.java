package ua.mykolamurza.pottullo.config;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.PrivatizationZone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author MykolaMurza
 */
public class RegionConfig {
    private final Pottullo plugin;
    private File zonesFile;
    private FileConfiguration config;

    public RegionConfig(Pottullo plugin) {
        this.plugin = plugin;
        createPrivatizationZonesFile();
    }

    /**
     * Save privatization zone using Player and PrivatizationZone objects.
     * Information about zones store as record in YML file, where key is player's ID.
     *
     * @param player - owner of zone
     * @param zone   - information about zone
     * @return operation success status
     */
    public boolean savePrivatizationZone(Player player, PrivatizationZone zone) {
        String path = player.getUniqueId().toString();

        if (config.contains(path)) {
            player.sendMessage("You can only have one private zone!");
            return false;
        }

        if (doesCollideWithExistingPrivatizationZones(zone)) {
            player.sendMessage("You cannot create a private zone that overlaps an existing region.");
            return false;
        }

        config.set(path + ".world", zone.getWorld());
        config.set(path + ".owner", zone.getOwner());
        config.set(path + ".from.x", zone.getFromX());
        config.set(path + ".to.x", zone.getToX());
        config.set(path + ".from.y", zone.getFromY());
        config.set(path + ".to.y", zone.getToY());
        config.set(path + ".from.z", zone.getFromZ());
        config.set(path + ".to.z", zone.getToZ());
        config.set(path + ".to.z", zone.getToZ());
        config.set(path + ".residents", new ArrayList<>());

        try {
            config.save(zonesFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save private zones data to file!");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void updateResidentsPrivatizationZone(Player player, PrivatizationZone zone) {
        String path = player.getUniqueId().toString();

        config.set(path + ".residents", zone.getResidents());

        try {
            config.save(zonesFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save private zones data to file!");
            e.printStackTrace();
        }
    }

    public PrivatizationZone getPrivatizationZone(Player player) {
        String path = player.getUniqueId().toString();

        if (!config.contains(path)) {
            return null;
        }

        return new PrivatizationZone(config.getString(path + ".world"), config.getString(path + ".owner"),
                config.getInt(path + ".from.x"), config.getInt(path + ".to.x"),
                config.getInt(path + ".from.y"), config.getInt(path + ".to.y"),
                config.getInt(path + ".from.z"), config.getInt(path + ".to.z"),
                config.getStringList(path + ".residents")
        );
    }

    public PrivatizationZone getPrivatizationZoneAt(Location location) {
        ConfigurationSection regions = config.getConfigurationSection("");
        if (regions == null) return null;

        for (String uuid : regions.getKeys(false)) {
            String world = config.getString(uuid + ".world");
            String owner = config.getString(uuid + ".owner");

            // Check if the world matches
            if (location.getWorld().getName().equals(world)) {
                int fromX = config.getInt(uuid + ".from.x");
                int toX = config.getInt(uuid + ".to.x");
                int fromY = config.getInt(uuid + ".from.y");
                int toY = config.getInt(uuid + ".to.y");
                int fromZ = config.getInt(uuid + ".from.z");
                int toZ = config.getInt(uuid + ".to.z");
                int locationX = (int) Math.floor(location.getX());
                int locationY = (int) Math.floor(location.getY());
                int locationZ = (int) Math.floor(location.getZ());

                if (locationX >= Math.min(fromX, toX) && locationX <= Math.max(fromX, toX)
                        && locationY >= Math.min(fromY, toY) && locationY <= Math.max(fromY, toY)
                        && locationZ >= Math.min(fromZ, toZ) && locationZ <= Math.max(fromZ, toZ)) {

                    return new PrivatizationZone(world, owner, fromX, toX, fromY, toY, fromZ, toZ);
                }
            }
        }

        return null;
    }

    public void removePrivatizationZone(Player player) {
        String path = player.getUniqueId().toString();
        if (config.contains(path)) {
            config.set(path, null);
            try {
                config.save(zonesFile);
            } catch (IOException e) {
                plugin.getLogger().severe("Could not save private zones data to file!");
                e.printStackTrace();
            }
        }
    }

    private void createPrivatizationZonesFile() {
        if (!plugin.getDataFolder().exists()) {
            if (!plugin.getDataFolder().mkdirs()) {
                plugin.getLogger().severe("Could not create the plugin data folder!");
                return;
            }
        }

        zonesFile = new File(plugin.getDataFolder(), "regions.yml");

        if (!zonesFile.exists()) {
            try {
                if (!zonesFile.createNewFile()) {
                    plugin.getLogger().severe("Could not create regions.yml!");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        config = new YamlConfiguration();
        try {
            config.load(zonesFile);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().severe("Could not load regions.yml!");
            e.printStackTrace();
        }
    }


    private boolean doesCollideWithExistingPrivatizationZones(PrivatizationZone newZone) {
        for (String key : config.getKeys(false)) {
            int existingFromX = config.getInt(key + ".from.x");
            int existingToX = config.getInt(key + ".to.x");
            int existingFromY = config.getInt(key + ".from.y");
            int existingToY = config.getInt(key + ".to.y");
            int existingFromZ = config.getInt(key + ".from.z");
            int existingToZ = config.getInt(key + ".to.z");

            if (newZone.getToX() >= existingFromX && newZone.getFromX() <= existingToX
                    && newZone.getToY() >= existingFromY && newZone.getFromY() <= existingToY
                    && newZone.getToZ() >= existingFromZ && newZone.getFromZ() <= existingToZ) {
                return true; // collision detected
            }
        }
        return false; // no collisions
    }
}
