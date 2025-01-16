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

import dev.efekos.mm.item.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.function.BiConsumer;

public interface MenuItem {

    boolean listensTo(InventoryEvent e);
    void on(InventoryEvent e);
    boolean placesItem();
    void placeItems(Inventory inventory);
    default boolean isTicked(){
        return false;
    };
    default void tick(Inventory inventory, Player owner){};

    // static creators

    static Background background(){
        return background(BackgroundColor.BLACK);
    }
    static Background background(BackgroundColor color){
        return new Background(color);
    }

    static Skull skull(int slot,Player owner){
        return new Skull(slot,owner);
    }

    static SingleItemStack stack(int slot,ItemStack item){
        return new SingleItemStack(slot,item);
    }

    static Ticker ticker(BiConsumer<Inventory,Player> consumer){
        return new Ticker(consumer);
    }

    static ClickListener clickListener(){
        return new ClickListener();
    }

    static ClickListener clickListener(Integer... slots){
        return new ClickListener(Arrays.asList(slots));
    }

    static Button button(ItemStack stack){
        return new Button().stack(stack);
    };

    static Button button(int slot,ItemStack stack){
        return new Button(slot,stack);
    }

    static Button button(ItemStack stack,ClickEventListener onClick){
        return new Button().stack(stack).onClick(onClick);
    }

    static Button button(int slot,ItemStack stack,ClickEventListener onClick){
        return new Button(slot,stack).onClick(onClick);
    }

    static Button button(){
        return new Button();
    }

    static DynamicButton dynamicButton(int slot,ItemStack stack){
        return new DynamicButton(slot,stack);
    }

    static DynamicButton dynamicButton(int slot,ItemStack stack,ClickEventListener onClick){
        return new DynamicButton(slot,stack).onClick(onClick);
    }

    static DynamicButton dynamicButton(ItemStack stack){
        return new DynamicButton().stack(stack);
    }

    static DynamicButton dynamicButton(ItemStack stack,ClickEventListener onClick){
        return new DynamicButton().stack(stack).onClick(onClick);
    }

    static DynamicButton dynamicButton(){
        return new DynamicButton();
    }

    static Square square(){
        return new Square();
    }

    static Square square(int x,int y){
        return new Square().position(x,y);
    }

    static Square square(ItemStack stack){
        return new Square().stack(stack);
    }

    static Square square(ItemStack stack,int x,int y){
        return new Square().position(x,y).stack(stack);
    }

    static Square square(ItemStack stack,int x,int y,int width,int height){
        return new Square(x, y, width, height, stack);
    }

}
