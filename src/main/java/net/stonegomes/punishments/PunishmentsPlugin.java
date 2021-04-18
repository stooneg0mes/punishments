package net.stonegomes.punishments;

import lombok.Getter;
import net.stonegomes.commons.CommonsPlugin;
import net.stonegomes.commons.module.Module;
import net.stonegomes.punishments.module.CommandModule;
import net.stonegomes.punishments.module.ListenerModule;
import net.stonegomes.punishments.module.StorageModule;
import net.stonegomes.punishments.module.ViewModule;

public class PunishmentsPlugin extends CommonsPlugin {

    @Getter
    private static final PunishmentsPlugin instance = getPlugin(PunishmentsPlugin.class);

    @Override
    public void handleEnable() {
        saveDefaultConfig();
    }

    @Override
    public Module[] getModules() {
        return new Module[]{
            new StorageModule(),
            new CommandModule(),
            new ListenerModule(),
            new ViewModule()
        };
    }

}
