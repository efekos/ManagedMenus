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

import java.util.ArrayList;
import java.util.List;

public class ClickListener implements ClickableMenuItem {

    private final List<Integer> slots;
    private ClickEventListener onClick;
    private ClickEventListener onShiftClick = (a,b,c,d)->onClick.apply(a,b,c,d);
    private ClickEventListener onRightClick = (a,b,c,d)->onClick.apply(a,b,c,d);
    private ClickEventListener onRightShiftClick = (a,b,c,d)->onRightClick.apply(a,b,c,d);

    public ClickListener() {
        slots = new ArrayList<>();
    }

    public ClickListener(int slot) {
        slots = List.of(slot);
    }

    public ClickListener(List<Integer> slots) {
        this.slots = slots;
    }

    @Override
    public List<Integer> slots() {
        return slots;
    }

    public ClickListener onClick(ClickEventListener onClick) {
        this.onClick = onClick;
        return this;
    }

    public ClickListener onShiftClick(ClickEventListener onShiftClick) {
        this.onShiftClick = onShiftClick;
        return this;
    }

    public ClickListener onRightClick(ClickEventListener onRightClick) {
        this.onRightClick = onRightClick;
        return this;
    }

    public ClickListener onRightShiftClick(ClickEventListener onRightShiftClick) {
        this.onRightShiftClick = onRightShiftClick;
        return this;
    }

    @Override
    public void click(int slot, ItemStack clickedItem, HumanEntity clicker, Inventory inventory) {
        onClick.apply(slot,clickedItem,clicker,inventory);
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
        onRightShiftClick.apply(slot, clickedItem, clicker, inventory);
    }

    public ClickListener slot(int slot){
        slots.add(slot);
        return this;
    }

    public ClickListener slots(int... slots){
        for (int slot : slots) this.slots.add(slot);
        return this;
    }

    public ClickListener slots(List<Integer> slots){
        this.slots.addAll(slots);
        return this;
    }

    @Override
    public boolean placesItem() {
        return false;
    }

    @Override
    public void placeItems(Inventory inventory) {

    }

}
