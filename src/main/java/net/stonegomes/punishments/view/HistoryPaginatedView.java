package net.stonegomes.punishments.view;

import me.saiintbrisson.minecraft.*;
import me.saiintbrisson.minecraft.utils.ItemBuilder;
import net.stonegomes.punishments.helper.TimeHelper;
import net.stonegomes.punishments.punishment.Punishment;
import org.bukkit.Material;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class HistoryPaginatedView extends PaginatedView<Punishment> {

    private final DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy '-' hh:mm");

    public HistoryPaginatedView() {
        super(4, "");

        setLayout(
            "XXXXXXXXX",
            "XOOOOOOOX",
            "XOOOOOOOX",
            "<XXXXXXX>"
        );

        setCancelOnClick(true);
        setCancelOnClone(true);
        setCancelOnDrag(true);
        setCancelOnDrop(true);
        setCancelOnMoveOut(true);
        setCancelOnPickup(true);
    }

    @Override
    protected void onRender(ViewContext context) {
        setSource(context.get("punishments"));
    }

    @Override
    protected void onOpen(OpenViewContext context) {
        context.setInventoryTitle("Punishments history from '" + context.getPlayer().getName() + "'");
    }

    @Override
    protected void onPaginationItemRender(PaginatedViewContext<Punishment> context, ViewItem item, Punishment value) {
        item.withItem(new ItemBuilder(Material.PAPER)
            .name("§c#" + value.getId())
            .lore(
                "§8* §7Status: §f" + (value.isActive() ? "active" : "inactive"),
                "§8* §7Author: §f" + value.getAuthor(),
                "§8* §7Reason: §f" + value.getReason(),
                "§8* §7Type: §f" + value.getType().getName(),
                "§8* §7Date: §f" + simpleDateFormat.format(value.getTime()),
                "§8* §7Duration: §f" + (value.hasPunishmentDuration() ? TimeHelper.formatTime(value.getPunishmentDuration(), false) : "/-/"),
                "§8* §7Duration left: §f" + (value.hasPunishmentDuration() ? TimeHelper.formatTime(value.getTimeLeft(), true) : "/-/"),
                ""
            )
            .build()
        ).onClick(handler -> {
            /*
            TODO
             */
        });
    }

    @Override
    public ViewItem getNextPageItem(PaginatedViewContext<Punishment> context) {
        if ((context.getPage() + 1) < context.getPagesCount()) {
            return context.slot(26).withItem(new ItemBuilder(Material.ARROW)
                .name("§aNext page")
                .build()
            ).onClick(handler -> context.switchToNextPage());
        } else return null;
    }

    @Override
    public ViewItem getPreviousPageItem(PaginatedViewContext<Punishment> context) {
        if (context.getPage() != 0) {
            return context.slot(18).withItem(new ItemBuilder(Material.ARROW)
                .name("§aPrevious page")
                .build()
            ).onClick(handler -> context.switchToPreviousPage());
        } else return null;
    }

}
