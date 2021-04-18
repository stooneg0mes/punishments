package net.stonegomes.punishments.module;

import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageType;
import net.stonegomes.commons.module.Module;
import net.stonegomes.punishments.PunishmentsPlugin;
import net.stonegomes.punishments.command.*;

public class CommandModule extends Module {

    private final PunishmentsPlugin punishmentsPlugin = PunishmentsPlugin.getInstance();

    @Override
    public void handleEnable() {
        BukkitFrame bukkitFrame = new BukkitFrame(punishmentsPlugin);

        bukkitFrame.registerCommands(
            new BanCommand(),
            new TempMuteCommand(),
            new TempBanCommand(),
            new MuteCommand(),
            new KickCommand(),
            new HistoryCommand()
        );

        bukkitFrame.getMessageHolder().setMessage(MessageType.INCORRECT_TARGET, "§cThis command is not made for you.");
        bukkitFrame.getMessageHolder().setMessage(MessageType.INCORRECT_USAGE, "§cCommand usage is §f/{usage}§c.");
        bukkitFrame.getMessageHolder().setMessage(MessageType.NO_PERMISSION, "§cYou don't have permission to execute this command.");
    }

}
