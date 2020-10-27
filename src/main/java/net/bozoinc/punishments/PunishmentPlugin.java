package net.bozoinc.punishments;

import me.saiintbrisson.bukkit.command.BukkitFrame;
import net.bozoinc.punishments.command.BanCommand;
import net.bozoinc.punishments.command.TempBanCommand;
import net.bozoinc.punishments.command.TempMuteCommand;
import net.bozoinc.punishments.listener.AsyncPlayerChatListener;
import net.bozoinc.punishments.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PunishmentPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        BukkitFrame bukkitFrame = new BukkitFrame(this);
        bukkitFrame.registerCommands(new BanCommand(), new TempMuteCommand(), new TempBanCommand());

        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }


}
