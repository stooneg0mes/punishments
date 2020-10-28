package net.bozoinc.punishments.inventory;

import lombok.Getter;
import net.bozoinc.punishments.inventory.item.ClickableItem;
import net.bozoinc.punishments.inventory.item.itemstack.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

@Getter
public class PaginatorInventory {

    private int currentPage;
    private final List<CustomInventory> pages;

    public PaginatorInventory(List<ClickableItem> items, String title) {
        currentPage = 0;
        pages = new LinkedList<>();

        CustomInventory inventory = new DefaultPaginatorInventory(title, this);

        Integer[] ALLOWED_SLOTS = new Integer[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
        int i = 0;

        /*
        In case the provided items is empty
         */

        if (items.isEmpty()) {
            inventory.setItem(22, ClickableItem.builder()
                .itemStack(new ItemStackBuilder(Material.WEB).name("§cInventário vázio").build())
                .build());

            pages.add(inventory);
            return;
        }

        /*
        Filling the inventory & adding pages
         */

        for (ClickableItem clickableItem : items) {
            if (ALLOWED_SLOTS[i] == 34) {
                inventory.setItem(ALLOWED_SLOTS[i], clickableItem);

                i = 0;
                inventory = new DefaultPaginatorInventory(title, this);
                continue;
            }

            inventory.setItem(ALLOWED_SLOTS[i], clickableItem);
            if (i == 0) pages.add(inventory);

            i++;
        }
    }

    public void open(Player player) {
        if (pages.size() == 0) return;

        CustomInventory customInventory = pages.get(currentPage);
        customInventory.openInventory(player);
    }

    public void goToNextPage(Player player) {
        if ((currentPage + 1) >= pages.size()) return;
        currentPage++;

        CustomInventory customInventory = pages.get(currentPage);
        customInventory.openInventory(player);
    }

    public void goToPreviousPage(Player player) {
        if (currentPage == 0) return;
        currentPage--;

        CustomInventory customInventory = pages.get(currentPage);
        customInventory.openInventory(player);
    }

    public CustomInventory getPage(int index) {
        return pages.get(index);
    }

}
