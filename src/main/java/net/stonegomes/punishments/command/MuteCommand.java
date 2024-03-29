package net.stonegomes.punishments.command;

import com.google.common.collect.Lists;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import net.stonegomes.punishments.punishment.Punishment;
import net.stonegomes.punishments.punishment.PunishmentType;
import net.stonegomes.punishments.punishment.PunishmentUser;
import net.stonegomes.punishments.punishment.cache.PunishmentUserCache;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand {

    private final PunishmentUserCache punishmentUserCache = PunishmentUserCache.getInstance();

    @Command(name = "mute", permission = "punishments.commands.mute", usage = "mute <target> [reason]")
    public void execute(Context<CommandSender> context, Player target, @Optional String[] reason) {
        CommandSender commandSender = context.getSender();

        Punishment punishment = Punishment.builder()
            .id(RandomStringUtils.randomNumeric(5).toUpperCase())
            .active(true)
            .author(commandSender.getName())
            .punishmentDuration(null)
            .time(System.currentTimeMillis())
            .reason(reason == null ? "No reason" : String.join(" ", reason))
            .type(PunishmentType.MUTE)
            .build();

        PunishmentUser punishmentUser = punishmentUserCache.get(target.getUniqueId());
        if (punishmentUser == null) {
            PunishmentUser newUser = PunishmentUser.builder()
                .uniqueId(target.getUniqueId())
                .punishments(Lists.newArrayList(punishment))
                .build();

            punishmentUserCache.putElement(target.getUniqueId(), newUser);
        } else {
            Punishment findPunishment = punishmentUser.findActivePunishment(PunishmentType.MUTE, PunishmentType.TEMPORARY_MUTE);

            if (findPunishment.getType() == PunishmentType.MUTE) {
                commandSender.sendMessage("§cThe player is already muted at the moment.");
                return;
            } else {
                findPunishment.setActive(false);
            }

            punishmentUser.addPunishment(punishment);
        }

        target.sendMessage(new String[]{
            "",
            "§c§lMUTE",
            "§cYou are permanently muted on the server.",
            "",
            "§cAuthor: §f" + punishment.getAuthor(),
            "§cReason: §f" + punishment.getReason(),
            ""
        });
    }

}
