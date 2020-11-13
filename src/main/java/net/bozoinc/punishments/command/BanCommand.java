package net.bozoinc.punishments.command;

import com.google.common.collect.Lists;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import net.bozoinc.punishments.cache.impl.PunishedUserCache;
import net.bozoinc.punishments.entity.PunishedUser;
import net.bozoinc.punishments.entity.Punishment;
import net.bozoinc.punishments.entity.type.PunishmentType;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand {

    private final PunishedUserCache punishedUserCache = PunishedUserCache.getInstance();

    @Command(name = "ban", permission = "punishments.commands.ban", usage = "ban <target> <reason>")
    public void execute(Context<CommandSender> context, Player target, @Optional String[] reason) {
        CommandSender commandSender = context.getSender();

        Punishment punishment = Punishment.builder()
            .id(RandomStringUtils.randomNumeric(5).toUpperCase())
            .active(true)
            .author(commandSender.getName())
            .punishmentDuration(-1)
            .timeLeft(-1)
            .time(System.currentTimeMillis())
            .reason(reason == null ? "Não especificado" : String.join(" ", reason))
            .type(PunishmentType.BAN)
            .build();

        PunishedUser punishedUser = punishedUserCache.get(target.getUniqueId());
        if (punishedUser == null) {
            PunishedUser newUser = PunishedUser.builder()
                .uuid(target.getUniqueId())
                .name(target.getName())
                .punishments(Lists.newArrayList(punishment))
                .build();

            punishedUserCache.put(target.getUniqueId(), newUser);
        } else {
            Punishment findPunishment = punishedUser.findActivePunishment(PunishmentType.BAN, PunishmentType.TEMPORARY_BAN);

            if (findPunishment.getType() == PunishmentType.BAN) {
                commandSender.sendMessage("§cO jogador já está banido para sempre no momento.");
                return;
            } else {
                findPunishment.setActive(false);
            }

            punishedUser.addPunishment(punishment);
        }

        String[] kickMessage = new String[]{
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

        target.kickPlayer(String.join("\n", kickMessage));
    }

}
