package ua.mykolamurza.pottullo.command.sub;

import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends PottulloSubCommand {

    public HelpCommand() {
        super("/pz help", "Provides this list of commands.");
    }

    public void handleCommand(CommandSender sender, List<PottulloSubCommand> commands) {
        sender.sendMessage("Private zone (PZ) commands list:");
        for (PottulloSubCommand subCommand : commands) {
            sender.sendMessage(subCommand.getUsage() + " - " + subCommand.getDescription());
        }
        sender.sendMessage("To create a private zone, set the required block " +
                "(lapis lazuli ore block) and type the `/pz accept` command.");
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        // unused method
    }
}
