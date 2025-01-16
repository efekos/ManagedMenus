/*
 * MIT License
 *
 * Copyright (c) 2025 efekos
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

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ClickableMenuItem extends MenuItem {

    List<Integer> slots();

    @Override
    default boolean listensTo(InventoryEvent e) {
        return e instanceof InventoryClickEvent;
    }

    @Override
    default void on(InventoryEvent ev) {
        if(!(ev instanceof InventoryClickEvent e))return;
        int slot = e.getSlot();
        if(!slots().contains(slot))return;
        e.setCancelled(true);
        ItemStack item = e.getCurrentItem();
        HumanEntity clicked = e.getWhoClicked();
        Inventory inventory = e.getInventory();
        if(e.isShiftClick())
            if(e.isRightClick()) shiftRightClick(slot, item, clicked, inventory);
        else shiftClick(slot,item, clicked, inventory);
        else if(e.isRightClick()) rightClick(slot, item, clicked, inventory);
            else click(slot, item, clicked, inventory);
    }

    void click(int slot, ItemStack clickedItem, HumanEntity clicker, Inventory inventory);
    default void shiftClick(int slot, ItemStack clickedItem, HumanEntity clicker, Inventory inventory) {
        click(slot, clickedItem, clicker, inventory);
    }
    default void rightClick(int slot, ItemStack clickedItem, HumanEntity clicker, Inventory inventory) {
        click(slot, clickedItem, clicker, inventory);
    }
    default void shiftRightClick(int slot, ItemStack clickedItem, HumanEntity clicker, Inventory inventory) {
        rightClick(slot, clickedItem, clicker, inventory);
    }

}
