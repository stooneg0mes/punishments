package net.stonegomes.punishments.command;

import com.google.common.collect.ImmutableMap;
import me.saiintbrisson.minecraft.ViewFrame;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.stonegomes.punishments.module.ViewModule;
import net.stonegomes.punishments.punishment.PunishmentUser;
import net.stonegomes.punishments.punishment.cache.PunishmentUserCache;
import net.stonegomes.punishments.view.HistoryPaginatedView;
import org.bukkit.entity.Player;

public class HistoryCommand {

    private final PunishmentUserCache punishmentUserCache = PunishmentUserCache.getInstance();
    private final ViewFrame viewFrame = ViewModule.getViewFrame();

    @Command(name = "punishments.history", permission = "punishments.commands.history", usage = "punishments history <target>", target = CommandTarget.PLAYER)
    public void execute(Context<Player> context, Player target) {
        PunishmentUser targetUser = punishmentUserCache.get(target.getUniqueId());
        if (targetUser == null) {
            context.sendMessage("§cThis player doesn't have an history.");
            return;
        }

        viewFrame.open(
            HistoryPaginatedView.class,
            context.getSender(),
            ImmutableMap.of("punishments", targetUser.getPunishments())
        );
    }

}
