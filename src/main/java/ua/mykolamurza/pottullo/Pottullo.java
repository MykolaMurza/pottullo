package ua.mykolamurza.pottullo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ua.mykolamurza.pottullo.command.AcceptPrivatizationZoneCommand;
import ua.mykolamurza.pottullo.handler.LapisLazuliHandler;

import java.util.Objects;

// Privatization of the territory using lapis lazuli ore
public final class Pottullo extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Start POTTULLO.");
        saveDefaultConfig();

        // Commands
        Objects.requireNonNull(getCommand("accept")).setExecutor(new AcceptPrivatizationZoneCommand());

        // Handlers
        getServer().getPluginManager().registerEvents(
                new LapisLazuliHandler(Objects.requireNonNull(getConfig().getList("blocks"))), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Stop POTTULLO.");
    }
}
