package net.bozoinc.punishments.listener;

import net.bozoinc.punishments.cache.impl.PunishedUserCache;
import net.bozoinc.punishments.entity.PunishedUser;
import net.bozoinc.punishments.entity.Punishment;
import net.bozoinc.punishments.entity.type.PunishmentType;
import net.bozoinc.punishments.helper.TimeHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    private final PunishedUserCache punishedUserCache = PunishedUserCache.getInstance();

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        PunishedUser punishedUser = punishedUserCache.get(player.getUniqueId());
        if (punishedUser == null) return;

        Punishment punishment = punishedUser.findActivePunishment(PunishmentType.TEMPORARY_MUTE, PunishmentType.MUTE);
        if (punishment.getTime() <= System.currentTimeMillis()) {
            punishment.setActive(false);
            return;
        }

        String[] message;

        switch (punishment.getType()) {
            case TEMPORARY_MUTE: {
                if (punishment.getTime() <= System.currentTimeMillis()) {
                    punishment.setActive(false);
                    return;
                }

                message = new String[]{
                    "",
                    "§cVocê está temporariamente silenciado do servidor.",
                    "",
                    "§cAutor: §f" + punishment.getAuthor(),
                    "§cMotivo: §f" + punishment.getReason(),
                    "§cTempo restante: §f" + TimeHelper.formatDifference(punishment.getTime(), true),
                    ""
                };
                break;
            }
            case MUTE: {
                message = new String[]{
                    "",
                    "§cVocê está permanentemente silenciado do servidor.",
                    "",
                    "§cAutor: §f" + punishment.getAuthor(),
                    "§cMotivo: §f" + punishment.getReason(),
                    ""
                };
                break;
            }
            default:
                message = new String[]{""};
        }

        event.setCancelled(true);
        player.sendMessage(message);
    }

}
