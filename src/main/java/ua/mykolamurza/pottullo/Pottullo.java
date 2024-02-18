package ua.mykolamurza.pottullo;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ua.mykolamurza.pottullo.command.PottulloCommand;
import ua.mykolamurza.pottullo.configuration.PrivateZoneConfig;
import ua.mykolamurza.pottullo.listener.LapisLazuliListener;
import ua.mykolamurza.pottullo.listener.ZoneProtectionListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static ua.mykolamurza.pottullo.configuration.LocalizationConfig.setSystemLanguage;
import static ua.mykolamurza.pottullo.configuration.PrivatizationDistanceConfig.setMicroPrivatizationDistance;
import static ua.mykolamurza.pottullo.configuration.PrivatizationDistanceConfig.setPrivatizationDistance;

/**
 * Privatization of the territory using lapis lazuli ore
 *
 * @author Mykola Murza
 * @version Minecraft 1.20
 */
public final class Pottullo extends JavaPlugin {
    public static JavaPlugin plugin = null;
    private PrivateZoneConfig privateZoneConfig;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        privateZoneConfig = new PrivateZoneConfig(this);
        setPrivatizationDistance(getConfig().getInt("radius", 7));
        setMicroPrivatizationDistance(getConfig().getInt("micro-radius", 2));
        Locale.setDefault(Locale.ENGLISH);
        setSystemLanguage(getConfig().getString("language", "en"));

        Objects.requireNonNull(getCommand("pz")).setExecutor(new PottulloCommand(this));

        getServer().getPluginManager().registerEvents(buildLapisLazuliHandler(), this);
        getServer().getPluginManager().registerEvents(new ZoneProtectionListener(this), this);
    }

    public PrivateZoneConfig getPrivateZoneConfig() {
        return privateZoneConfig;
    }

    @NotNull
    private LapisLazuliListener buildLapisLazuliHandler() {
        return new LapisLazuliListener(
                Objects.requireNonNull(getConfig().getList("blocks", new ArrayList<>())),
                Objects.requireNonNull(getConfig().getList("micro-blocks", new ArrayList<>())), this);
    }
}
