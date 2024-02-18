package ua.mykolamurza.pottullo.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

import static ua.mykolamurza.pottullo.configuration.LocalizationConfig.getBundledText;

/**
 * @author Mykola Murza
 */
public class ResidentsListPrivateZoneCommand extends PottulloSubCommand {
    private final Pottullo plugin;

    public ResidentsListPrivateZoneCommand(Pottullo plugin) {
        super("/pz residents", getBundledText("command.description.residents"));
        this.plugin = plugin;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console - the simulation of a life.");
            return;
        }

        PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZone(player);
        if (zone == null) {
            player.sendMessage(getBundledText("command.common.dont-own-zone"));
            return;
        }

        if (zone.getResidents().isEmpty()) {
            player.sendMessage(getBundledText("command.residents.no-res"));
            return;
        }

        String residentsList = String.join(", ", zone.getResidentsNames());
        player.sendMessage(getBundledText("command.residents.list-title") + residentsList);
    }
}
