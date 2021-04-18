package net.stonegomes.punishments.command;

import com.google.common.collect.Lists;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import net.stonegomes.punishments.punishment.cache.PunishmentUserCache;
import net.stonegomes.punishments.punishment.PunishmentUser;
import net.stonegomes.punishments.punishment.Punishment;
import net.stonegomes.punishments.punishment.PunishmentType;
import net.stonegomes.punishments.helper.TimeHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TempMuteCommand {

    private final PunishmentUserCache punishmentUserCache = PunishmentUserCache.getInstance();

    @Command(name = "tempmute", permission = "punishments.commands.tempmute", usage = "tempmute <target> <time> <reason>")
    public void execute(Context<CommandSender> context, Player target, String time, @Optional String[] reason) {
        CommandSender commandSender = context.getSender();

        Punishment punishment = Punishment.builder()
            .id(RandomStringUtils.randomNumeric(5).toUpperCase())
            .active(true)
            .author(commandSender.getName())
            .punishmentDuration(TimeHelper.convertToMillis(time))
            .time(System.currentTimeMillis())
            .reason(reason == null ? "No reason" : String.join(" ", reason))
            .type(PunishmentType.TEMPORARY_MUTE)
            .build();

        PunishmentUser punishmentUser = punishmentUserCache.get(target.getUniqueId());
        if (punishmentUser == null) {
            PunishmentUser newUser = PunishmentUser.builder()
                .uniqueId(target.getUniqueId())
                .punishments(Lists.newArrayList(punishment))
                .build();

            punishmentUserCache.putElement(target.getUniqueId(), newUser);
        } else {
            if (punishmentUser.findActivePunishment(PunishmentType.TEMPORARY_MUTE) != null || punishmentUser.findActivePunishment(PunishmentType.MUTE) != null) {
                commandSender.sendMessage("§cO jogador já está mutado no momento.");
                return;
            }

            punishmentUser.addPunishment(punishment);
        }

        target.sendMessage(new String[]{
            "",
            "§c§lMUTE",
            "§cYou are temporary muted on the server.",
            "",
            "§cAuthor: §f" + punishment.getAuthor(),
            "§cReason: §f" + punishment.getReason(),
            "§cTime left: §f" + TimeHelper.formatTime(punishment.getPunishmentDuration(), false),
            ""
        });
    }

}
