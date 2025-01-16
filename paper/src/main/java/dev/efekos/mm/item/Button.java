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

package dev.efekos.mm.item;

import dev.efekos.mm.ClickableMenuItem;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Button implements ClickableMenuItem {

    private ClickEventListener onClick = (a,b,c,d)->{};
    private ClickEventListener onShiftClick = (a,b,c,d)->onClick.apply(a,b,c,d);
    private ClickEventListener onRightClick = (a,b,c,d)->onClick.apply(a,b,c,d);
    private ClickEventListener onShiftRightClick = (a,b,c,d)->onRightClick.apply(a,b,c,d);
    private int slot;
    private ItemStack stack;

    public Button(int slot, ItemStack stack) {
        this.slot = slot;
        this.stack = stack;
    }

    public Button() {
        slot = 0;
        stack = ItemStack.empty();
    }

    @Override
    public List<Integer> slots() {
        return List.of(slot);
    }

    @Override
    public void click(int slot, ItemStack clickedItem, HumanEntity clicker, Inventory inventory) {
        onClick.apply(slot, clickedItem, clicker, inventory);
    }

    @Override
    public void shiftClick(int slot, ItemStack clickedItem, HumanEntity clicker, Inventory inventory) {
        onShiftClick.apply(slot, clickedItem, clicker, inventory);
    }

    @Override
    public void rightClick(int slot, ItemStack clickedItem, HumanEntity clicker, Inventory inventory) {
        onRightClick.apply(slot, clickedItem, clicker, inventory);
    }

    @Override
    public void shiftRightClick(int slot, ItemStack clickedItem, HumanEntity clicker, Inventory inventory) {
        onShiftRightClick.apply(slot, clickedItem, clicker, inventory);
    }

    @Override
    public boolean placesItem() {
        return true;
    }

    @Override
    public void placeItems(Inventory inventory) {
        inventory.setItem(slot, stack);
    }

    public Button slot(int slot) {
        this.slot = slot;
        return this;
    }

    public Button stack(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    public Button onClick(ClickEventListener onClick) {
        this.onClick = onClick;
        return this;
    }

    public Button onShiftClick(ClickEventListener onShiftClick) {
        this.onShiftClick = onShiftClick;
        return this;
    }

    public Button onRightClick(ClickEventListener onRightClick) {
        this.onRightClick = onRightClick;
        return this;
    }

    public Button onShiftRightClick(ClickEventListener onShiftRightClick) {
        this.onShiftRightClick = onShiftRightClick;
        return this;
    }

}
