package net.stonegomes.punishments.listener;

import net.stonegomes.punishments.punishment.cache.PunishmentUserCache;
import net.stonegomes.punishments.punishment.PunishmentUser;
import net.stonegomes.punishments.punishment.Punishment;
import net.stonegomes.punishments.punishment.PunishmentType;
import net.stonegomes.punishments.helper.TimeHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    private final PunishmentUserCache punishmentUserCache = PunishmentUserCache.getInstance();

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        PunishmentUser punishmentUser = punishmentUserCache.get(player.getUniqueId());
        if (punishmentUser == null) return;

        Punishment punishment = punishmentUser.findActivePunishment(PunishmentType.TEMPORARY_MUTE, PunishmentType.MUTE);
        if (punishment == null) return;

        String[] message;
        switch (punishment.getType()) {
            case TEMPORARY_MUTE: {
                if ((punishment.getTimeLeft() <= System.currentTimeMillis())) {
                    punishment.setActive(false);
                    return;
                }

                message = new String[]{
                    "",
                    "§c§lMUTE",
                    "§cYou are temporary muted on the server.",
                    "",
                    "§cAuthor: §f" + punishment.getAuthor(),
                    "§cReason: §f" + punishment.getReason(),
                    "§cTime left: §f" + TimeHelper.formatTime(punishment.getTimeLeft(), true),
                    ""
                };
                break;
            }
            case MUTE: {
                message = new String[]{
                    "",
                    "§c§lMUTE",
                    "§cYou are permanently muted on the server.",
                    "",
                    "§cAuthor: §f" + punishment.getAuthor(),
                    "§cReason: §f" + punishment.getReason(),
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
