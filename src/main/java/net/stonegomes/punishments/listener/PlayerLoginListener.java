package net.stonegomes.punishments.listener;

import net.stonegomes.punishments.helper.TimeHelper;
import net.stonegomes.punishments.punishment.cache.PunishmentUserCache;
import net.stonegomes.punishments.punishment.PunishmentUser;
import net.stonegomes.punishments.punishment.Punishment;
import net.stonegomes.punishments.punishment.PunishmentType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import static org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER;

public class PlayerLoginListener implements Listener {

    private final PunishmentUserCache punishmentUserCache = PunishmentUserCache.getInstance();

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        PunishmentUser punishmentUser = punishmentUserCache.get(event.getUniqueId());
        if (punishmentUser == null) return;

        Punishment punishment = punishmentUser.findActivePunishment(PunishmentType.BAN, PunishmentType.TEMPORARY_BAN);
        if (punishment == null) return;

        String[] kickMessage;
        switch (punishment.getType()) {
            case TEMPORARY_BAN: {
                if (punishment.getTimeLeft() <= System.currentTimeMillis()) {
                    punishment.setActive(false);
                    return;
                }

                kickMessage = new String[]{
                    "",
                    "§c§lPUNISHMENTS",
                    "",
                    "§c         You are temporary banned on the server.",
                    "",
                    "§cTime left: " + TimeHelper.formatTime(punishment.getPunishmentDuration(), false),
                    "§cReason: " + punishment.getReason(),
                    "§cAuthor: " + punishment.getAuthor(),
                    "§cID: " + punishment.getId(),
                    ""
                };
                break;
            }
            case BAN: {
                kickMessage = new String[]{
                    "",
                    "§c§lPUNISHMENTS",
                    "",
                    "§c         You are permanently banned from the server.",
                    "",
                    "§cReason: " + punishment.getReason(),
                    "§cAuthor: " + punishment.getAuthor(),
                    "§cID: " + punishment.getId(),
                    ""
                };
                break;
            }
            default:
                kickMessage = new String[]{""};
        }

        event.disallow(KICK_OTHER, String.join("\n", kickMessage));
    }

}
