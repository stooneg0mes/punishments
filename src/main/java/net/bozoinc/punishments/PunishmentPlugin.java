package net.bozoinc.punishments;

import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageType;
import net.bozoinc.punishments.cache.impl.PunishedUserCache;
import net.bozoinc.punishments.command.*;
import net.bozoinc.punishments.inventory.listener.InventoryClickListener;
import net.bozoinc.punishments.listener.AsyncPlayerChatListener;
import net.bozoinc.punishments.listener.PlayerLoginListener;
import net.bozoinc.punishments.storage.MongoDB;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class PunishmentPlugin extends JavaPlugin {

    @Getter
    private static PunishmentPlugin instance;

    private MongoDB mongoDB;

    @Override
    public void onEnable() {
        instance = this;

        if (setupStorage()) {
            /*
            Loading users
             */

            PunishedUserCache.getInstance().load();

            /*
            Registering commands
             */

            BukkitFrame bukkitFrame = new BukkitFrame(this);

            bukkitFrame.registerCommands(
                new BanCommand(),
                new TempMuteCommand(),
                new TempBanCommand(),
                new MuteCommand(),
                new KickCommand(),
                new HistoryCommand()
            );

            bukkitFrame.getMessageHolder().setMessage(MessageType.INCORRECT_TARGET, "§cEsse comando não foi feito para você.");
            bukkitFrame.getMessageHolder().setMessage(MessageType.INCORRECT_USAGE, "§cPor favor use §f/{usage}§c.");
            bukkitFrame.getMessageHolder().setMessage(MessageType.NO_PERMISSION, "§cVocê não tem permissão para executar esse comando.");

            /*
            Registering listeners
             */

            Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
            Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(), this);
            Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        } else {
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if (!mongoDB.isConnected()) return;

        PunishedUserCache.getInstance().save();
    }

    private boolean setupStorage() {
        mongoDB = new MongoDB("localhost", "test", 27017);

        return mongoDB.startConnection();
    }

}
