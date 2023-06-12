package ua.mykolamurza.pottullo.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

public class AddResidentPrivatizationZoneCommand implements CommandExecutor {
    private final Pottullo plugin;

    public AddResidentPrivatizationZoneCommand(Pottullo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return true;
        }

        if (arguments.length != 1) {
            return false;
        }

        String residentName = arguments[0];
        if (sender.getName().equals(residentName)) {
            player.sendMessage("You can't add or remove yourself as a resident of your own private zone.");
            return true;
        }

        PrivatizationZone zone = plugin.getRegionConfig().getPrivatizationZone(player);
        if (zone == null) {
            player.sendMessage("You do not own a private zone.");
            return true;
        }

        OfflinePlayer resident = Bukkit.getOfflinePlayer(residentName);
        if (zone.getResidents().contains(resident.getUniqueId().toString())) {
            player.sendMessage(residentName + " is already a resident of your private zone.");
        } else {
            zone.addResident(resident.getUniqueId());
            plugin.getRegionConfig().updateResidentsPrivatizationZone(player, zone);
            player.sendMessage(residentName + " has been added as a resident to your private zone.");
        }

        return true;
    }
}
