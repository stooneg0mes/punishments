package net.stonegomes.punishments.punishment.module;

import net.stonegomes.commons.module.Module;
import net.stonegomes.punishments.punishment.cache.PunishmentUserCache;

public class PunishmentUserModule extends Module {

    private final PunishmentUserCache punishmentUserCache = PunishmentUserCache.getInstance();

    @Override
    public void handleEnable() {
        punishmentUserCache.load();
    }

    @Override
    public void handleDisable() {
        punishmentUserCache.save();
    }

}
