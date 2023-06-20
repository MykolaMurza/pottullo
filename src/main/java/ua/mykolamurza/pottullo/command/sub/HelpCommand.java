package ua.mykolamurza.pottullo.command.sub;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author Mykola Murza
 */
public class HelpCommand extends PottulloSubCommand {

    public HelpCommand() {
        super("/pz help", "Provides this list of commands.");
    }

    public void handleCommand(CommandSender sender, List<PottulloSubCommand> commands) {
        sender.sendMessage(Component.text("The POTTULLO private zone (/pz) commands:", NamedTextColor.AQUA));
        for (PottulloSubCommand subCommand : commands) {
            Component message = Component.text(subCommand.getUsage(), NamedTextColor.GOLD, TextDecoration.BOLD)
                    .append(Component.text(" - " + subCommand.getDescription())
                            .color(NamedTextColor.WHITE)
                            .decoration(TextDecoration.BOLD, false));
            sender.sendMessage(message);
        }
        sender.sendMessage(Component.text("To create a private zone, set the required block (lapis lazuli " +
                "ore block) and type the `/pz accept` command.", NamedTextColor.GRAY, TextDecoration.ITALIC));
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        // unused method
    }
}
