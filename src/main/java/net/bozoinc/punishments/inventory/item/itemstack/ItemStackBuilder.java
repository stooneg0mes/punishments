package net.bozoinc.punishments.inventory.item.itemstack;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class ItemStackBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemStackBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemStackBuilder name(String name) {
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemStackBuilder lore(String... lore) {
        itemMeta.setLore(Lists.newArrayList(lore));
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemStackBuilder lore(List<String> lore) {
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemStackBuilder addLore(String... lore) {
        List<String> actualLore = itemMeta.getLore();
        actualLore.addAll(Arrays.asList(lore));
        itemMeta.setLore(actualLore);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemStackBuilder durability(int durability) {
        itemStack.setDurability((short) durability);

        return this;
    }

    public ItemStackBuilder owner(String owner) {
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        skullMeta.setOwner(owner);

        itemStack.setItemMeta(skullMeta);

        return this;
    }

    public ItemStackBuilder enchantment(Enchantment enchantment, int value) {
        itemStack.addUnsafeEnchantment(enchantment, value);

        return this;
    }

    public ItemStackBuilder amount(int amount) {
        itemStack.setAmount(amount);

        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

}