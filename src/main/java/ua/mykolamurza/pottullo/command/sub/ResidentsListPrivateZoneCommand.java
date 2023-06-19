package ua.mykolamurza.pottullo.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

/**
 * @author Mykola Murza
 */
public class ResidentsListPrivateZoneCommand extends PottulloSubCommand {
    private final Pottullo plugin;

    public ResidentsListPrivateZoneCommand(Pottullo plugin) {
        super("/pz residents", "Get a list of residents.");
        this.plugin = plugin;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return;
        }

        PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZone(player);
        if (zone == null) {
            player.sendMessage("You don't own a private zone.");
            return;
        }

        if (zone.getResidents().isEmpty()) {
            player.sendMessage("There are no residents in your private zone.");
            return;
        }

        String residentsList = String.join(", ", zone.getResidentsNames());
        player.sendMessage("Residents of your private zone: " + residentsList);
    }
}
