package ua.mykolamurza.pottullo.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;

import static ua.mykolamurza.pottullo.configuration.LocalizationConfig.getBundledText;

/**
 * @author Mykola Murza
 */
public class RemovePrivatizationZoneCommand extends PottulloSubCommand {
    private final Pottullo plugin;

    public RemovePrivatizationZoneCommand(Pottullo plugin) {
        super("/pz remove", getBundledText("command.description.remove"));
        this.plugin = plugin;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console - the simulation of a life.");
            return;
        }

        player.sendMessage(
                plugin.getPrivateZoneConfig().removePrivatizationZone(player)
                        ? getBundledText("command.remove.removed")
                        : getBundledText("command.common.dont-own-zone")
        );
    }
}
