package ua.mykolamurza.pottullo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

/**
 * @author Mykola Murza
 */
public class ResidentsListPrivateZoneCommand implements CommandExecutor {
    private final Pottullo plugin;

    public ResidentsListPrivateZoneCommand(Pottullo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return true;
        }

        PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZone(player);

        if (zone == null) {
            player.sendMessage("You don't own a private zone.");
            return true;
        }

        if (zone.getResidents().isEmpty()) {
            player.sendMessage("There are no residents in your private zone.");
            return true;
        }

        String residentsList = String.join(", ", zone.getResidentsNames());
        player.sendMessage("Residents of your private zone: " + residentsList);

        return true;
    }
}
