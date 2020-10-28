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

import static net.bozoinc.punishments.helper.TimeHelper.formatDifference;

public class TempMuteCommand {

    private final PunishedUserCache punishedUserCache = PunishedUserCache.getInstance();

    @Command(name = "tempmute", permission = "punishments.commands.tempmute", usage = "tempmute <target> <time> <reason>")
    public void execute(Context<CommandSender> context, Player target, String time, @Optional String[] reason) {
        CommandSender commandSender = context.getSender();

        long timeLong = TimeHelper.convertToLong(time);
        Punishment punishment = Punishment.builder()
            .active(true)
            .author(commandSender.getName())
            .punishmentTime(timeLong)
            .time(System.currentTimeMillis() + timeLong)
            .reason(reason == null ? "Não especificado" : String.join(" ", reason))
            .type(PunishmentType.TEMPORARY_MUTE)
            .build();

        PunishedUser punishedUser = punishedUserCache.get(target.getUniqueId());
        if (punishedUser == null) {
            PunishedUser newUser = PunishedUser.builder()
                .uuid(target.getUniqueId())
                .punishments(Sets.newHashSet(punishment))
                .build();

            punishedUserCache.put(target.getUniqueId(), newUser);
        } else {
            if (punishedUser.findActivePunishment(PunishmentType.TEMPORARY_MUTE) != null || punishedUser.findActivePunishment(PunishmentType.MUTE) != null) {
                commandSender.sendMessage("§cO jogador já está mutado no momento.");
                return;
            }

            punishedUser.addPunishment(punishment);
        }

        target.sendMessage(new String[]{
            "",
            "§cVocê foi mutado temporariamente no servidor.",
            "",
            "§c Autor: §f" + punishment.getAuthor(),
            "§c Motivo: §f" + punishment.getReason(),
            "§c Tempo: §f" + formatDifference(punishment.getPunishmentTime(), false),
            ""
        });
    }

}
