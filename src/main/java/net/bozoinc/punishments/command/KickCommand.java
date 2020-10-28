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

public class KickCommand {

    private final PunishedUserCache punishedUserCache = PunishedUserCache.getInstance();

    @Command(name = "kick", permission = "punishments.commands.kick", usage = "tempban <target> <time> <reason>")
    public void execute(Context<CommandSender> context, Player target, String time, @Optional String[] reason) {
        CommandSender commandSender = context.getSender();

        Punishment punishment = Punishment.builder()
            .active(false)
            .author(commandSender.getName())
            .punishmentTime(-1)
            .reason(reason == null ? "Não especificado" : String.join(" ", reason))
            .type(PunishmentType.KICK)
            .build();

        PunishedUser punishedUser = punishedUserCache.get(target.getUniqueId());
        if (punishedUser == null) {
            PunishedUser newUser = PunishedUser.builder()
                .uuid(target.getUniqueId())
                .punishments(Sets.newHashSet(punishment))
                .build();

            punishedUserCache.put(target.getUniqueId(), newUser);
        } else punishedUser.addPunishment(punishment);


        String[] kickMessage = new String[]{
            "",
            "§c§lPUNISHMENTS PLUGIN",
            "",
            "§cVocê foi kickado do servidor, você poderá entrar novamente se haver vagas.",
            ""
        };

        target.kickPlayer(String.join("\n", kickMessage));
    }

}
