package net.bozoinc.punishments.inventory.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.bozoinc.punishments.inventory.CustomInventory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@AllArgsConstructor
@Builder
@Getter
public class ClickableItem {

    private final ItemStack itemStack;
    private final BiConsumer<CustomInventory, InventoryClickEvent> eventConsumer;

}
