package ua.mykolamurza.pottullo.command;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ua.mykolamurza.pottullo.PrivatizationZone;
import ua.mykolamurza.pottullo.Storage;

public class AcceptPrivatizationZoneCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("It's funny, but you are just a console. Simulation of a life.");
            return true;
        }

        if (Storage.contains(player)) {
            try {
                updateRegionsData(player, Storage.get(player));
                player.sendMessage("You accepted your new private territory.");
                Storage.delete(player);
            } catch (Exception e) {
                player.sendMessage("Something went wrong.");
            }
        } else {
            player.sendMessage("Please, put privatization block before.");
        }
        return true;
    }

    private void updateRegionsData(Player player, PrivatizationZone zone) {
        RegionManager regionManager = WorldGuard.getInstance()
                .getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));

        BlockVector3 min = BlockVector3.at(zone.getFromX(), zone.getFromY(), zone.getFromZ());
        BlockVector3 max = BlockVector3.at(zone.getToX(), zone.getToY(), zone.getToZ());
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(player.getName(), min, max);

        // Add the owner
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        region.getOwners().addPlayer(localPlayer.getUniqueId());

        region.setFlag(Flags.TNT, StateFlag.State.DENY);

        assert regionManager != null;
        regionManager.addRegion(region);
    }
}
