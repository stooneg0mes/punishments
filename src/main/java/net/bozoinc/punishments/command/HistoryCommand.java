package net.bozoinc.punishments.command;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.bozoinc.punishments.cache.impl.PunishedUserCache;
import net.bozoinc.punishments.entity.PunishedUser;
import net.bozoinc.punishments.entity.Punishment;
import net.bozoinc.punishments.helper.TimeHelper;
import net.bozoinc.punishments.inventory.PaginatorInventory;
import net.bozoinc.punishments.inventory.item.ClickableItem;
import net.bozoinc.punishments.inventory.item.itemstack.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static net.bozoinc.punishments.helper.TimeHelper.formatDifference;

public class HistoryCommand {

    private final PunishedUserCache punishedUserCache = PunishedUserCache.getInstance();
    private final DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy '-' hh:mm");

    @Command(name = "punishments.history", permission = "punishments.commands.history", usage = "punishments history", target = CommandTarget.PLAYER)
    public void execute(Context<Player> context) {
        List<ClickableItem> historyItems = new LinkedList<>();

        for (PunishedUser punishedUser : punishedUserCache.values()) {
            ClickableItem userHistoryItem = ClickableItem.builder()
                .itemStack(
                    new ItemStackBuilder(Material.SKULL_ITEM)
                        .durability(3)
                        .owner(punishedUser.getName())
                        .name("§aHistórico de §f" + punishedUser.getName() + "§a.")
                        .lore("§7Clique para ver o histórico de punições desse jogador.")
                        .build()
                )
                .eventConsumer((inventory, event) -> {
                    List<ClickableItem> userHistoryItems = new LinkedList<>();
                    for (Punishment punishment : punishedUser.getPunishments()) {
                        userHistoryItems.add(buildPunishmentItem(punishment));
                    }

                    PaginatorInventory paginatorInventory = new PaginatorInventory(userHistoryItems, "Histórico de " + punishedUser.getName());
                    paginatorInventory.open((Player) event.getWhoClicked());
                })
                .build();

            historyItems.add(userHistoryItem);
        }

        PaginatorInventory paginatorInventory = new PaginatorInventory(historyItems, "Histórico de usuários");
        paginatorInventory.open(context.getSender());
    }

    private ClickableItem buildPunishmentItem(Punishment punishment) {
        return ClickableItem.builder()
            .itemStack(
                new ItemStackBuilder(Material.PAPER)
                    .name("§aPunição §f#" + punishment.getId())
                    .lore(
                        "",
                        "§7Status: §f" + (punishment.isActive() ? "ativa" : "finalizada"),
                        "§7Duração: §f" + formatDifference(punishment.getPunishmentDuration(), false),
                        "§7Duração restante: §f" + (punishment.isActive() ? formatDifference(punishment.getTimeLeft(), true) : "finalizada"),
                        "§7Horário da punição: §f" + simpleDateFormat.format(new Date(punishment.getTime())),
                        "§7Autor: §f" + punishment.getAuthor(),
                        "§7Tipo da punição: §f" + punishment.getType().getName(),
                        "",
                        punishment.isActive() ? "§aClique para revogar essa punição." : "§cEssa punição já foi finalizada.",
                        ""
                    )
                    .build()
            )
            .eventConsumer((inventory, event) -> {
                if (!punishment.isActive()) return;

                punishment.setActive(false);
                inventory.updateItem(event.getSlot());
            })
            .build();
    }

}
