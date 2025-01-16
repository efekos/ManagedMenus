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

import dev.efekos.mm.task.MenuTickTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Menu implements InventoryHolder {

    private final Inventory inventory;
    private final JavaPlugin plugin;
    private MenuTickTask tickTask;
    private MenuEventListener events;
    private Consumer<InventoryOpenEvent> onOpen;
    private Consumer<InventoryCloseEvent> onClose;
    private Consumer<InventoryDragEvent> onDrag;
    private Consumer<InventoryMoveItemEvent> onMove;
    private final List<MenuItem> items = new ArrayList<>();

    private void setupEvents(){
        if(events!=null)return;
        events = new MenuEventListener(this);
        plugin.getServer().getPluginManager().registerEvents(events, plugin);
    }

    public void open(Player player){
        setupEvents();
        inventory.clear();
        for (MenuItem item : items)
            if (item.placesItem())
                item.placeItems(inventory);
        player.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public static Menu create(String title,int rows,JavaPlugin owner){
        return new Menu(title,owner,rows*9);
    }

    Menu(String title,JavaPlugin owner,int size) {
        this.inventory = Bukkit.createInventory(this,size,title);
        this.plugin = owner;
    }

    public Menu onOpen(Consumer<InventoryOpenEvent> onOpen) {
        this.onOpen = onOpen;
        return this;
    }

    public Menu onClose(Consumer<InventoryCloseEvent> onClose) {
        this.onClose = onClose;
        return this;
    }

    public Menu onDrag(Consumer<InventoryDragEvent> onDrag) {
        this.onDrag = onDrag;
        return this;
    }

    public Menu onMove(Consumer<InventoryMoveItemEvent> onMove) {
        this.onMove = onMove;
        return this;
    }

    public void onOpen(InventoryOpenEvent event) {
        tickTask = new MenuTickTask(this,(Player) event.getPlayer());
        tickTask.runTaskTimer(plugin,1,1);
        for (MenuItem item : items) if(item.listensTo(event))item.on(event);
        onOpen.accept(event);
    }

    public void onClose(InventoryCloseEvent event) {
        for (MenuItem item : items) if(item.listensTo(event))item.on(event);
        onClose.accept(event);
        tickTask.cancel();
        tickTask=null;
    }

    public void onDrag(InventoryDragEvent event) {
        for (MenuItem item : items) if(item.listensTo(event))item.on(event);
        onDrag.accept(event);
    }

    public void onClick(InventoryClickEvent event){
        for (MenuItem item : items) if(item.listensTo(event))item.on(event);
    }

    public void onMove(InventoryMoveItemEvent event) {
        onMove.accept(event);
    }

    public Menu addItem(MenuItem item){
        items.add(item);
        return this;
    }

    public List<MenuItem> getItems() {
        return new ArrayList<>(items);
    }

    public void clearItems(){
        items.clear();
    }

    public void removeItem(int i){
        if(i<0||i>=items.size()) return;
        items.remove(i);
    }

    public void removeItem(MenuItem item){
        items.remove(item);
    }

    public static void prevent(Cancellable cancellable){
        cancellable.setCancelled(true);
    }

}
