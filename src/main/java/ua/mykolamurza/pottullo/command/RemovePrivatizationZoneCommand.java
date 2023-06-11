package ua.mykolamurza.pottullo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ua.mykolamurza.pottullo.Pottullo;

/**
 * @author MykolaMurza
 */
public class RemovePrivatizationZoneCommand implements CommandExecutor {
    private final Pottullo plugin;

    public RemovePrivatizationZoneCommand(Pottullo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return true;
        }

        plugin.getRegionConfig().removePrivatizationZone(player);
        sender.sendMessage("Your privatisation zone was removed.");

        return true;
    }
}
