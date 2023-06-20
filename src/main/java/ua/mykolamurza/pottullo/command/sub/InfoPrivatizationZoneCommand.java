package ua.mykolamurza.pottullo.command.sub;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

/**
 * @author Mykola Murza
 */
public class InfoPrivatizationZoneCommand extends PottulloSubCommand {
    private final Pottullo plugin;

    public InfoPrivatizationZoneCommand(Pottullo plugin) {
        super("/pz info", "Get info on the private zone in your location.");
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
            player.sendMessage("You are in the " + zone.getOwner() + "'s private zone!");
            player.sendMessage(String.format("Zone is from %d/%d/%d to %d/%d/%d",
                    zone.getFromX(), zone.getFromY(), zone.getFromZ(), zone.getToX(), zone.getToY(), zone.getToZ()));
        } else {
            player.sendMessage("There are no private zones at your current location.");
        }
    }
}
