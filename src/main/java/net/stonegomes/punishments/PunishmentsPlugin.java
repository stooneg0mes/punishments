package net.stonegomes.punishments;

import lombok.Getter;
import net.stonegomes.commons.CommonsPlugin;
import net.stonegomes.commons.module.Module;

@Getter
public class PunishmentsPlugin extends CommonsPlugin {

    @Getter
    private static PunishmentsPlugin instance;

    @Override
    public void handleEnable() {
        saveDefaultConfig();
        instance = this;
    }

    @Override
    public Module[] getModules() {
        return new Module[] {

        };
    }

}
