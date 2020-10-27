package net.bozoinc.punishments.command;

import com.google.common.collect.Sets;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import net.bozoinc.punishments.cache.impl.PunishedUserCache;
import net.bozoinc.punishments.entity.PunishedUser;
import net.bozoinc.punishments.entity.Punishment;
import net.bozoinc.punishments.entity.type.PunishmentType;
import net.bozoinc.punishments.helper.TimeHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TempBanCommand {

    private final PunishedUserCache punishedUserCache = PunishedUserCache.getInstance();

    @Command(name = "tempban", permission = "peppa.ban", usage = "tempban <target> <time> <reason>")
    public void execute(Context<CommandSender> context, Player target, String time, @Optional String[] reason) {
        CommandSender commandSender = context.getSender();

        long timeLong = TimeHelper.convertToLong(time);
        Punishment punishment = Punishment.builder()
            .active(true)
            .author(commandSender.getName())
            .punishmentTime(timeLong)
            .time(System.currentTimeMillis() + timeLong)
            .reason(reason == null ? "Não especificado" : String.join(" ", reason))
            .type(PunishmentType.TEMPORARY_BAN)
            .build();

        PunishedUser punishedUser = punishedUserCache.get(target.getUniqueId());
        if (punishedUser == null) {
            PunishedUser newUser = PunishedUser.builder()
                .uuid(target.getUniqueId())
                .punishments(Sets.newHashSet(punishment))
                .build();

            punishedUserCache.put(target.getUniqueId(), newUser);
        } else {
            if (punishedUser.findActivePunishment(PunishmentType.TEMPORARY_BAN) != null || punishedUser.findActivePunishment(PunishmentType.BAN) != null) {
                commandSender.sendMessage("§cO jogador já está banido no momento.");
                return;
            }

            punishedUser.addPunishment(punishment);
        }

        String[] kickMessage = new String[]{
            "",
            "§c§lPUNISHMENTS PLUGIN",
            "",
            "§c         Você está banido temporariamente desse servidor.",
            "",
            "§cTempo: " + TimeHelper.formatDifference(punishment.getPunishmentTime(), false),
            "§cMotivo: " + punishment.getReason(),
            "§cAutor: " + punishment.getAuthor(),
            "",
            "§cAchou a punição injusta? Crie uma revisão com o ID §f#" + punishment.getId() + "§c em:",
            "§fwww.peppacraft.com.br/revisao.",
            ""
        };

        target.kickPlayer(String.join("\n", kickMessage));
    }

}
