package ua.mykolamurza.pottullo.command.sub;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

import static ua.mykolamurza.pottullo.config.LocalizationConfig.getBundledText;

/**
 * @author Mykola Murza
 */
public class AddResidentPrivatizationZoneCommand extends PottulloSubCommand {
    private final Pottullo plugin;

    public AddResidentPrivatizationZoneCommand(Pottullo plugin) {
        super("/pz ares <name>", getBundledText("command.description.ares"));
        this.plugin = plugin;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return;
        }

        if (arguments.length != 2) {
            player.sendMessage(super.getUsage());
            return;
        }

        String residentName = arguments[1];
        if (sender.getName().equals(residentName)) {
            player.sendMessage(getBundledText("command.ares.cant-add-yourself"));
            return;
        }

        PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZone(player);
        if (zone == null) {
            player.sendMessage(getBundledText("command.common.dont-own-zone"));
            return;
        }

        OfflinePlayer resident = Bukkit.getOfflinePlayer(residentName);
        if (zone.getResidents().contains(resident.getUniqueId().toString())) {
            player.sendMessage(String.format(
                    getBundledText("command.ares.already-res"), player.getName()));
        } else {
            zone.addResident(resident.getUniqueId());
            plugin.getPrivateZoneConfig().updateResidentsPrivatizationZone(player, zone);
            player.sendMessage(String.format(
                    getBundledText("command.ares.you-added-res"), player.getName()));

            Player residentPlayer = Bukkit.getPlayer(residentName);
            if (residentPlayer != null) {
                residentPlayer.sendMessage(String.format(
                        getBundledText("command.ares.you-are-res"), player.getName()));
            }
        }
    }
}
