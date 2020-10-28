package net.bozoinc.punishments.command;

import com.google.common.collect.Sets;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import net.bozoinc.punishments.cache.impl.PunishedUserCache;
import net.bozoinc.punishments.entity.PunishedUser;
import net.bozoinc.punishments.entity.Punishment;
import net.bozoinc.punishments.entity.type.PunishmentType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.bozoinc.punishments.helper.TimeHelper.formatDifference;

public class MuteCommand {

    private final PunishedUserCache punishedUserCache = PunishedUserCache.getInstance();

    @Command(name = "ban", permission = "punishments.commands.ban", usage = "mute <target> <reason>")
    public void execute(Context<CommandSender> context, Player target, @Optional String[] reason) {
        CommandSender commandSender = context.getSender();

        Punishment punishment = Punishment.builder()
            .active(true)
            .author(commandSender.getName())
            .reason(reason == null ? "Não especificado" : String.join(" ", reason))
            .type(PunishmentType.MUTE)
            .build();

        PunishedUser punishedUser = punishedUserCache.get(target.getUniqueId());
        if (punishedUser == null) {
            PunishedUser newUser = PunishedUser.builder()
                .uuid(target.getUniqueId())
                .name(target.getName())
                .punishments(Sets.newHashSet(punishment))
                .build();

            punishedUserCache.put(target.getUniqueId(), newUser);
        } else {
            Punishment findPunishment = punishedUser.findActivePunishment(PunishmentType.MUTE, PunishmentType.TEMPORARY_MUTE);

            if (findPunishment.getType() == PunishmentType.MUTE) {
                commandSender.sendMessage("§cO jogador já está mutado para sempre no momento.");
                return;
            } else {
                findPunishment.setActive(false);
            }

            punishedUser.addPunishment(punishment);
        }

        target.sendMessage(new String[]{
            "",
            "§cVocê foi silenciado permanentemente do servidor.",
            "",
            "§c Autor: §f" + punishment.getAuthor(),
            "§c Motivo: §f" + punishment.getReason(),
            ""
        });
    }

}
