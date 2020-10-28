package net.bozoinc.punishments.inventory.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@AllArgsConstructor
@Builder
@Getter
public class ClickableItem {

    private ItemStack itemStack;
    private Consumer<InventoryClickEvent> eventConsumer;

}
