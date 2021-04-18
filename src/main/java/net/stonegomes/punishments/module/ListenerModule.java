package net.stonegomes.punishments.module;

import net.stonegomes.commons.module.Module;
import net.stonegomes.punishments.PunishmentsPlugin;
import net.stonegomes.punishments.listener.AsyncPlayerChatListener;
import net.stonegomes.punishments.listener.PlayerLoginListener;

public class ListenerModule extends Module {

    private final PunishmentsPlugin punishmentsPlugin = PunishmentsPlugin.getInstance();

    @Override
    public void handleEnable() {
        punishmentsPlugin.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), punishmentsPlugin);
        punishmentsPlugin.getServer().getPluginManager().registerEvents(new PlayerLoginListener(), punishmentsPlugin);
    }

}
