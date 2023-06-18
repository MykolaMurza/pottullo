package ua.mykolamurza.pottullo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ua.mykolamurza.pottullo.command.*;
import ua.mykolamurza.pottullo.config.PrivateZoneConfig;
import ua.mykolamurza.pottullo.handler.LapisLazuliHandler;
import ua.mykolamurza.pottullo.handler.ZoneProtectionHandler;

import java.util.Objects;

/**
 * Privatization of the territory using lapis lazuli ore
 *
 * @author Mykola Murza
 * @version Minecraft 1.20.1
 */
public final class Pottullo extends JavaPlugin {
    private PrivateZoneConfig privateZoneConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Start POTTULLO.");
        saveDefaultConfig();
        privateZoneConfig = new PrivateZoneConfig(this);

        // Commands
        Objects.requireNonNull(getCommand("accept"))
                .setExecutor(new AcceptPrivatizationZoneCommand(this));
        Objects.requireNonNull(getCommand("info"))
                .setExecutor(new InfoPrivatizationZoneCommand(this));
        Objects.requireNonNull(getCommand("remove"))
                .setExecutor(new RemovePrivatizationZoneCommand(this));
        Objects.requireNonNull(getCommand("addresident"))
                .setExecutor(new AddResidentPrivatizationZoneCommand(this));
        Objects.requireNonNull(getCommand("removeresident"))
                .setExecutor(new RemoveResidentPrivatizationZoneCommand(this));
        Objects.requireNonNull(getCommand("residents"))
                .setExecutor(new ResidentsListPrivateZoneCommand(this));

        // Handlers
        getServer().getPluginManager().registerEvents(
                new LapisLazuliHandler(Objects.requireNonNull(getConfig().getList("blocks")), this), this);
        getServer().getPluginManager().registerEvents(
                new ZoneProtectionHandler(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Stop POTTULLO.");
    }

    public PrivateZoneConfig getPrivateZoneConfig() {
        return privateZoneConfig;
    }
}
