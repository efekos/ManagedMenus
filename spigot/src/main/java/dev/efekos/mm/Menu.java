/*
 * Copyright (c) 2024 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.efekos.mm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Menu implements InventoryHolder {
    protected Inventory inventory;
    protected Player owner;
    protected MenuContext data;

    public Menu(MenuContext data) {
        this.owner = data.getOwner();
        this.data = data;
    }

    public abstract boolean cancelAllClicks();

    public abstract int getRows();

    public abstract String getTitle();

    public abstract void onClick(InventoryClickEvent event);

    public abstract void onClose(InventoryCloseEvent event);

    public abstract void onOpen(InventoryOpenEvent event);

    public abstract void fill();

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void open() {
        this.inventory = Bukkit.createInventory(this, getSlots(), getTitle());
        fill();

        data.addMenu(this);
        owner.openInventory(this.inventory);
    }

    protected ItemStack createItem(Material material, String displayName, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();

        assert itemMeta != null;
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.stream(lore).map(r -> ChatColor.translateAlternateColorCodes('&', r)).collect(Collectors.toList()));
        item.setItemMeta(itemMeta);

        return item;
    }

    protected ItemStack createSkull(Player owner, String displayName, String... lore) {
        ItemStack item = createItem(Material.PLAYER_HEAD, displayName, lore);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        assert meta != null;
        meta.setOwningPlayer(owner);
        item.setItemMeta(meta);
        return item;
    }

    protected void back() {
        owner.closeInventory();
        data.lastMenu().open();
    }

    protected void refresh() {
        getInventory().clear();
        fill();
    }

    protected void fillEmptyWith(ItemStack tem) {
        for (int i = 0; i < getSlots(); i++) {
            if (getInventory().getItem(i) == null) getInventory().setItem(i, tem);
        }
    }

    public int getSlots() {
        return getRows() * 9;
    }

}