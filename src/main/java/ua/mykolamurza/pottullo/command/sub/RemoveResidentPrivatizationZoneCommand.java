package ua.mykolamurza.pottullo.command.sub;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

/**
 * @author Mykola Murza
 */
public class RemoveResidentPrivatizationZoneCommand extends PottulloSubCommand {
    private final Pottullo plugin;

    public RemoveResidentPrivatizationZoneCommand(Pottullo plugin) {
        super("/pz rres <name>", "Remove a resident from your private zone.");
        this.plugin = plugin;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return;
        }

        if (arguments.length != 2) {
            sender.sendMessage(super.getUsage());
            return;
        }

        String residentName = arguments[1];
        if (sender.getName().equals(residentName)) {
            player.sendMessage("You can't add or remove yourself as a resident of your own private zone.");
            return;
        }

        PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZone(player);
        if (zone == null) {
            player.sendMessage("You don't own a private zone.");
            return;
        }

        OfflinePlayer resident = Bukkit.getOfflinePlayer(residentName);
        if (zone.getResidents().contains(resident.getUniqueId().toString())) {
            zone.removeResident(resident.getUniqueId());
            plugin.getPrivateZoneConfig().updateResidentsPrivatizationZone(player, zone);
            player.sendMessage(residentName + " has been removed from your private zone.");

            Player residentPlayer = Bukkit.getPlayer(residentName);
            if (residentPlayer != null) {
                residentPlayer.sendMessage(player.getName() + " removed you from zone.");
            }
        } else {
            player.sendMessage(residentName + " isn't resident of your private zone.");
        }
    }
}
