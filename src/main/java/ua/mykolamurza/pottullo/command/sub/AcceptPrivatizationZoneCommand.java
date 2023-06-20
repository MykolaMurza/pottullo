package ua.mykolamurza.pottullo.command.sub;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.Storage;

import static ua.mykolamurza.pottullo.config.LocalizationConfig.getBundledText;

/**
 * @author Mykola Murza
 */
public class AcceptPrivatizationZoneCommand extends PottulloSubCommand {
    private final Pottullo plugin;

    public AcceptPrivatizationZoneCommand(Pottullo plugin) {
        super("/pz accept", getBundledText("command.description.accept"));
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
                    player.sendMessage(getBundledText("command.accept.accepted"));
                    Storage.delete(player);
                }
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(Component.text(
                        getBundledText("command.common.unknown-error"), NamedTextColor.RED, TextDecoration.BOLD));
            }
        } else {
            player.sendMessage(getBundledText("command.accept.put-block-before"));
        }
    }
}
