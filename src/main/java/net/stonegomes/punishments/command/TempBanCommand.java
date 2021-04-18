package net.stonegomes.punishments.command;

import com.google.common.collect.Lists;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import net.stonegomes.commons.helper.TimeHelper;
import net.stonegomes.punishments.punishment.Punishment;
import net.stonegomes.punishments.punishment.PunishmentType;
import net.stonegomes.punishments.punishment.PunishmentUser;
import net.stonegomes.punishments.punishment.cache.PunishmentUserCache;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TempBanCommand {

    private final PunishmentUserCache punishmentUserCache = PunishmentUserCache.getInstance();

    @Command(name = "tempban", permission = "punishments.commands.tempban", usage = "tempban <target> <time> <reason>")
    public void execute(Context<CommandSender> context, Player target, String time, @Optional String[] reason) {
        CommandSender commandSender = context.getSender();

        Punishment punishment = Punishment.builder()
            .id(RandomStringUtils.randomNumeric(5).toUpperCase())
            .active(true)
            .author(commandSender.getName())
            .punishmentDuration(TimeHelper.convertToMillis(time))
            .time(System.currentTimeMillis())
            .reason(reason == null ? "No reason" : String.join(" ", reason))
            .type(PunishmentType.TEMPORARY_BAN)
            .build();

        PunishmentUser punishmentUser = punishmentUserCache.get(target.getUniqueId());
        if (punishmentUser == null) {
            PunishmentUser newUser = PunishmentUser.builder()
                .uniqueId(target.getUniqueId())
                .punishments(Lists.newArrayList(punishment))
                .build();

            punishmentUserCache.putElement(target.getUniqueId(), newUser);
        } else {
            if (punishmentUser.findActivePunishment(PunishmentType.TEMPORARY_BAN) != null || punishmentUser.findActivePunishment(PunishmentType.BAN) != null) {
                commandSender.sendMessage("§cThe player is already banned at the moment.");
                return;
            }

            punishmentUser.addPunishment(punishment);
        }

        String[] kickMessage = new String[]{
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

        target.kickPlayer(String.join("\n", kickMessage));
    }

}
