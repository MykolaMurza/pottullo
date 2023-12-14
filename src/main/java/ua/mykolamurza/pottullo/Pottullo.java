package ua.mykolamurza.pottullo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ua.mykolamurza.pottullo.command.PottulloCommand;
import ua.mykolamurza.pottullo.config.PrivateZoneConfig;
import ua.mykolamurza.pottullo.handler.LapisLazuliHandler;
import ua.mykolamurza.pottullo.handler.ZoneProtectionHandler;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static ua.mykolamurza.pottullo.config.LocalizationConfig.setSystemLanguage;
import static ua.mykolamurza.pottullo.config.PrivatizationDistanceConfig.setSystemPD;

/**
 * Privatization of the territory using lapis lazuli ore
 *
 * @author Mykola Murza
 * @version Minecraft 1.20.2
 */
public final class Pottullo extends JavaPlugin {
    private PrivateZoneConfig privateZoneConfig;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Start POTTULLO.");
        saveDefaultConfig();
        privateZoneConfig = new PrivateZoneConfig(this);
        setSystemPD(getConfig().getInt("radius", 7));
        Locale.setDefault(Locale.ENGLISH);
        setSystemLanguage(getConfig().getString("language", "en"));

        Objects.requireNonNull(getCommand("pz")).setExecutor(new PottulloCommand(this));

        getServer().getPluginManager().registerEvents(new LapisLazuliHandler(
                Objects.requireNonNull(getConfig().getList("blocks", new ArrayList<>())), this), this);
        getServer().getPluginManager().registerEvents(new ZoneProtectionHandler(this), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Stop POTTULLO.");
    }

    public PrivateZoneConfig getPrivateZoneConfig() {
        return privateZoneConfig;
    }
}
