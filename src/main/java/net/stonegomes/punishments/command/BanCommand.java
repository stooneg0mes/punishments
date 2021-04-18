package net.stonegomes.punishments.command;

import com.google.common.collect.Lists;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import net.stonegomes.punishments.punishment.cache.PunishmentUserCache;
import net.stonegomes.punishments.punishment.PunishmentUser;
import net.stonegomes.punishments.punishment.Punishment;
import net.stonegomes.punishments.punishment.PunishmentType;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand {

    private final PunishmentUserCache punishmentUserCache = PunishmentUserCache.getInstance();

    @Command(name = "ban", permission = "punishments.commands.ban", usage = "ban <target> <reason>")
    public void execute(Context<CommandSender> context, Player target, @Optional String[] reason) {
        CommandSender commandSender = context.getSender();

        Punishment punishment = Punishment.builder()
            .id(RandomStringUtils.randomNumeric(5).toUpperCase())
            .active(true)
            .author(commandSender.getName())
            .punishmentDuration(null)
            .time(System.currentTimeMillis())
            .reason(reason == null ? "No reason" : String.join(" ", reason))
            .type(PunishmentType.BAN)
            .build();

        PunishmentUser punishmentUser = punishmentUserCache.get(target.getUniqueId());
        if (punishmentUser == null) {
            PunishmentUser newUser = PunishmentUser.builder()
                .uniqueId(target.getUniqueId())
                .punishments(Lists.newArrayList(punishment))
                .build();

            punishmentUserCache.putElement(target.getUniqueId(), newUser);
        } else {
            Punishment findPunishment = punishmentUser.findActivePunishment(PunishmentType.BAN, PunishmentType.TEMPORARY_BAN);

            if (findPunishment.getType() == PunishmentType.BAN) {
                commandSender.sendMessage("§cThe player is already banned at the moment.");
                return;
            } else {
                findPunishment.setActive(false);
            }

            punishmentUser.addPunishment(punishment);
        }

        String[] kickMessage = new String[]{
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

        target.kickPlayer(String.join("\n", kickMessage));
    }

}
