package ua.mykolamurza.pottullo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.command.sub.*;

import java.util.List;
import java.util.Locale;

import static ua.mykolamurza.pottullo.config.LocalizationConfig.getBundledText;

/**
 * @author Mykola Murza
 */
public class PottulloCommand implements CommandExecutor {
    private final HelpCommand help;
    private final AcceptPrivatizationZoneCommand accept;
    private final InfoPrivatizationZoneCommand info;
    private final AddResidentPrivatizationZoneCommand addResident;
    private final RemoveResidentPrivatizationZoneCommand removeResident;
    private final RemovePrivatizationZoneCommand remove;
    private final ResidentsListPrivateZoneCommand residents;
    private final List<PottulloSubCommand> commands;

    public PottulloCommand(Pottullo plugin) {
        this.help = new HelpCommand();
        this.accept = new AcceptPrivatizationZoneCommand(plugin);
        this.info = new InfoPrivatizationZoneCommand(plugin);
        this.addResident = new AddResidentPrivatizationZoneCommand(plugin);
        this.removeResident = new RemoveResidentPrivatizationZoneCommand(plugin);
        this.remove = new RemovePrivatizationZoneCommand(plugin);
        this.residents = new ResidentsListPrivateZoneCommand(plugin);
        this.commands = List.of(help, accept, info, addResident, removeResident, remove, residents);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] arguments) {
        if (arguments.length == 0 || arguments[0].equalsIgnoreCase("help")) {
            help.handleCommand(sender, commands);
            return true;
        }

        switch (arguments[0].toLowerCase(Locale.ENGLISH)) {
            case "accept" -> accept.handleCommand(sender, arguments);
            case "info" -> info.handleCommand(sender, arguments);
            case "ares", "addresident" -> addResident.handleCommand(sender, arguments);
            case "rres", "removeresident" -> removeResident.handleCommand(sender, arguments);
            case "remove" -> remove.handleCommand(sender, arguments);
            case "residents" -> residents.handleCommand(sender, arguments);
            default -> {
                sender.sendMessage(getBundledText("command.common.unknown-command"));
                return false;
            }
        }

        return true;
    }
}
