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
import dev.efekos.mm.DynamicProvider;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.BiFunction;

public class DynamicButton implements ClickableMenuItem, DynamicProvider<DynamicButton> {

    private ClickEventListener onClick = (a,b,c,d)->{};
    private ClickEventListener onShiftClick = (a,b,c,d)->onClick.apply(a,b,c,d);
    private ClickEventListener onRightClick = (a,b,c,d)->onClick.apply(a,b,c,d);
    private ClickEventListener onShiftRightClick = (a,b,c,d)->onRightClick.apply(a,b,c,d);
    private int slot;
    private ItemStack stack;
    private int refreshRate = 1;
    private BiFunction<Inventory, Player, Material> materialProvider;
    private BiFunction<Inventory, Player, String> nameProvider;
    private BiFunction<Inventory, Player, List<String>> loreProvider;
    private BiFunction<Inventory, Player, Boolean> glintProvider;

    public DynamicButton(int slot, ItemStack stack) {
        this.slot = slot;
        this.stack = stack;
    }

    public DynamicButton() {
        slot = 0;
        stack = new ItemStack(Material.AIR);
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

    public DynamicButton slot(int slot) {
        this.slot = slot;
        return this;
    }

    public DynamicButton stack(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    public DynamicButton onClick(ClickEventListener onClick) {
        this.onClick = onClick;
        return this;
    }

    public DynamicButton onShiftClick(ClickEventListener onShiftClick) {
        this.onShiftClick = onShiftClick;
        return this;
    }

    public DynamicButton onRightClick(ClickEventListener onRightClick) {
        this.onRightClick = onRightClick;
        return this;
    }

    public DynamicButton onShiftRightClick(ClickEventListener onShiftRightClick) {
        this.onShiftRightClick = onShiftRightClick;
        return this;
    }

    @Override
    public int getRefreshRate() {
        return refreshRate;
    }

    public DynamicButton dynamicMaterial(BiFunction<Inventory,Player,Material> func){
        this.materialProvider = func;
        return this;
    }

    public DynamicButton dynamicName(BiFunction<Inventory,Player, String> name){
        this.nameProvider = name;
        return this;
    }

    public DynamicButton dynamicLore(BiFunction<Inventory,Player, List<String>> lore){
        this.loreProvider = lore;
        return this;
    }

    public DynamicButton dynamicGlint(BiFunction<Inventory,Player, Boolean> glint){
        this.glintProvider = glint;
        return this;
    }

    public DynamicButton refreshRate(int refreshRate){
        this.refreshRate = refreshRate;
        return this;
    }

    private int ticks;

    @Override
    public boolean isTicked() {
        return true;
    }

    @Override
    public void tick(Inventory inventory, Player owner) {
        if(refreshRate<=1||++ticks%refreshRate!=0)return;
        ItemStack item = inventory.getItem(slot);
        if(item==null||item.getType().isAir()) item = stack.clone();
        if(materialProvider!=null)item.setType(materialProvider.apply(inventory, owner));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        if(nameProvider!=null) meta.setDisplayName(nameProvider.apply(inventory, owner));
        if(loreProvider!=null) meta.setLore(loreProvider.apply(inventory,owner));
        if(glintProvider!=null) if(glintProvider.apply(inventory,owner)){
            meta.addEnchant(Enchantment.MENDING,1,true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(Enchantment.MENDING);
        }
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }

}
