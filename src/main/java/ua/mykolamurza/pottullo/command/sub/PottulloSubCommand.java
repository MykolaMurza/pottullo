package ua.mykolamurza.pottullo.command.sub;

import org.bukkit.command.CommandSender;

/**
 * @author Mykola Murza
 */
public abstract class PottulloSubCommand {
    private final String usage;
    private final String description;

    public PottulloSubCommand(String usage, String description) {
        this.usage = usage;
        this.description = description;
    }

    public abstract void handleCommand(CommandSender sender, String[] arguments);

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }
}
