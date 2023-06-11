package ua.mykolamurza.pottullo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ua.mykolamurza.pottullo.command.AcceptPrivatizationZoneCommand;
import ua.mykolamurza.pottullo.command.InfoPrivatizationZoneCommand;
import ua.mykolamurza.pottullo.command.RemovePrivatizationZoneCommand;
import ua.mykolamurza.pottullo.config.RegionConfig;
import ua.mykolamurza.pottullo.handler.LapisLazuliHandler;

import java.util.Objects;

// Privatization of the territory using lapis lazuli ore
public final class Pottullo extends JavaPlugin {
    private RegionConfig regionConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Start POTTULLO.");
        saveDefaultConfig();
        regionConfig = new RegionConfig(this);

        // Commands
        Objects.requireNonNull(getCommand("accept")).setExecutor(new AcceptPrivatizationZoneCommand(this));
        Objects.requireNonNull(getCommand("info")).setExecutor(new InfoPrivatizationZoneCommand(this));
        Objects.requireNonNull(getCommand("remove")).setExecutor(new RemovePrivatizationZoneCommand(this));

        // Handlers
        getServer().getPluginManager().registerEvents(
                new LapisLazuliHandler(Objects.requireNonNull(getConfig().getList("blocks")), this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Stop POTTULLO.");
    }

    public RegionConfig getRegionConfig() {
        return regionConfig;
    }
}
