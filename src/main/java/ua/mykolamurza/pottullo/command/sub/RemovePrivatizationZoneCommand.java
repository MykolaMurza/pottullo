package ua.mykolamurza.pottullo.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;

/**
 * @author Mykola Murza
 */
public class RemovePrivatizationZoneCommand extends PottulloSubCommand {
    private final Pottullo plugin;

    public RemovePrivatizationZoneCommand(Pottullo plugin) {
        super("/pz remove", "Remove your PZ.");
        this.plugin = plugin;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return;
        }

        plugin.getPrivateZoneConfig().removePrivatizationZone(player);
    }
}
