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

import dev.efekos.mm.DynamicProvider;
import dev.efekos.mm.MenuItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.BiFunction;

public class DynamicItemStack implements MenuItem, DynamicProvider<DynamicItemStack> {

    private int slot;
    private ItemStack stack;
    private int refreshRate = 1;
    private BiFunction<Inventory, Player, Material> materialProvider;
    private BiFunction<Inventory, Player, Component> nameProvider;
    private BiFunction<Inventory, Player, List<Component>> loreProvider;
    private BiFunction<Inventory, Player, Boolean> glintProvider;

    public DynamicItemStack(int slot, ItemStack stack){
        this.slot = slot;
        this.stack = stack;
    }

    public DynamicItemStack() {
        slot = 0;
        stack = ItemStack.empty();
    }

    @Override
    public boolean placesItem() {
        return true;
    }

    @Override
    public void placeItems(Inventory inventory) {
        inventory.setItem(slot, stack);
    }

    @Override
    public boolean isTicked() {
        return true;
    }

    private int ticks;

    @Override
    public void tick(Inventory inventory, Player owner) {
        if(refreshRate<=1||++ticks%refreshRate!=0)return;
        ItemStack item = inventory.getItem(slot);
        if(item==null||item.isEmpty()) item = stack.clone();
        if(materialProvider!=null)item = item.withType(materialProvider.apply(inventory, owner));
        ItemMeta meta = item.getItemMeta();
        if(nameProvider!=null) meta.displayName(nameProvider.apply(inventory, owner));
        if(loreProvider!=null) meta.lore(loreProvider.apply(inventory,owner));
        if(glintProvider!=null) if(glintProvider.apply(inventory,owner)){
            meta.addEnchant(Enchantment.MENDING,1,true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(Enchantment.MENDING);
        }
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }

    public DynamicItemStack slot(int slot){
        this.slot = slot;
        return this;
    }

    public DynamicItemStack refreshRate(int refreshRate){
        this.refreshRate = refreshRate;
        return this;
    }

    public DynamicItemStack firstStack(ItemStack stack){
        this.stack = stack;
        return this;
    }

    @Override
    public int getRefreshRate() {
        return refreshRate;
    }

    public DynamicItemStack dynamicMaterial(BiFunction<Inventory,Player,Material> func){
        this.materialProvider = func;
        return this;
    }

    public DynamicItemStack dynamicName(BiFunction<Inventory,Player, Component> name){
        this.nameProvider = name;
        return this;
    }

    public DynamicItemStack dynamicLore(BiFunction<Inventory,Player, List<Component>> lore){
        this.loreProvider = lore;
        return this;
    }

    public DynamicItemStack dynamicGlint(BiFunction<Inventory,Player, Boolean> glint){
        this.glintProvider = glint;
        return this;
    }

}
