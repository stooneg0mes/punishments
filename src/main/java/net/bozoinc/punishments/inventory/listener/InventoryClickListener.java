package net.bozoinc.punishments.inventory.listener;

import net.bozoinc.punishments.inventory.CustomInventory;
import net.bozoinc.punishments.inventory.item.ClickableItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory.getHolder() instanceof CustomInventory) {
            CustomInventory customInventory = (CustomInventory) inventory.getHolder();

            if (customInventory.isCancellable()) event.setCancelled(true);

            ClickableItem clickableItem = customInventory.getItem(event.getSlot());
            if (clickableItem == null) return;

            clickableItem.getEventConsumer().accept(event);
        }
    }

}
