package ua.mykolamurza.pottullo.command.sub;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

import static ua.mykolamurza.pottullo.config.LocalizationConfig.getBundledText;

/**
 * @author Mykola Murza
 */
public class InfoPrivatizationZoneCommand extends PottulloSubCommand {
    private final Pottullo plugin;

    public InfoPrivatizationZoneCommand(Pottullo plugin) {
        super("/pz info", getBundledText("command.description.info"));
        this.plugin = plugin;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return;
        }

        Location location = player.getLocation();
        PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZoneAt(location);
        if (zone != null) {
            player.sendMessage(String.format(
                    getBundledText("command.info.zone"), player.getName()));
            player.sendMessage(String.format(getBundledText("command.info.zone-bounds"),
                    zone.getFromX(), zone.getFromY(), zone.getFromZ(), zone.getToX(), zone.getToY(), zone.getToZ()));
        } else {
            player.sendMessage(getBundledText("command.info.no-zone"));
        }
    }
}
