package ua.mykolamurza.pottullo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.Storage;

/**
 * @author Mykola Murza
 */
public class AcceptPrivatizationZoneCommand implements CommandExecutor {
    private final Pottullo plugin;

    public AcceptPrivatizationZoneCommand(Pottullo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return true;
        }

        if (Storage.contains(player)) {
            try {
                boolean isSuccess = plugin.getPrivateZoneConfig().savePrivatizationZone(player, Storage.get(player));
                if (isSuccess) {
                    player.sendMessage("You accepted your new private territory.");
                    Storage.delete(player);
                }
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage("Something went wrong.");
            }
        } else {
            player.sendMessage("Please, put privatization block before.");
        }
        return true;
    }
}
