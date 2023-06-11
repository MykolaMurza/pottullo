package ua.mykolamurza.pottullo.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.PrivatizationZone;

/**
 * @author MykolaMurza
 */
public class InfoPrivatizationZoneCommand implements CommandExecutor {
    private final Pottullo plugin;

    public InfoPrivatizationZoneCommand(Pottullo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return true;
        }

        Location location = player.getLocation();
        PrivatizationZone zone = plugin.getRegionConfig().getPrivatizationZoneAt(location);

        if (zone != null) {
            player.sendMessage("You are in the " + zone.getOwner() + "'s privatization zone!");
        } else {
            player.sendMessage("There are no privatization zones at your current location.");
        }

        return true;
    }
}
