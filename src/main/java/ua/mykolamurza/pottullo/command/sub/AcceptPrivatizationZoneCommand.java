package ua.mykolamurza.pottullo.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.Storage;

/**
 * @author Mykola Murza
 */
public class AcceptPrivatizationZoneCommand extends PottulloSubCommand {
    private final Pottullo plugin;

    public AcceptPrivatizationZoneCommand(Pottullo plugin) {
        super("/pz accept", "Accept your selected zone as a private zone.");
        this.plugin = plugin;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return;
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
    }
}
