package net.bozoinc.punishments.inventory;

import net.bozoinc.punishments.inventory.item.ClickableItem;
import net.bozoinc.punishments.inventory.item.itemstack.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DefaultPaginatorInventory extends CustomInventory {

    private final PaginatorInventory paginatorInventory;

    public DefaultPaginatorInventory(String title, PaginatorInventory paginatorInventory) {
        super(title, 5, true); /* Rows must be 5 */

        this.paginatorInventory = paginatorInventory;
    }

    @Override
    public void init(Player player) {
        ClickableItem nextPage = ClickableItem.builder()
            .itemStack(new ItemStackBuilder(Material.ARROW).name("§aPróxima página").build())
            .eventConsumer((inventory, event) -> paginatorInventory.goToNextPage(player))
            .build();

        ClickableItem previousPage = ClickableItem.builder()
            .itemStack(new ItemStackBuilder(Material.ARROW).name("§aPágina anterior").build())
            .eventConsumer((inventory, event) -> paginatorInventory.goToPreviousPage(player))
            .build();

        if ((paginatorInventory.getCurrentPage() + 1) < paginatorInventory.getPages().size()) setItem(26, nextPage);
        if (paginatorInventory.getCurrentPage() > 0) setItem(18, previousPage);
    }

}
