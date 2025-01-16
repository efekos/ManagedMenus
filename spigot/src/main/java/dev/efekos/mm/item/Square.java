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

import dev.efekos.mm.MenuItem;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Square implements MenuItem {

    private int x;
    private int y;
    private int width;
    private int height;
    private ItemStack stack;

    @Override
    public boolean placesItem() {
        return true;
    }

    public Square x(int x){
        this.x = x;
        return this;
    }

    public Square y(int y){
        this.y = y;
        return this;
    }

    public Square width(int width){
        this.width = width;
        return this;
    }

    public Square height(int height){
        this.height = height;
        return this;
    }

    public Square stack(ItemStack stack){
        this.stack = stack;
        return this;
    }

    public Square position(int x, int y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Square size(int width, int height){
        this.width = width;
        this.height = height;
        return this;
    }

    public Square() {
    }

    public Square(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Square(int x, int y, int width, int height, ItemStack stack) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.stack = stack;
    }

    private int slot(int x, int y) {
        return y*9+x;
    }

    @Override
    public void placeItems(Inventory inventory) {
        int x2 = x+width;
        int y2 = y+height;
        int startSlot = slot(x, y);
        int endSlot = slot(x2, y2);
        for (int i = startSlot; i <= endSlot; i++) inventory.setItem(i, stack);
    }

}
