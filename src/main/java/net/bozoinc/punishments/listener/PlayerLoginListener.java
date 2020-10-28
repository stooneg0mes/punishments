package net.bozoinc.punishments.listener;

import net.bozoinc.punishments.cache.impl.PunishedUserCache;
import net.bozoinc.punishments.entity.PunishedUser;
import net.bozoinc.punishments.entity.Punishment;
import net.bozoinc.punishments.entity.type.PunishmentType;
import net.bozoinc.punishments.helper.TimeHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import static org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER;


public class PlayerLoginListener implements Listener {

    private final PunishedUserCache punishedUserCache = PunishedUserCache.getInstance();

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        PunishedUser punishedUser = punishedUserCache.get(event.getUniqueId());
        if (punishedUser == null) return;

        Punishment punishment = punishedUser.findActivePunishment(PunishmentType.BAN, PunishmentType.TEMPORARY_BAN);
        if (punishment != null) {
            String[] kickMessage;

            switch (punishment.getType()) {
                case TEMPORARY_BAN: {
                    if (punishment.getTime() <= System.currentTimeMillis()) {
                        punishment.setActive(false);
                        return;
                    }

                    kickMessage = new String[]{
                        "",
                        "§c§lPUNISHMENTS PLUGIN",
                        "",
                        "§c         Você está banido temporariamente desse servidor.",
                        "",
                        "§cTempo restante: " + TimeHelper.formatDifference(punishment.getTime(), true),
                        "§cMotivo: " + punishment.getReason(),
                        "§cAutor: " + punishment.getAuthor(),
                        "",
                        "§cAchou a punição injusta? Crie uma revisão com o ID §f#" + punishment.getId() + "§c em:",
                        "§fwww.peppacraft.com.br/revisao.",
                        ""
                    };
                    break;
                }
                case BAN: {
                    kickMessage = new String[]{
                        "",
                        "§c§lPUNISHMENTS PLUGIN",
                        "",
                        "§c         Você está banido permanente desse servidor.",
                        "",
                        "§cMotivo: " + punishment.getReason(),
                        "§cAutor: " + punishment.getAuthor(),
                        "",
                        "§cAchou a punição injusta? Crie uma revisão com o ID §f#" + punishment.getId() + "§c em:",
                        "§fwww.peppacraft.com.br/revisao.",
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
}
