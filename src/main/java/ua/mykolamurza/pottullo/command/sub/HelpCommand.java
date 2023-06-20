package ua.mykolamurza.pottullo.command.sub;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;

import java.util.List;

import static ua.mykolamurza.pottullo.config.LocalizationConfig.getBundledText;

/**
 * @author Mykola Murza
 */
public class HelpCommand extends PottulloSubCommand {

    public HelpCommand() {
        super("/pz help", getBundledText("command.description.help"));
    }

    public void handleCommand(CommandSender sender, List<PottulloSubCommand> commands) {
        sender.sendMessage(Component.text(getBundledText("command.help.title"), NamedTextColor.AQUA));
        for (PottulloSubCommand subCommand : commands) {
            Component message = Component.text(subCommand.getUsage(), NamedTextColor.GOLD, TextDecoration.BOLD)
                    .append(Component.text(": " + subCommand.getDescription())
                            .color(NamedTextColor.WHITE)
                            .decoration(TextDecoration.BOLD, false));
            sender.sendMessage(message);
        }
        sender.sendMessage(Component.text(getBundledText("command.help.guide"),
                NamedTextColor.GRAY, TextDecoration.ITALIC));
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        // unused method
    }
}
