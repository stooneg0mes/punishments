package net.bozoinc.punishments.inventory;

import com.google.common.collect.Maps;
import lombok.Getter;
import net.bozoinc.punishments.inventory.item.ClickableItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Map;

@Getter
public abstract class CustomInventory implements InventoryHolder {

    private final Inventory inventory;
    private final boolean cancellable;
    private final Map<Integer, ClickableItem> items;

    public CustomInventory(String title, int rows, boolean cancellable) {
        inventory = Bukkit.createInventory(this, rows * 9, title);
        items = Maps.newHashMap();

        this.cancellable = cancellable;
    }

    /*
    Items
     */

    public void setItem(int slot, ClickableItem clickableItem) {
        inventory.setItem(slot, clickableItem.getItemStack());
        items.put(slot, clickableItem);
    }

    public ClickableItem getItem(int slot) {
        return items.get(slot);
    }

    /*
    Opening inventory
     */

    public void openInventory(Player player) {
        init(player);

        player.openInventory(inventory);
    }

    public abstract void init(Player player);

}